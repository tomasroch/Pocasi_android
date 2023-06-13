package com.pocasi_android.enums;

public enum Language {
    CS("cz"),
    EN("en");

    private String alternativeName;

    Language(String alternativeName) {        this.alternativeName = alternativeName;    }

    public String getAlternativeName() {
        return alternativeName;
    }
}
