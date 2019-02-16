package com.jokopriyono.photography;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jokopriyono.photography.api.PhotoItem;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView imgPhoto;
    private TextView txtAuthor;
    private TextView txtFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPhoto = findViewById(R.id.img_photo);
        txtAuthor = findViewById(R.id.txt_author);
        txtFileName = findViewById(R.id.txt_filename);

        if (getIntent() != null) {
            if (getIntent().getExtras() != null) {
                PhotoItem data = (PhotoItem) getIntent().getSerializableExtra("data");
                if (data != null) {
                    showDataPhoto(data);
                }
            }
        }

    }

    private void showDataPhoto(PhotoItem data) {
        txtAuthor.setText(data.getAuthor());
        txtFileName.setText(data.getFilename());

        String url = "https://picsum.photos/200?image=" + data.getId();
        Picasso.get().load(url).into(imgPhoto);
    }
}
