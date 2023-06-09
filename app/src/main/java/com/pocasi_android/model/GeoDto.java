package com.pocasi_android.model;

public class GeoDto {

    private Double latitude;
    private Double longitude;

    private String continent;

    private String lookupSource;

    private String continentCode;

    private String localityLanguageRequested;

    private String city;
    private String countryName;

    private String countryCode;

    private String postcode;

    private String principalSubdivision;

    private String principalSubdivisionCode;

    private String plusCode;

    private String locality;

    private LocalityInfo localityInfo;

    public GeoDto() {    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getLookupSource() {
        return lookupSource;
    }

    public void setLookupSource(String lookupSource) {
        this.lookupSource = lookupSource;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public void setContinentCode(String continentCode) {
        this.continentCode = continentCode;
    }

    public String getLocalityLanguageRequested() {
        return localityLanguageRequested;
    }

    public void setLocalityLanguageRequested(String localityLanguageRequested) {
        this.localityLanguageRequested = localityLanguageRequested;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPrincipalSubdivision() {
        return principalSubdivision;
    }

    public void setPrincipalSubdivision(String principalSubdivision) {
        this.principalSubdivision = principalSubdivision;
    }

    public String getPrincipalSubdivisionCode() {
        return principalSubdivisionCode;
    }

    public void setPrincipalSubdivisionCode(String principalSubdivisionCode) {
        this.principalSubdivisionCode = principalSubdivisionCode;
    }

    public String getPlusCode() {
        return plusCode;
    }

    public void setPlusCode(String plusCode) {
        this.plusCode = plusCode;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public LocalityInfo getLocalityInfo() {
        return localityInfo;
    }

    public void setLocalityInfo(LocalityInfo localityInfo) {
        this.localityInfo = localityInfo;
    }
}
