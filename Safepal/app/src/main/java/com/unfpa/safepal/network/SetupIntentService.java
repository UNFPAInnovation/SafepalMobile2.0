package com.unfpa.safepal.network;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.unfpa.safepal.provider.articletable.ArticletableColumns;
import com.unfpa.safepal.provider.articletable.ArticletableContentValues;
import com.unfpa.safepal.provider.districttable.DistricttableColumns;
import com.unfpa.safepal.provider.districttable.DistricttableContentValues;
import com.unfpa.safepal.provider.faqtable.FaqtableColumns;
import com.unfpa.safepal.provider.faqtable.FaqtableContentValues;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableColumns;
import com.unfpa.safepal.provider.organizationtable.OrganizationtableContentValues;
import com.unfpa.safepal.provider.questiontable.QuestiontableColumns;
import com.unfpa.safepal.provider.questiontable.QuestiontableContentValues;
import com.unfpa.safepal.provider.quiztable.QuiztableColumns;
import com.unfpa.safepal.provider.quiztable.QuiztableContentValues;
import com.unfpa.safepal.provider.videotable.VideotableColumns;
import com.unfpa.safepal.provider.videotable.VideotableContentValues;
import com.unfpa.safepal.retrofit.APIClient;
import com.unfpa.safepal.retrofit.APIInterface;
import com.unfpa.safepal.retrofitmodels.articles.Articles;
import com.unfpa.safepal.retrofitmodels.districts.Districts;
import com.unfpa.safepal.retrofitmodels.faqs.Faq;
import com.unfpa.safepal.retrofitmodels.organizations.Organizations;
import com.unfpa.safepal.retrofitmodels.questions.Questions;
import com.unfpa.safepal.retrofitmodels.quizzes.Quizzes;
import com.unfpa.safepal.retrofitmodels.videos.Result;
import com.unfpa.safepal.retrofitmodels.videos.Videos;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Fetches data from the server, deletes old data and saves it into the db for offline use and better user experience
 * Service is called whenever the app starts in MainActivity
 * @author Phillip Kigenyi (codephillip@gmail.com)
 */
public class SetupIntentService extends IntentService {
    private static final String TAG = SetupIntentService.class.getSimpleName();
    private APIInterface apiInterface;

    public SetupIntentService() {
        super(SetupIntentService.class.getSimpleName());
    }

    public SetupIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: started");
        Timber.d("onHandleIntent: started service");
        apiInterface = APIClient.getClient().create(APIInterface.class);

        boolean isConnectedToInternet = isConnectedToInternet(this);
        Timber.d("is connected %s", isConnectedToInternet);

