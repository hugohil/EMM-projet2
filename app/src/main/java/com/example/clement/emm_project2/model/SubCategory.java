package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Clement on 10/08/15.
 */
public class SubCategory extends AppData{
    @JsonProperty("_id")
    private Long id;

    @JsonProperty("tid")
    private int tid;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("image_url")
    private String imageURL;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("__v")
    private String v;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
