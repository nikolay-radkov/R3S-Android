package com.nikolay.r3s.models;

public class Subscription implements IModel {
    private int id;
    private String favicon;
    private String name;
    private String url;
    private String updatedAt;

    public Subscription(String name, String url, String favicon, String updatedAt) {
        this.updatedAt = updatedAt;
        this.favicon = favicon;
        this.name = name;
        this.url = url;
    }

    public Subscription() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
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

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
