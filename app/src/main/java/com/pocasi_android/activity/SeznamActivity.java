package com.pocasi_android.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.pocasi_android.R;
import com.pocasi_android.api.AutoCompleteServiceImpl;
import com.pocasi_android.api.GeoServiceImpl;
import com.pocasi_android.api.OpenWeatherServiceImpl;
import com.pocasi_android.enums.Language;
import com.pocasi_android.model.AutoCompleteDto;
import com.pocasi_android.model.GeoDto;
import com.pocasi_android.model.Properties;
import com.pocasi_android.model.WeatherDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class SeznamActivity extends AppCompatActivity {

    private Language language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam);
        language = Language.valueOf(getIntent().getStringExtra("language"));
        AutoCompleteTextView autoCompleteView = findViewById(R.id.autoCompleteTextView);
        autoCompleteView.setHint("Zadej název města");

        autoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                findViewById(R.id.buttonAdd).setEnabled(true);
            }
        });
        autoCompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                System.out.println(charSequence);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                findViewById(R.id.buttonAdd).setEnabled(false);
                if (i + i2 >= 3){
                    // handle data
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    Handler handler = new Handler(Looper.getMainLooper());
                    executor.execute(() -> {
                        //Background work here
                        AutoCompleteServiceImpl autoCompleteService = new AutoCompleteServiceImpl();
                        AutoCompleteDto dto = autoCompleteService.getAutoComplete(charSequence.toString(), language.name().toLowerCase());
                        HashMap<String, Properties> cityStore = new HashMap<>();
                        dto.getFeatures()
                           .forEach(f -> {
                               if (f.properties.getCity() != null && f.properties.getCity().toLowerCase().contains(charSequence.toString().toLowerCase())){
                                   cityStore.put(f.properties.getCity() + "("+f.properties.getAddress_line2()+")", f.getProperties());
                               }
                           });
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(SeznamActivity.this,
                                android.R.layout.simple_dropdown_item_1line, new ArrayList<>(cityStore.keySet()));
                        handler.post(() -> {
                            autoCompleteView.setAdapter(adapter);
                            autoCompleteView.showDropDown();
                        });
                    });

                } else {
                    autoCompleteView.setAdapter(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




    }

}