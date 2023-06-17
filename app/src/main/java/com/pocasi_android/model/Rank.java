package com.pocasi_android.model;

public class Rank{
    public Double importance;
    public Double confidence;
    public Double confidence_city_level;
    public String match_type;

    public Rank() {
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public Double getConfidence_city_level() {
        return confidence_city_level;
    }

    public void setConfidence_city_level(Double confidence_city_level) {
        this.confidence_city_level = confidence_city_level;
    }

    public String getMatch_type() {
        return match_type;
    }

    public void setMatch_type(String match_type) {
        this.match_type = match_type;
    }
}
