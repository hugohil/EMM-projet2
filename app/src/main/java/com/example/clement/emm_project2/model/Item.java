package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
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
    private ArrayList<Map<String, String>> fieldVideo;

    @JsonProperty("duration")
    private Float duration;

    @JsonProperty("field_poster")
    private String fieldPoster;

    @JsonProperty("field_vignette")
    private ArrayList<Map<String, Object>> fieldVignette;

    @JsonProperty("field_files")
    private  String[] fieldFiles;

    @JsonProperty("free")
    private boolean free;

    @JsonProperty("nid")
    private int nid;

    @JsonProperty("children")
    private String[] children;

    @JsonProperty("nb_credits")
    private Float nbCredits;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("url_path")
    private String urlPath;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Float getNbCredits() {
        return nbCredits;
    }

    public void setNbCredits(Float nbCredits) {
        this.nbCredits = nbCredits;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public String[] getFieldFiles() {
        return fieldFiles;
    }

    public void setFieldFiles(String[] fieldFiles) {
        this.fieldFiles = fieldFiles;
    }

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

    public ArrayList<Map<String, String>> getFieldVideo() {
        return fieldVideo;
    }

    public void setFieldVideo(ArrayList<Map<String, String>> fieldVideo) {
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

    public ArrayList<Map<String, Object>> getFieldVignette() {
        return fieldVignette;
    }

    public void setFieldVignette(ArrayList<Map<String, Object>> fieldVignette) {
        this.fieldVignette = fieldVignette;
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