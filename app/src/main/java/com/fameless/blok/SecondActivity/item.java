package com.fameless.blok.SecondActivity;

public class item {
    private String week;
    private String title;
    private String img;
    private String text;
    private String detailUrl;
    private String grade;

    public item() {
    }

    public item(String week, String title, String img, String text, String detailUrl, String grade) {
        this.week = week;
        this.title = title;
        this.img = img;
        this.text = text;
        this.detailUrl = detailUrl;
        this.grade = grade;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
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
}

