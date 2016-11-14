<?php

session_start();

use \Psr\Http\Message\ServerRequestInterface as Request;
use \Psr\Http\Message\ResponseInterface as Response;

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


//run app
$app->run();

?>