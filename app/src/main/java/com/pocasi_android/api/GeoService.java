package com.pocasi_android.api;

import com.pocasi_android.model.GeoDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeoService {

    @GET("/data/reverse-geocode-client")
    public Call<GeoDto> getGeoInformation(@Query("latitude") double latitude, @Query("longitude") double longitude, @Query("localityLanguage") String lang);
}
