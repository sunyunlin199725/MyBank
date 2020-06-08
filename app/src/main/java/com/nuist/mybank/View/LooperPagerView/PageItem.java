package com.nuist.mybank.View.LooperPagerView;

public class PageItem {
    private  String title;//图片title
    private  String picResID;//图片url
    private  int piclocalID;//本地图片url

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicResID() {
        return picResID;
    }

    public void setPicResID(String picResID) {
        this.picResID = picResID;
    }

    public int getPiclocalID() {
        return piclocalID;
    }

    public void setPiclocalID(int piclocalID) {
        this.piclocalID = piclocalID;
    }

    public PageItem(String title, String picResID) {
        this.title = title;
        this.picResID = picResID;
    }
    public PageItem() {
    }
}
