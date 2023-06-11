package com.pocasi_android.api;

import com.pocasi_android.model.WeatherDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface OpenWeatherService {

    @GET
    Call<WeatherDto> getBasicWeather(@Url String url, @Query("lat") double latitude, @Query("lon") double longitude,
                                     @Query("lang") String lang, @Query("units") String units, @Query("appid") String appid);
}
