package com.pocasi_android;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import com.pocasi_android.api.GeoService;
import com.pocasi_android.api.GeoServiceImpl;
import com.pocasi_android.api.OpenWeatherServiceImpl;
import com.pocasi_android.api.RestHttpClient;
import com.pocasi_android.model.GeoDto;
import com.pocasi_android.model.WeatherDto;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        /*if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location location2 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);*/

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {

                GeoServiceImpl geoService = new GeoServiceImpl();
                OpenWeatherServiceImpl openWeatherService = new OpenWeatherServiceImpl();

                GeoDto geoDto = geoService.getGeoInformation(50.0215700,15.1837200, "cs");
                WeatherDto weatherDto = openWeatherService.getBasicWeather(50.0215700,15.1837200, "cs");
                System.out.println(geoDto);
            }
        });
        thread.start();
    }
}