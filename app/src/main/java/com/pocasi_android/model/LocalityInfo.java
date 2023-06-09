package com.pocasi_android.model;

import java.util.List;

public class LocalityInfo {

    private String likelyLand;

    private List<InfoObject> administrative;

    private List<InfoObject> informative;

    public LocalityInfo() {    }

    public String getLikelyLand() {
        return likelyLand;
    }

    public void setLikelyLand(String likelyLand) {
        this.likelyLand = likelyLand;
    }

    public List<InfoObject> getAdministrative() {
        return administrative;
    }

    public void setAdministrative(List<InfoObject> administrative) {
        this.administrative = administrative;
    }

    public List<InfoObject> getInformative() {
        return informative;
    }

    public void setInformative(List<InfoObject> informative) {
        this.informative = informative;
    }
}
