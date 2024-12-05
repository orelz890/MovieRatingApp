package com.example.movieapp.serviceapi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    // Private constructor to prevent instantiation
    private RetrofitInstance() {}

    // Inner static class responsible for holding the Singleton instance
    private static class RetrofitHolder {
        private static final Retrofit INSTANCE = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    // Public method to access the Singleton Retrofit instance
    public static MovieApiService getService() {
        return RetrofitHolder.INSTANCE.create(MovieApiService.class);
    }
}