        if (isConnectedToInternet) {
            try {
                loadVideos();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadArticles();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadOrganizations();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadDistricts();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadQuestions();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadQuizzes();
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                loadFaqs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadVideos() {
        Timber.d("get videos list started");
        Call<Videos> call = apiInterface.getVideos();

        call.enqueue(new Callback<Videos>() {
            @Override
            public void onResponse(Call<Videos> call, Response<Videos> response) {
                Timber.d("onResponse() -> %s", response.code());
                try {
                    if (response.code() == 200) {
                        saveVideos(response.body());
                    } else {
                        Timber.e("Failed to get videos");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Videos> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveVideos(Videos videos) {
        Timber.d("INSERT: video starting");
        if (videos == null)
            throw new NullPointerException("Videos not found");
        List<Result> videosList = videos.getResults();

        long deleted = 0;
        try {
            if (videosList.size() > 1)
                deleted = getContentResolver().delete(VideotableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (Result video : videosList) {
            VideotableContentValues values = new VideotableContentValues();
            Timber.d("Video data " + video.getTitle() + video.getCreatedAt() + video.getUrl());
            values.putTitle(video.getTitle());
            values.putCategory(video.getCategory().getName());
            values.putServerid(video.getId());
            values.putDuration(video.getDuration());
            values.putDescription(video.getDescription());
            values.putUrl(video.getUrl());
            values.putThumbnail(video.getThumbnail());
            values.putCreatedAt(video.getCreatedAt());
            values.putRating(video.getRating());
            // insert 0 completion since user has not yet watched video
            values.putCompletionRate(0);
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved video %s", uri);
        }
    }

    private void loadArticles() {
        Timber.d("get articles list started");
        Call<Articles> call = apiInterface.getArticles();

        call.enqueue(new Callback<Articles>() {
            @Override
            public void onResponse(Call<Articles> call, Response<Articles> response) {
                Timber.d("onResponse() -> %s", response.code());
                try {
                    if (response.code() == 200) {
                        saveArticles(response.body());
                    } else {
                        Timber.e("Failed to get articles");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Articles> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveArticles(Articles articles) {
        Timber.d("INSERT: articles starting");
        if (articles == null)
            throw new NullPointerException("Articles not found");
        List<com.unfpa.safepal.retrofitmodels.articles.Result> articlesList = articles.getResults();

        long deleted = 0;
        try {
            if (articlesList.size() > 1)
                deleted = getContentResolver().delete(ArticletableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.articles.Result article : articlesList) {
            ArticletableContentValues values = new ArticletableContentValues();
            Timber.d("Article data " + article.getTitle() + article.getCreatedAt());
            values.putTitle(article.getTitle());
            values.putCategory(article.getCategory().getName());
            values.putContent(article.getContent());
            values.putQuestions(article.getQuestions());
            values.putServerid(article.getId());
            values.putThumbnail(article.getThumbnail());
            values.putCreatedAt(article.getCreatedAt());
            values.putRating(article.getRating());
            // insert 0 completion since user has not yet read the article
            values.putCompletionRate(0);
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved article %s", uri);
        }
    }

    private void loadOrganizations() {
        Timber.d("get organizations list started");
        Call<Organizations> call = apiInterface.getOrganizations();

        call.enqueue(new Callback<Organizations>() {
            @Override
            public void onResponse(Call<Organizations> call, Response<Organizations> response) {
                Timber.d("onResponse() -> %s", response.code());
                try {
                    if (response.code() == 200) {
                        saveOrganizations(response.body());
                    } else {
                        Timber.e("Failed to get organizations");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Organizations> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveOrganizations(Organizations organizations) {
        Timber.d("INSERT: organizations starting");
        if (organizations == null)
            throw new NullPointerException("Organizations not found");
        List<com.unfpa.safepal.retrofitmodels.organizations.Result> organizationsList = organizations.getResults();

        long deleted = 0;
        try {
            if (organizationsList.size() > 1)
                deleted = getContentResolver().delete(OrganizationtableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.organizations.Result organization : organizationsList) {
            OrganizationtableContentValues values = new OrganizationtableContentValues();
            Timber.d("organization data %s", organization.getFacilityName());
            values.putFacilityName(organization.getFacilityName());
            values.putDistrict(organization.getDistrict().getName());
            values.putAddress(organization.getAddress());
            values.putCloseHour(organization.getCloseHour());
            values.putOpenHour(organization.getOpenHour());
            values.putServerid(organization.getId());
            values.putCreatedAt(organization.getCreatedAt());
            values.putLink(organization.getLink());
            values.putLongitude(organization.getLongitude());
            values.putLatitude(organization.getLatitude());
            values.putPhoneNumber(organization.getPhoneNumber());
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved organization %s", uri);
        }
    }

    private void loadDistricts() {
        Timber.d("get district list started");
        Call<Districts> call = apiInterface.getDistricts();

        call.enqueue(new Callback<Districts>() {
            @Override
            public void onResponse(Call<Districts> call, Response<Districts> response) {
                try {
                    if (response.code() == 200) {
                        saveDistricts(response.body());
                    } else {
                        Timber.e("Failed to get districts");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Districts> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveDistricts(Districts districts) {
        Timber.d("INSERT: districts starting");
        if (districts == null)
            throw new NullPointerException("Districts not found");
        List<com.unfpa.safepal.retrofitmodels.districts.Result> districtsList = districts.getResults();

        long deleted = 0;
        try {
            if (districtsList.size() > 1)
                deleted = getContentResolver().delete(DistricttableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.districts.Result district : districtsList) {
            DistricttableContentValues values = new DistricttableContentValues();
            Timber.d("district data %s", district.getName());
            values.putName(district.getName());
            values.putCreatedAtNull();
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved district %s", uri);
        }
    }

    private void loadQuestions() {
        Timber.d("get questions list started");
        Call<Questions> call = apiInterface.getQuestions();
        call.enqueue(new Callback<Questions>() {
            @Override
            public void onResponse(Call<Questions> call, Response<Questions> response) {
                try {
                    if (response.code() == 200) {
                        saveQuestions(response.body());
                    } else {
                        Timber.e("Failed to get questions");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Questions> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveQuestions(Questions questions) {
        Timber.d("INSERT: questions starting");
        if (questions == null)
            throw new NullPointerException("Questions not found");
        List<com.unfpa.safepal.retrofitmodels.questions.Result> questionsList = questions.getResults();

        long deleted = 0;
        try {
            if (questionsList.size() > 1)
                deleted = getContentResolver().delete(QuestiontableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.questions.Result question : questionsList) {
            QuestiontableContentValues values = new QuestiontableContentValues();
            Timber.d("question data %s", question.getContent());
            values.putContent(question.getContent());
            values.putCorrectAnswer(question.getAnswer());
            values.putDifficulty(question.getDifficulty());
            values.putPositionNumber(question.getPosition());
            values.putQuiz(question.getQuiz());
            values.putCreatedAtNull();
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved question %s", uri);
        }
    }

    private void loadQuizzes() {
        Timber.d("get quizzes list started");
        Call<Quizzes> call = apiInterface.getQuizzes();
        call.enqueue(new Callback<Quizzes>() {
            @Override
            public void onResponse(Call<Quizzes> call, Response<Quizzes> response) {
                try {
                    if (response.code() == 200) {
                        saveQuizzes(response.body());
                    } else {
                        Timber.e("Failed to get quizzes");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Quizzes> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveQuizzes(Quizzes quizzes) {
        Timber.d("INSERT: quizzes starting");
        if (quizzes == null)
            throw new NullPointerException("Quizzes not found");
        List<com.unfpa.safepal.retrofitmodels.quizzes.Result> quizzesList = quizzes.getResults();

        long deleted = 0;
        try {
            if (quizzesList.size() > 1)
                deleted = getContentResolver().delete(QuiztableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.quizzes.Result quiz : quizzesList) {
            QuiztableContentValues values = new QuiztableContentValues();
            Timber.d("quiz data %s", quiz.getTitle());
            values.putTitle(quiz.getTitle());
            values.putThumbnail(quiz.getThumbnail());
            values.putServerid(quiz.getId());
            values.putDescription(quiz.getDescription());
            values.putArticle(quiz.getArticle());
            values.putCreatedAtNull();
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved quiz %s", uri);
        }
    }


    private void loadFaqs() {
        Timber.d("get faqs list started");
        Call<Faq> call = apiInterface.getFaqs();

        call.enqueue(new Callback<Faq>() {
            @Override
            public void onResponse(Call<Faq> call, Response<Faq> response) {
                try {
                    if (response.code() == 200) {
                        saveFaqs(response.body());
                    } else {
                        Timber.e("Failed to get faqs");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Faq> call, Throwable t) {
                Timber.e("onFailure() -> %s", t.getMessage());
            }
        });
    }

    private void saveFaqs(Faq faq) {
        Timber.d("INSERT: faqs starting");
        if (faq == null)
            throw new NullPointerException("Faqs not found");
        List<com.unfpa.safepal.retrofitmodels.faqs.Result> faqsList = faq.getResults();

        long deleted = 0;
        try {
            if (faqsList.size() > 1)
                deleted = getContentResolver().delete(FaqtableColumns.CONTENT_URI, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timber.d("deleted data count %s", deleted);

        for (com.unfpa.safepal.retrofitmodels.faqs.Result faqObject : faqsList) {
            FaqtableContentValues values = new FaqtableContentValues();
            Timber.d("quiz data %s", faqObject.getQuestion());
            values.putQuestion(faqObject.getQuestion());
            values.putAnswer(faqObject.getAnswer());
            values.putCategory(faqObject.getCategory());
            values.putServerid(faqObject.getId());
            final Uri uri = values.insert(getContentResolver());
            Timber.d("saved faq %s", uri);
        }
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        try {
                            HttpURLConnection urlc = (HttpURLConnection) (new URL("http://clients3.google.com").openConnection());
                            urlc.setRequestProperty("User-Agent", "Test");
                            urlc.setRequestProperty("Connection", "close");
                            urlc.setConnectTimeout(2000);
                            urlc.setReadTimeout(2000);
                            urlc.connect();
                            boolean isConnected = (urlc.getResponseCode() == 200);
                            urlc.disconnect();
                            // making a network request on 2G network will not wield proper results
                            // instead depend on the CONNECTED state of the network
                            if (!isConnected && getNetworkClass(context) == 2) {
                                isConnected = info[i].getState() == NetworkInfo.State.CONNECTED;
                            }
                            return isConnected;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
        }
        return false;
    }

    public static int getNetworkClass(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return 2;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return 3;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return 4;
            default:
                return 3;
        }
    }
}
