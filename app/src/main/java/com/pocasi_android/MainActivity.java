package com.pocasi_android;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.pocasi_android.api.GeoService;
import com.pocasi_android.api.RestHttpClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GeoService geoService = RestHttpClient.createService(GeoService.class);
    }
}