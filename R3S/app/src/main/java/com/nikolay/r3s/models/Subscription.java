package com.nikolay.r3s.models;

import java.util.ArrayList;

public class Subscription implements IModel {
    private String id;
    private String favicon;
    private String name;
    private String url;
    private String updatedAt;
    private ArrayList<Entry> entries;

    public Subscription(String name, String url, String favicon, String updatedAt) {
        this.updatedAt = updatedAt;
        this.favicon = favicon;
        this.name = name;
        this.url = url;
        this.entries = new ArrayList<Entry>();
    }

    public Subscription() {

    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getFavicon() {
        return favicon;
    }

    public void setFavicon(String favicon) {
        this.favicon = favicon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public ArrayList<Entry> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
