package com.pocasi_android.api;

import com.pocasi_android.model.GeoDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GeoService {

    @GET
    Call<GeoDto> getGeoInformation(@Url String url, @Query("latitude") double latitude, @Query("longitude") double longitude,
                                   @Query("localityLanguage") String lang);
}
