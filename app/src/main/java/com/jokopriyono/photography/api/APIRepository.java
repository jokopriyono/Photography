package com.jokopriyono.photography.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIRepository {
    @GET("list")
    Call<List<PhotoItem>> getAllPhotos();
}
