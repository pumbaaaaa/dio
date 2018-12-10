package com.test.dio.base.config;

import com.test.dio.base.ssl.TrustAllCerts;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Configuration
public class OkHttpConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .sslSocketFactory(sslSocketFactory(), new TrustAllCerts())
                .hostnameVerifier((s, sslSession) -> true)
                .build();
    }

    private SSLSocketFactory sslSocketFactory() {
        SSLSocketFactory sslSocketFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            sslSocketFactory = sc.getSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }
}
