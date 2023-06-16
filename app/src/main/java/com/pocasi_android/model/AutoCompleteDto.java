package com.pocasi_android.model;

import java.util.List;

public class AutoCompleteDto {

    private String type;
    private List<Feature> features;
    private Query query;

        public AutoCompleteDto() {
        }

        public String getType() {
                return type;
        }

        public void setType(String type) {
                this.type = type;
        }

        public List<Feature> getFeatures() {
                return features;
        }

        public void setFeatures(List<Feature> features) {
                this.features = features;
        }

        public Query getQuery() {
                return query;
        }

        public void setQuery(Query query) {
                this.query = query;
        }
}
