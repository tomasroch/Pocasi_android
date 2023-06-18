package com.pocasi_android.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.pocasi_android.R;
import com.pocasi_android.api.AutoCompleteServiceImpl;
import com.pocasi_android.api.OpenWeatherServiceImpl;
import com.pocasi_android.db.DBManager;
import com.pocasi_android.db.DatabaseHelper;
import com.pocasi_android.enums.Language;
import com.pocasi_android.model.AutoCompleteDto;
import com.pocasi_android.model.Properties;
import com.pocasi_android.model.WeatherDto;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SeznamActivity extends AppCompatActivity {

    private Language language;

    private DBManager dbManager;

    private SimpleCursorAdapter adapter;

    final String[] from2 = new String[]{DatabaseHelper.CITY, DatabaseHelper.LAST_TEMP};

    final int[] to2 = new int[]{android.R.id.text1, android.R.id.text2};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seznam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbManager = new DBManager(this);
        dbManager.open();
        language = Language.valueOf(getIntent().getStringExtra("language"));
        AutoCompleteTextView autoCompleteView = findViewById(R.id.autoCompleteTextView);
        Button addButton = findViewById(R.id.buttonAdd);
        autoCompleteView.setHint("Zadej název města");

        initData();
        refreshData();

        HashMap<String, Properties> cityStore = new HashMap<>();
        // below listeners only
        autoCompleteView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addButton.setEnabled(true);
            }
        });
        autoCompleteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addButton.setEnabled(false);
                if (autoCompleteView.isPerformingCompletion()) {
                    // An item has been selected from the list. Ignore.
                } else {
                    if (i + i2 >= 3) {
                        // handle data
                        ExecutorService executor = Executors.newSingleThreadExecutor();
                        Handler handler = new Handler(Looper.getMainLooper());
                        executor.execute(() -> {
                            //Background work here
                            AutoCompleteServiceImpl autoCompleteService = new AutoCompleteServiceImpl();
                            AutoCompleteDto dto = autoCompleteService.getAutoComplete(charSequence.toString(), language.name().toLowerCase());
                            dto.getFeatures()
                               .forEach(f -> {
                                   if (f.properties.getCity() != null && f.properties.getCity().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                                       cityStore.put(f.properties.getCity() + "(" + f.properties.getAddress_line2() + ")", f.getProperties());
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

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Properties property = cityStore.get(autoCompleteView.getText().toString());
                dbManager.insert(property.getCity(), property.getLon(), property.getLat());
                refreshData();
                autoCompleteView.setText("");
            }
        });
    }

    private void initData() {
        Cursor cursor = dbManager.fetch();
        ListView listView = findViewById(R.id.seznamListView);
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, from2, to2, 0);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        refreshData();
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor cursor = dbManager.fetch();
                cursor.moveToPosition(i);
                int id = cursor.getInt(0);
                String city = cursor.getString(1);
                String longitude = cursor.getString(2);
                String latitude = cursor.getString(3);

                Intent intent = new Intent(SeznamActivity.this, DetailActivity.class);
                intent.putExtra("language", language.name());
                intent.putExtra("id", id);
                intent.putExtra("city", city);
                intent.putExtra("longitude", longitude);
                intent.putExtra("latitude", latitude);


                activityResultLauncher.launch(intent);
            }
        });
    }

    private void refreshData() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            //Background work here
            OpenWeatherServiceImpl openWeatherService = new OpenWeatherServiceImpl();
            Cursor cursor = dbManager.fetch();
            cursor.moveToPosition(-1);
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                double longitude = cursor.getDouble(2);
                double latitude = cursor.getDouble(3);
                WeatherDto weatherDto = openWeatherService.getBasicWeather(latitude, longitude, language.getAlternativeName());
                dbManager.update(id, String.format("%s°", Math.round(weatherDto.getMain().getTemp())), Calendar.getInstance().getTime().toString());
            }
            Cursor finalCursor = dbManager.fetch();

            handler.post(() -> {
                ListView listView = findViewById(R.id.seznamListView);
                adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, finalCursor, from2, to2, 0);
                adapter.notifyDataSetChanged();
                listView.setAdapter(adapter);
            });
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dbManager.close();
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}