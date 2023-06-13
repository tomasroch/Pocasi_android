package com.pocasi_android;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.pocasi_android.api.GeoService;
import com.pocasi_android.api.GeoServiceImpl;
import com.pocasi_android.api.OpenWeatherServiceImpl;
import com.pocasi_android.api.RestHttpClient;
import com.pocasi_android.enums.Language;
import com.pocasi_android.model.GeoDto;
import com.pocasi_android.model.WeatherDto;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private Language language = Language.CS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        TextView textViewTeplota = findViewById(R.id.teploMainTextView);
        textViewTeplota.setText("");
        TextView textViewPopis = findViewById(R.id.popisPocasiTextView);
        textViewPopis.setText("");
        TextView textViewCity = findViewById(R.id.cityTextView);
        textViewCity.setText("");

        ProgressBar progressBar = findViewById(R.id.progressBar);

        // check and grant permission
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }

        // handle data
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            GeoServiceImpl geoService = new GeoServiceImpl();
            OpenWeatherServiceImpl openWeatherService = new OpenWeatherServiceImpl();

            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location == null){
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }


            GeoDto geoDto = geoService.getGeoInformation(location.getLatitude(),location.getLongitude(), language.name());
            WeatherDto weatherDto = openWeatherService.getBasicWeather(location.getLatitude(),location.getLongitude(), language.getAlternativeName());
            Bitmap icon = openWeatherService.getWeatherIcon(weatherDto.getWeather().get(0).getIcon());
            handler.post(() -> {
                ImageView imageView = findViewById(R.id.pocasiImageView);
                imageView.setImageBitmap(icon);
                textViewTeplota.setText(String.format("%s°", Math.round(weatherDto.getMain().getTemp())));
                textViewPopis.setText(StringUtils.capitalize(weatherDto.getWeather().get(0).getDescription()));
                textViewCity.setText(geoDto.getCity());
                progressBar.setVisibility(View.GONE);
            });
        });

    }

    @Deprecated
    private void asyncTask(){
        //TODO rozdělit každý volání na vlastní Thread
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                GeoServiceImpl geoService = new GeoServiceImpl();
                OpenWeatherServiceImpl openWeatherService = new OpenWeatherServiceImpl();

                GeoDto geoDto = geoService.getGeoInformation(50.0215700,15.1837200, "cs");
                WeatherDto weatherDto = openWeatherService.getBasicWeather(50.0215700,15.1837200, "cs");
                Bitmap icon = openWeatherService.getWeatherIcon(weatherDto.getWeather().get(0).getIcon());
                System.out.println(geoDto);
            }
        });
        thread.start();
    }

    @Override public void onRequestPermissionsResult(int requestCode,
                                                     String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, "Permissions granted", Toast.LENGTH_SHORT).show();
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(MainActivity.this, "For full functionality grant requested permissions", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }
}