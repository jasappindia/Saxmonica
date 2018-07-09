package com.musicapplication.models;

import java.io.Serializable;

/**
 * Created by PRATEEK on 12/6/2016.
 */
public class DrawerItem implements Serializable {
    private boolean showNotify;
    private String title;
    Integer drawable;


    public DrawerItem() {

    }

    public DrawerItem(boolean showNotify, String title, Integer drawable) {
        this.showNotify = showNotify;
        this.title = title;
        this.drawable = drawable;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDrawable() {
        return drawable;
    }

    public void setDrawable(Integer drawable) {
        this.drawable = drawable;
    }
}