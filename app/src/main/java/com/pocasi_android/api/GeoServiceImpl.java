package com.pocasi_android.api;

import com.pocasi_android.model.GeoDto;

public class GeoServiceImpl {

    private final String BASE_URL = "https://api.bigdatacloud.net";

    private final GeoService geoService;

    public GeoServiceImpl() {
        this.geoService = RestHttpClient.createService(GeoService.class);
    }

    public GeoDto getGeoInformation(double latitude, double longitude, String lang){
        try  {
            return geoService.getGeoInformation(BASE_URL + "/data/reverse-geocode-client",50.0215700,15.1837200, "cs").execute().body();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
