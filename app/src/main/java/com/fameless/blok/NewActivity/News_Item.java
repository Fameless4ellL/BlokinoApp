package com.fameless.blok.NewActivity;

public class News_Item {
    private String title;
    private String season;
    private String serial;
    private String txt;
    private String detailTitleUrl;
    private String detailSerialUrl;

    public News_Item() {
    }

    public News_Item(String title, String season, String serial, String txt, String detailTitleUrl, String detailSerialUrl) {
        this.title = title;
        this.season = season;
        this.serial = serial;
        this.txt = txt;
        this.detailTitleUrl = detailTitleUrl;
        this.detailSerialUrl = detailSerialUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getDetailTitleUrl() {
        return detailTitleUrl;
    }

    public void setDetailTitleUrl(String detailTitleUrl) {
        this.detailTitleUrl = detailTitleUrl;
    }

    public String getDetailSerialUrl() {
        return detailSerialUrl;
    }

    public void setDetailSerialUrl(String detailSerialUrl) {
        this.detailSerialUrl = detailSerialUrl;
    }
}
