package com.alexanders.retrotest.app;

import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author astolnikov: 10.05.2018
 */

/**
 *
 */
public class HttpConnectionHelper {

    //May be used if stand has problems with ssl validation
    //DON'T DISABLE WITHOUT NECESSITY
    public OkHttpClient getOkHttpClient(boolean isSSLValidate) {
        if(isSSLValidate) {
            return new OkHttpClient().newBuilder()
                    .addInterceptor(new AllureOkHttp3())
                    .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
                    .build();
        } else  {
            try {
                // Create a trust manager that does not validate certificate chains
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

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                // Create an ssl socket factory with our all-trusting manager
                final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(sslSocketFactory);
                builder.hostnameVerifier((hostname, session) -> true);
                builder.addInterceptor(new AllureOkHttp3())
                        .connectTimeout(15, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS);
                return builder.build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
