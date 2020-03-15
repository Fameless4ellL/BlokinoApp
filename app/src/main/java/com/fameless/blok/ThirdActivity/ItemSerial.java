package com.fameless.blok.ThirdActivity;

public class ItemSerial {
    private String TitleSerial;
    private String TextSerial;
    private String DetailUrlSerial;

    public ItemSerial() {
    }

    public ItemSerial(String titleSerial, String textSerial, String detailUrlSerial) {
        TitleSerial = titleSerial;
        TextSerial = textSerial;
        DetailUrlSerial = detailUrlSerial;
    }

    public String getTitleSerial() {
        return TitleSerial;
    }

    public void setTitleSerial(String titleSerial) {
        TitleSerial = titleSerial;
    }

    public String getTextSerial() {
        return TextSerial;
    }

    public void setTextSerial(String textSerial) {
        TextSerial = textSerial;
    }

    public String getDetailUrlSerial() {
        return DetailUrlSerial;
    }

    public void setDetailUrlSerial(String detailUrlSerial) {
        DetailUrlSerial = detailUrlSerial;
    }
}
