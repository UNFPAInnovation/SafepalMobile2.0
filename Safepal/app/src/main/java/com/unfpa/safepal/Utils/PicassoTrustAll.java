package com.unfpa.safepal.Utils;

import android.content.Context;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

class SelfSignCertHttpClient {
    public static OkHttpClient getSelfSignOkHttpClient(List<Interceptor> interceptors) {
        try {
            final TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager)trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }

            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

public class PicassoTrustAll {

    private static Picasso mInstance = null;

    private PicassoTrustAll(Context context) {
        List<Interceptor> interceptors = new ArrayList<>();
        // Add any interceptors you wish (e.g Authorization Header interceptor)

        mInstance = new Picasso.Builder(context)
                .downloader(new OkHttp3Downloader(SelfSignCertHttpClient.getSelfSignOkHttpClient(interceptors)))
                .listener((picasso, uri, exception) -> Log.e("PICASSO", exception.getMessage())).build();

    }

    public static Picasso getInstance(Context context) {
        if (mInstance == null) {
            new PicassoTrustAll(context);
        }
        return mInstance;
    }
}