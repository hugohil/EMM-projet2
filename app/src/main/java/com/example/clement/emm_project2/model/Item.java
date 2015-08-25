package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Created by Clement on 23/08/15.
 */
public class Item extends AppData {

    @JsonProperty("type")
    private String type;

    @JsonProperty("title")
    private String title;

    @JsonProperty("field_video")
    private Map<String, String> fieldVideo; // WWTFFF????

    @JsonProperty("duration")
    private Float duration;

    @JsonProperty("field_poster")
    private String fieldPoster;

    @JsonProperty("field_vignette")
    private Map<String, String> fieldVignette; // WWTFFF????


    @JsonProperty("field_fichier")
    private Map<String, String> fieldFichier; // WWTFFF????

    @JsonProperty("free")
    private boolean free;

    @JsonProperty("children")
    private String[] children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Map<String, String> getFieldVideo() {
        return fieldVideo;
    }

    public void setFieldVideo(Map<String, String> fieldVideo) {
        this.fieldVideo = fieldVideo;
    }

    public Float getDuration() {
        return duration;
    }

    public void setDuration(Float duration) {
        this.duration = duration;
    }

    public String getFieldPoster() {
        return fieldPoster;
    }

    public void setFieldPoster(String fieldPoster) {
        this.fieldPoster = fieldPoster;
    }

    public Map<String, String> getFieldVignette() {
        return fieldVignette;
    }

    public void setFieldVignette(Map<String, String> fieldVignette) {
        this.fieldVignette = fieldVignette;
    }

    public Map<String, String> getFieldFichier() {
        return fieldFichier;
    }

    public void setFieldFichier(Map<String, String> fieldFichier) {
        this.fieldFichier = fieldFichier;
    }

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String[] getChildren() {
        return children;
    }

    public void setChildren(String[] children) {
        this.children = children;
    }
}
