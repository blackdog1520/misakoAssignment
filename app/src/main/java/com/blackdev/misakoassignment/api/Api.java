package com.blackdev.misakoassignment.api;

import retrofit.RestAdapter;

public class Api {
    public static ApiInterface getClient(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint("https://restcountries.eu")
                .build();

        ApiInterface api = adapter.create(ApiInterface.class);
        return api;
    }
}
