package com.jokopriyono.photography;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jokopriyono.photography.api.APIRepository;
import com.jokopriyono.photography.api.PhotoItem;
import com.jokopriyono.photography.data.Database;
import com.jokopriyono.photography.data.SharedPref;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<List<PhotoItem>> {

    private static final String baseUrl = "https://picsum.photos/";
    private RelativeLayout relativeLoading;
    private RecyclerView recycler;
    private TextView txtDownload;
    private RecyclerAdapter.OnPhotoClickListener photoClickListener = new RecyclerAdapter.OnPhotoClickListener() {
        @Override
        public void OnClick(PhotoItem photo) {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("data", photo);
            startActivity(i);
        }
    };
    private SharedPref sharedPref;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPref = new SharedPref(this);
        db = new Database(this);

        relativeLoading = findViewById(R.id.relative_loading);
        recycler = findViewById(R.id.recycler);
        txtDownload = findViewById(R.id.txt_download);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        if (sharedPref.isDownloaded()) {
            loadDataFromDatabase();
        } else {
            getDataFromAPI();
        }
    }

    private void loadDataFromDatabase() {
        List<PhotoItem> dataFromDB = getPhotoFromDatabase();
        showDataRecycler(dataFromDB);
    }

    private void setTextDownload() {
        String isinya = String.valueOf(sharedPref.isDownloaded());
        txtDownload.setText(isinya);
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

    private void showDataRecycler(List<PhotoItem> data) {
        RecyclerAdapter adapter = new RecyclerAdapter(data, photoClickListener);
        recycler.setAdapter(adapter);
    }

    @Override
    public void onResponse(@NonNull Call<List<PhotoItem>> call, @NonNull Response<List<PhotoItem>> response) {
        if (response.body() != null) {
            changeVariableDownload();
            insertDatatoDatabase(response.body());
            showDataRecycler(response.body());
        }
        hideLoading();
    }

    @Override
    public void onFailure(@NonNull Call<List<PhotoItem>> call, @NonNull Throwable t) {
        hideLoading();
        Toast.makeText(this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        t.printStackTrace();
    }

    private void changeVariableDownload() {
        sharedPref.setDownload(true);
    }

    private void insertDatatoDatabase(List<PhotoItem> data) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        for (PhotoItem item : data) {
            String SQL_INSERT = String.format("INSERT INTO %s " +
                            "(%s,%s,%s,%s,%s,%s,%s,%s) " +
                            "VALUES(%s,'%s','%s','%s','%s','%s','%s','%s')",
                    Database.TABLE_PHOTO,

                    Database.COLUMN_ID,
                    Database.COLUMN_FORMAT,
                    Database.COLUMN_WIDTH,
                    Database.COLUMN_HEIGHT,
                    Database.COLUMN_FILENAME,
                    Database.COLUMN_AUTHOR,
                    Database.COLUMN_AUTHOR_URL,
                    Database.COLUMN_POST_URL,

                    item.getId(),
                    item.getFormat(),
                    String.valueOf(item.getWidth()),
                    String.valueOf(item.getHeight()),
                    item.getFilename(),
                    item.getAuthor().replace("'", ""),
                    item.getAuthor_url(),
                    item.getPost_url()
            );
            sqLiteDatabase.execSQL(SQL_INSERT);
        }
    }

    private List<PhotoItem> getPhotoFromDatabase() {
        ArrayList<PhotoItem> data = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        String SQL_SELECT_ALL = String.format("SELECT * FROM %s",
                Database.TABLE_PHOTO);
        Cursor cursor = sqLiteDatabase.rawQuery(SQL_SELECT_ALL, null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount(); i++) {
                PhotoItem item = new PhotoItem();
                item.setId(getIntValue(cursor, Database.COLUMN_ID));
                item.setFormat(getStringValue(cursor, Database.COLUMN_FORMAT));
                item.setWidth(getIntValue(cursor, Database.COLUMN_WIDTH));
                item.setHeight(getIntValue(cursor, Database.COLUMN_HEIGHT));
                item.setFilename(getStringValue(cursor, Database.COLUMN_FILENAME));
                item.setAuthor(getStringValue(cursor, Database.COLUMN_AUTHOR));
                item.setAuthor_url(getStringValue(cursor, Database.COLUMN_AUTHOR_URL));
                item.setPost_url(getStringValue(cursor, Database.COLUMN_POST_URL));

                data.add(item);
                cursor.moveToNext();
            }
        }

        cursor.close();
        return data;
    }

    private String getStringValue(Cursor record, String column) {
        try {
            int count = record.getColumnCount();
            for (int counter = 0; counter < count; counter++) {
                if (record.getColumnName(counter).equals(column)) {
                    if (record.getString(counter) != null)
                        return record.getString(counter);
                    else
                        break;
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private int getIntValue(Cursor record, String column) {
        try {
            int count = record.getColumnCount();
            for (int counter = 0; counter < count; counter++) {
                if (record.getColumnName(counter).equals(column)) {
                    if (record.getString(counter) != null)
                        return record.getInt(counter);
                    else
                        break;
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
