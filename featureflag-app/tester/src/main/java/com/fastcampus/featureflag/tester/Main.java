package com.fastcampus.featureflag.tester;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        System.out.print("Hello and welcome!");
        // Replace this URL with your localhost endpoint
        String url = "http://localhost:8080/api/v1/resolve/string/myStringFlag";

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try {
            Response response = client.newCall(request).execute();
            logger.info("Response code: " + response.code());
            logger.info("Response code: " + response.code());
            logger.info("Response code: " + response.code());
            logger.info("Response code: " + response.code());
            logger.info("Response code: " + response.code());
            assert response.body() != null;
            logger.info("Response body: " + response.body().string());
        } catch (IOException e) {
            logger.error("Error making request to " + e.getMessage());
        }


    }
}