package com.jokopriyono.photography;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout relativeLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLoading = findViewById(R.id.relative_loading);
        RecyclerView recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        DataDummy dataDummy = new DataDummy();
        RecyclerAdapter adapter = new RecyclerAdapter(dataDummy);

        recycler.setAdapter(adapter);

    }

    private void showLoading(){
        relativeLoading.setVisibility(View.VISIBLE);
    }

    private void hideLoading(){
        relativeLoading.setVisibility(View.GONE);
    }
}
