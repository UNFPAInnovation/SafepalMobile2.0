<?php

session_start();

//PSR
use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

//geocoder
use \Geocoder\Provider\GoogleMaps as gmaps;

require '../vendor/autoload.php';

$dotenv = new Dotenv\Dotenv(__DIR__);
$dotenv->load();

$app = new \Slim\App(["settings" => getenv($config)]);

//get dependency injection containers
$dicontainer = $app->getContainer();

//Monolog
$dicontainer['logger'] = function ($mc){
	$logger = new \Monolog\Logger('apiLogger');
	$file = new \Monolog\Handler\StreamHandler("../logs/app.log");
	$logger->pushHandler($file);
	return $logger;
};

//PDO
$dicontainer['pdo'] = function ($pc){
	$pdo = new PDO("mysql:host=".getenv($db['host']).";dbname=".getenv($db['name']).",".getenv($db['username']).",".getenv($db['pass']));
	$pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
	$pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
	$pdo->setAttribute(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8');
	return $pdo;
};

//middleware to handle CSRF
$app->add(new \Slim\Csrf\Guard);


//add reports
$app->post('/api/v1/reports/new', function (Request $req, Response $res){
	$report = $req->getParsedBody();
	if (!empty($report)) {

		$query_params = array(
        ':survivor_gender' => $report['survivor_gender'],
        ':survivor_date_of_birth' => $report['survivor_date_of_birth'],
        ':incident_type' => $report['incident_type'],
        ':incident_location' => $report['incident_location'],
        ':incident_date_and_time' => $report['incident_date_and_time'],
        ':status' => $report['status'],
        ':incident_description' => $report['incident_description'],
        ':incident_reported_by' => $report['incident_reported_by'],
    	':reporter_lat' => $report['reporter_lat'],
    	':reporter_long' => $report['reporter_long'],
    	':survivor_contact_phone_number' => $report['survivor_contact_phone_number'],
    	':survivor_contact_email' => $report['survivor_contact_email'],
    	);

    	try {

    		$this->pdo->prepare(getenv($query));
    		$this->pdo->execute(filter_var_array($query_params));

    		//return status and data
    		return $res->withJson(getenv($SUCCESS));

    	} catch (PDOException $e) {
    		$this->logger->addInfo($e);

    		//return status and data
    		return $res->withJson(getenv($FAILURE));
    	}

    	$this->pdo = null; //close connection
	}
});

//get all reports
$app->post('/api/v1/reports/all', function (Request $req, Response $res){

});

//return report by id
$app->get('/api/v1/reports/{rid}', function (Request $req, Response $res){
	$rID = $req->getAttribute('rid');
});

//delete report
$app->post('/api/v1/reports/del/{rid}', function (Request $req, Response $res){
	$rID = $req->getAttribute('rid');
});

//get nearest health centres based on GPS
$app->post('/api/v1/locations/nearest', function (Request $req, Response $res){
	$gps = $req->getParsedBody();

	//quick check
	if (!empty($gps)) {
		//curl http adapter -- *note: should be optional since slim already implements PSR-7
		$curl = new \Ivory\HttpAdapter\CurlHttpAdapter();
		$gmaps = new gmaps($curl);

		//get district name
		$district = $gmaps->reverse($gps['reporter_lat'], $gps['reporter_long'])->getLocality();

		$centres = $this->pdo->query("SELECT * FROM cso_details WHERE cso_location = ".$district);

		$nearest = new array();

		//**note: consider refactoring here
		if (sizeof($centres) > 0) {

			for ($centres as $key => $value) {
				//calculate distance -- could be optional since we are sending back centres in the same district

				$theta = $gps['reporter_long'] - $centres['cso_longtitude'];
				$d = sin(deg2rad($gps['reporter_lat'])) * sin(deg2rad($centres['cso_latitude'])) 
					+ cos(deg2rad($gps['reporter_lat'])) * cos(deg2rad($centres['cso_latitude'])) * cos(deg2rad($theta));
				$d = (float) rad2deg(acos($d)) * 69.09; //converted to miles from nautical miles? (60 * 1.1515)

				//construct result array
				array_push($nearest, array("cso_name" => $centres["cso_name"], "cso_location" => $centres["cso_location"], 
					"cso_distance" => $d * (int) 1.61, "cso_phone_number" => $centres["cso_phone_number"]));
			}
		}

		$this->pdo = null; //close connection

		//return json
		return $res->withJson(sort($nearest));
	}

});


//run app
$app->run();

?>