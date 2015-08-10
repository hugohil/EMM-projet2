package com.example.clement.emm_project2.model;

import android.media.Image;

import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class Author {

    private String fullname;

    private String link;

    private Map<String, Image> picture;

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Map<String, Image> getPicture() {
        return picture;
    }

    public void setPicture(Map<String, Image> picture) {
        this.picture = picture;
    }
}
