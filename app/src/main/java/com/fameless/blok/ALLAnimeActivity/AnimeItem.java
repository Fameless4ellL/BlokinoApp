package com.fameless.blok.ALLAnimeActivity;

import java.io.Serializable;

public class AnimeItem implements Serializable {
    private String title;
    private String img;
    private String text;
    private String detailUrl;
    private String grade;


    public AnimeItem() {
    }

    public AnimeItem(String title, String img, String text, String detailUrl, String grade) {
        this.title = title;
        this.img = img;
        this.text = text;
        this.detailUrl = detailUrl;
        this.grade = grade;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
