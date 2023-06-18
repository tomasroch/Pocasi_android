package com.pocasi_android.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.pocasi_android.R;
import com.pocasi_android.api.OpenWeatherServiceImpl;
import com.pocasi_android.db.DBManager;
import com.pocasi_android.enums.Language;
import com.pocasi_android.model.WeatherDto;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    private Language language;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager = new DBManager(this);
        dbManager.open();

        language = Language.valueOf(getIntent().getStringExtra("language"));

        Integer id = getIntent().getIntExtra("id", -1);
        String cityName = getIntent().getStringExtra("city");
        Double longitude = getIntent().getDoubleExtra("longitude", -1);
        Double latitude = getIntent().getDoubleExtra("latitude", -1);

        Button buttonSmazat = findViewById(R.id.buttonSmazat);
        ImageView imageView = findViewById(R.id.pocasiImgView);
        TextView misto = findViewById(R.id.mistoTxt);
        TextView pocasi = findViewById(R.id.pocasiTxt);
        TextView aktualniTeplota = findViewById(R.id.aktualniTeplotaTxt);
        TextView pocitovaTeplota = findViewById(R.id.pocitovaTeplotaTxt);
        TextView minimalniTeplota = findViewById(R.id.minimalniTeplotaTxt);
        TextView maximalniTeplota = findViewById(R.id.maximalniTeplotaTxt);
        TextView vlhkost = findViewById(R.id.vlhkostTxt);
        TextView rychlostVetru = findViewById(R.id.rychlostVetruTxt);
        TextView tlak = findViewById(R.id.tlakTxt);
        TextView vychodSlunce = findViewById(R.id.vychodSlunceTxt);
        TextView zapadSlunce = findViewById(R.id.zapadSlunceTxt);
        TextView viditelnost = findViewById(R.id.viditelnostTxt);

        misto.setText("Místo: " + cityName);


        // handle data
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            OpenWeatherServiceImpl openWeatherService = new OpenWeatherServiceImpl();

            WeatherDto weatherDto = openWeatherService.getBasicWeather(latitude, longitude, language.getAlternativeName());
            Bitmap icon = openWeatherService.getWeatherIcon(weatherDto.getWeather().get(0).getIcon());

            SimpleDateFormat sdf = new  SimpleDateFormat("HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("Europe/Prague"));
            handler.post(() -> {
                imageView.setImageBitmap(icon);
                pocasi.setText("Počasí: " + StringUtils.capitalize(weatherDto.getWeather().get(0).getDescription()));
                aktualniTeplota.setText("Aktuální teplota: " + String.format("%s°", Math.round(weatherDto.getMain().getTemp())));
                pocitovaTeplota.setText("Poctivoá teplota: " + String.format("%s°", Math.round(weatherDto.getMain().getFeels_like())));
                minimalniTeplota.setText("Minimální teplota: " + String.format("%s°", Math.round(weatherDto.getMain().getTemp_min())));
                maximalniTeplota.setText("Maximální teplota: " + String.format("%s°", Math.round(weatherDto.getMain().getTemp_max())));
                vlhkost.setText("Vlhkost: " + String.format("%s%%", Math.round(weatherDto.getMain().getHumidity())));
                rychlostVetru.setText("Rychlost větru: " + String.format("%sm/s", Math.round(weatherDto.getWind().getSpeed())));
                tlak.setText("Tlak: " + String.format("%shPa", Math.round(weatherDto.getMain().getPressure())));
                //dává nerelevantní infomrace
                //vychodSlunce.setText("Východ slunce: " + sdf.format(new Date(weatherDto.getSys().getSunrise()*1000)));
                //zapadSlunce.setText("Západ slunce: " + sdf.format(new Date(weatherDto.getSys().getSunset()*1000)));
                System.out.println(sdf.format(new Date()));
                viditelnost.setText("Viditelnost: " + String.format("%sm", Math.round(weatherDto.getVisibility())));

            });
        });



        buttonSmazat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.delete(id);
                dbManager.close();
                DetailActivity.this.finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}