package com.fameless.blok.fourthActivity;

public class ItemChooser {
    private String VideoUrl;
    private String ChooserLang;
    private String VideoUrlDetail;

    public ItemChooser() {
    }

    public ItemChooser(String videoUrl, String chooserLang, String videoUrlDetail) {
        VideoUrl = videoUrl;
        ChooserLang = chooserLang;
        VideoUrlDetail = videoUrlDetail;
    }

    public String getVideoUrlDetail() {
        return VideoUrlDetail;
    }

    public void setVideoUrlDetail(String videoUrlDetail) {
        VideoUrlDetail = videoUrlDetail;
    }

    public String getVideoUrl() {
        return VideoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUrl = videoUrl;
    }

    public String getChooserLang() {
        return ChooserLang;
    }

    public void setChooserLang(String chooserLang) {
        ChooserLang = chooserLang;
    }
}
