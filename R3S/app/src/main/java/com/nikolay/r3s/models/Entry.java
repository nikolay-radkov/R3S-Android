package com.nikolay.r3s.models;

public class Entry implements IModel{
    private int id;
    private String title;
    private String createdAt;
    private String content;
    private int subscriptionId;
    private String link;

    public Entry(int subscriptionId, String title, String createdAt,
                 String content, String link) {
        this.subscriptionId = subscriptionId;
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
        this.link = link;
    }

    public Entry() {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() { return link; }

    public void setLink(String link) { this.link = link; }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public boolean hasSubscriptionId(int id) {
        return this.getSubscriptionId() == id;
    }
}
