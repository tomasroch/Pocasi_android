package com.pocasi_android.model;

public class Timezone{
    private String name;
    private String name_alt;
    private String offset_STD;
    private Integer offset_STD_seconds;
    private String offset_DST;
    private Integer offset_DST_seconds;
    private String abbreviation_STD;
    private String abbreviation_DST;

    public Timezone() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName_alt() {
        return name_alt;
    }

    public void setName_alt(String name_alt) {
        this.name_alt = name_alt;
    }

    public String getOffset_STD() {
        return offset_STD;
    }

    public void setOffset_STD(String offset_STD) {
        this.offset_STD = offset_STD;
    }

    public Integer getOffset_STD_seconds() {
        return offset_STD_seconds;
    }

    public void setOffset_STD_seconds(Integer offset_STD_seconds) {
        this.offset_STD_seconds = offset_STD_seconds;
    }

    public String getOffset_DST() {
        return offset_DST;
    }

    public void setOffset_DST(String offset_DST) {
        this.offset_DST = offset_DST;
    }

    public Integer getOffset_DST_seconds() {
        return offset_DST_seconds;
    }

    public void setOffset_DST_seconds(Integer offset_DST_seconds) {
        this.offset_DST_seconds = offset_DST_seconds;
    }

    public String getAbbreviation_STD() {
        return abbreviation_STD;
    }

    public void setAbbreviation_STD(String abbreviation_STD) {
        this.abbreviation_STD = abbreviation_STD;
    }

    public String getAbbreviation_DST() {
        return abbreviation_DST;
    }

    public void setAbbreviation_DST(String abbreviation_DST) {
        this.abbreviation_DST = abbreviation_DST;
    }
}
