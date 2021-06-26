package com.project.misterauto.Activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.project.misterauto.Adapter.TachesAdapter;
import com.project.misterauto.R;
import com.project.misterauto.Util.Cache;
import com.project.misterauto.model.taches;
import com.project.misterauto.network.ApiCall;
import com.project.misterauto.network.GsonHelper;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class tacheActivity extends AppCompatActivity {

    ArrayList<taches> taches;
    ProgressBar progressBars;
    TextView empty_response;
    RecyclerView recyclerView;
    TachesAdapter tachesAdapter;
    Bundle extras;
    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taches);

        progressBars = (ProgressBar) findViewById(R.id.progress_bar);
        empty_response = findViewById(R.id.empty_textview);
        recyclerView = findViewById(R.id.taches_recyclerview);

        if(!getResources().getBoolean(R.bool.is_tablet)) {
            final LinearLayoutManager llm = new LinearLayoutManager(tacheActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
        }
        else{
            final GridLayoutManager lm = new GridLayoutManager( tacheActivity.this, 2);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(lm);
        }




        taches = new ArrayList<>();

        extras = getIntent().getExtras();
        if(extras!=null) {
            try {
                id = extras.getInt("id");
            }catch (Exception e)
            {
                if (progressBars.isShown())
                    progressBars.setVisibility(View.GONE);

                recyclerView.setVisibility(View.GONE);
                empty_response.setVisibility(View.VISIBLE);
            }
            getAlltaches();

        }


    }


    private void getAlltaches(){
        String cacheTag = "taches_"+id;

        ApiCall.getTaches(id,new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response)
            {
                if (response.body() != null && response.isSuccessful()) {
                    ArrayList<taches> result = GsonHelper.getGson().fromJson(response.body(), new TypeToken<List<taches>>() {
                    }.getType());
                    Cache.putPermanentObject(response.body().toString(), cacheTag);
                    if (result.size() > 0)
                        taches.addAll(result);
                } else {
                    String resultFromCache = (String) Cache.getPermanentObject(cacheTag);
                    if (resultFromCache != null)
                        taches = GsonHelper.getGson().fromJson(resultFromCache, new TypeToken<List<taches>>() {
                        }.getType());
                    Toast.makeText(tacheActivity.this, getString(R.string.error_load_data), Toast.LENGTH_SHORT).show();

                }

                setListAdapter();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                String resultFromCache = (String) Cache.getPermanentObject(cacheTag);
                if(resultFromCache!=null)
                    taches = GsonHelper.getGson().fromJson(resultFromCache, new TypeToken<List<taches>>(){}.getType());
                Toast.makeText(tacheActivity.this, getString(R.string.check_internet), Toast.LENGTH_SHORT).show();

                if (progressBars.isShown())
                    progressBars.setVisibility(View.GONE);

                setListAdapter();
            }
        });
    }



    private void setListAdapter(){
        if(taches.size()>0){
            tachesAdapter = new TachesAdapter(tacheActivity.this, taches);
            recyclerView.setAdapter(tachesAdapter);

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
            final LinearLayoutManager llm = new LinearLayoutManager(tacheActivity.this);
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
        } else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            final GridLayoutManager lm = new GridLayoutManager( tacheActivity.this, 2);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(lm);

        }
    }


}

