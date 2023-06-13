package com.pocasi_android.api;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.pocasi_android.model.WeatherDto;
import retrofit2.Call;

import java.io.IOException;
import java.net.URL;

public class OpenWeatherServiceImpl {
    //TODO udělat tyhle stringy z properties
    private final String BASE_URL = "https://api.openweathermap.org";
    private final String API_KEY = "82357571f08c3035651f1dbeaba159da";

    private final OpenWeatherService openWeatherService;

    public OpenWeatherServiceImpl() {
        openWeatherService = RestHttpClient.createService(OpenWeatherService.class);
    }

    public WeatherDto getBasicWeather(double latitude, double longitude, String lang){
        try {
            return openWeatherService.getBasicWeather(BASE_URL + "/data/2.5/weather", latitude, longitude, lang, "metric", API_KEY).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getWeatherIcon(String icon){
        Bitmap mIcon11 = null;
        try {
            mIcon11 = BitmapFactory.decodeStream(new URL("https://openweathermap.org/img/wn/"+ icon +"@2x.png").openConnection().getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }


}
