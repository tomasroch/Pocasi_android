package com.pocasi_android.model;

public class Query{
    private String text;
    private Parsed parsed;

    public Query() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Parsed getParsed() {
        return parsed;
    }

    public void setParsed(Parsed parsed) {
        this.parsed = parsed;
    }
}
