package com.jokopriyono.photography;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jokopriyono.photography.api.APIRepository;
import com.jokopriyono.photography.api.PhotoItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<PhotoItem>> {

    private static final String baseUrl = "https://picsum.photos/";
    private RelativeLayout relativeLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLoading = findViewById(R.id.relative_loading);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

//        DataDummy dataDummy = new DataDummy();
//        RecyclerAdapter adapter = new RecyclerAdapter(dataDummy);
//
//        recycler.setAdapter(adapter);

        getDataFromAPI();
    }

    private void getDataFromAPI() {
        showLoading();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();
        APIRepository apiRepository = retrofit.create(APIRepository.class);
        Call<List<PhotoItem>> call = apiRepository.getAllPhotos();
        call.enqueue(this);
    }

    private void showLoading() {
        relativeLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        relativeLoading.setVisibility(View.GONE);
    }

    @Override
    public void onResponse(@NonNull Call<List<PhotoItem>> call, @NonNull Response<List<PhotoItem>> response) {
        hideLoading();
        Toast.makeText(this, "Berhasil", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(@NonNull Call<List<PhotoItem>> call, @NonNull Throwable t) {
        hideLoading();
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }
}
