package com.pocasi_android.api;

import com.pocasi_android.model.AutoCompleteDto;
import com.pocasi_android.model.WeatherDto;

import java.io.IOException;

public class AutoCompleteServiceImpl {

    private final String BASE_URL = "https://api.geoapify.com";
    private final String API_KEY = "9e3ed3286ea846a59bd6df5c5662d8ef";

    private final AutoCompleteService autoCompleteService;

    public AutoCompleteServiceImpl() {
        this.autoCompleteService = RestHttpClient.createService(AutoCompleteService.class);
    }

    public AutoCompleteDto getAutoComplete(String text, String lang){
        try {
            return autoCompleteService.getAutoComplete(BASE_URL + "/v1/geocode/autocomplete", text, API_KEY, lang).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
