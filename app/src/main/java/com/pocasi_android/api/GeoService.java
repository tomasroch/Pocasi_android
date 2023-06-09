package com.pocasi_android.api;

import com.pocasi_android.model.GeoModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoService {

    @GET("/reverse-geocode-client ")
    public Call<GeoModel> getGeoInformation(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("localityLanguage") String lang);
}
