package com.project.misterauto.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.project.misterauto.Adapter.UserAdapter;
import com.project.misterauto.R;
import com.project.misterauto.Util.Cache;
import com.project.misterauto.model.users;
import com.project.misterauto.network.ApiCall;
import com.project.misterauto.network.GsonHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ArrayList<users> users;
    ProgressBar progressBars;
    TextView empty_response;
    RecyclerView recyclerView;
    UserAdapter userAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBars = (ProgressBar) findViewById(R.id.progress_bar);
        empty_response = findViewById(R.id.empty_textview);
        recyclerView = findViewById(R.id.user_recyclerview);

        if(!getResources().getBoolean(R.bool.is_tablet)) {
            final LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
        }
        else{
            final GridLayoutManager lm = new GridLayoutManager( MainActivity.this, 2);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(lm);
        }




        users = new ArrayList<>();
        getAllUsers();
    }


    private void getAllUsers(){
        String cacheTag = "users";

        ApiCall.getUsers(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response)
            {
                if (response.body() != null && response.isSuccessful()) {
                        ArrayList<users> result = GsonHelper.getGson().fromJson(response.body(), new TypeToken<List<users>>() {
                        }.getType());
                        Cache.putPermanentObject(response.body().toString(), cacheTag);
                        if (result.size() > 0)
                            users.addAll(result);
                    } else {
                        String resultFromCache = (String) Cache.getPermanentObject(cacheTag);
                        if (resultFromCache != null)
                            users = GsonHelper.getGson().fromJson(resultFromCache, new TypeToken<List<users>>() {
                            }.getType());
                        Toast.makeText(MainActivity.this, getString(R.string.error_load_data), Toast.LENGTH_SHORT).show();

                    }

                    setListAdapter();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                      t.printStackTrace();
                 String resultFromCache = (String) Cache.getPermanentObject(cacheTag);
                    if(resultFromCache!=null)
                        users = GsonHelper.getGson().fromJson(resultFromCache, new TypeToken<List<users>>(){}.getType());
                    Toast.makeText(MainActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

                if (progressBars.isShown())
                    progressBars.setVisibility(View.GONE);

                    setListAdapter();
            }
        });
    }



    private void setListAdapter(){
        if(users.size()>0){
            userAdapter = new UserAdapter(MainActivity.this, users);
                recyclerView.setAdapter(userAdapter);

            if (progressBars.isShown())
                progressBars.setVisibility(View.GONE);

        }


        else
        {
            if (progressBars.isShown())
                progressBars.setVisibility(View.GONE);

            recyclerView.setVisibility(View.GONE);
            empty_response.setVisibility(View.VISIBLE);
        }



    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {

        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            final LinearLayoutManager llm = new LinearLayoutManager(MainActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            final GridLayoutManager lm = new GridLayoutManager( MainActivity.this, 2);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(lm);

        }
    }
}

