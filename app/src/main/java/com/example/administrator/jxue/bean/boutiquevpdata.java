package com.example.administrator.jxue.bean;

import java.util.List;

/**
 * Created by Administrator on 2015-5-2.
 */
public class boutiquevpdata {
    private String imageUrl,title;
    private List<Marks> marks;
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Marks> getMarks() {
        return marks;
    }

    public void setMarks(List<Marks> marks) {
        this.marks = marks;
    }
}
