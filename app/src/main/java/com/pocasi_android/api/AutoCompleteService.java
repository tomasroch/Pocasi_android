package com.pocasi_android.api;

import com.pocasi_android.model.AutoCompleteDto;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AutoCompleteService {

    @GET
    Call<AutoCompleteDto> getAutoComplete(@Url String url, @Query("text") String text, @Query("apiKey") String apiKey, @Query("lang") String lang);

}
