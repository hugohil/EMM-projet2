package com.example.clement.emm_project2.model;

import android.media.Image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class Author extends AppData {

    @JsonProperty("fullname")
    private String fullName;

    private String link;

    private Map<String, Image> picture;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
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

    public String toString() {
        return "Author = [fullname: "+ this.fullName + ", link: "+ this.link + "]";
    }
}
