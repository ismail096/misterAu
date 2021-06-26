package com.project.misterauto.network;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("users")
    Call<JsonArray> getUsers();

    @GET("todos")
    Call<JsonArray> getTaches( @Query("userId") int userId);
}
