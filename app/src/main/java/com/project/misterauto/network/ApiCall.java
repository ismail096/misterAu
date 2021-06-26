package com.project.misterauto.network;

import com.google.gson.JsonArray;
import com.project.misterauto.AppController;

import retrofit2.Call;
import retrofit2.Callback;

public class ApiCall
{
    public static void getUsers(Callback<JsonArray> callback) {
        Call<JsonArray> call = AppController.getAPIService().getUsers();
        call.enqueue(callback);
    }


    public static void getTaches(int userId,  Callback<JsonArray> callback) {
        Call<JsonArray> call = AppController.getAPIService().getTaches(userId);
        call.enqueue(callback);
    }
}
