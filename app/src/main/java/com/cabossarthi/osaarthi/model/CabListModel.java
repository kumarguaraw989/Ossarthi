package com.cabossarthi.osaarthi.model;

public class CabListModel {
    String url;
    String price;
    String name;

    public CabListModel(String url, String price, String name) {
        this.url = url;
        this.price = price;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
