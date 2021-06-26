package com.project.misterauto.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;


public class GsonHelper {

    private static Gson gson;
    public  static Gson getGson(){
        if(gson == null)
            gson =new GsonBuilder()
                .setLenient()
                .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).create();
        return gson;
    }
}
