package com.blackdev.misakoassignment;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;

public interface ApiInterface {
    @GET("/rest/v2/region/asia")
    public void getDataList(Callback<List<POJO>> callback);
}
