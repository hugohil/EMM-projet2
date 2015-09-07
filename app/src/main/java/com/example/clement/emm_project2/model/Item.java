package com.example.clement.emm_project2.model;

import com.example.clement.emm_project2.util.FormationCustomDeserializer;
import com.example.clement.emm_project2.util.ItemCustomDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 23/08/15.
 */
@JsonDeserialize(using = ItemCustomDeserializer.class)
public class Item extends AppData implements Serializable {

    private String title;
    private String type;
    private int nid;
    private int nbCredits;
    private String fieldPoster;
    private String mongoId;
    private List<String> childrens;
    private boolean free;
    private List<Map<String, Object>> fieldFiles;
    private List<Map<String, Object>> fieldVignette;
    private List<Map<String, Object>> fieldVideo;
    private boolean active;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public int getNbCredits() {
        return nbCredits;
    }

    public void setNbCredits(int nbCredits) {
        this.nbCredits = nbCredits;
    }

    public String getFieldPoster() {
        return fieldPoster;
    }

    public void setFieldPoster(String fieldPoster) {
        this.fieldPoster = fieldPoster;
    }

    public String getMongoId() {
        return mongoId;
    }

    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }

    public List<String> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<String> childrens) {
        this.childrens = childrens;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public List<Map<String, Object>> getFieldFiles() {
        return fieldFiles;
    }

    public void setFieldFiles(List<Map<String, Object>> fieldFiles) {
        this.fieldFiles = fieldFiles;
    }

    public List<Map<String, Object>> getFieldVignette() {
        return fieldVignette;
    }

    public void setFieldVignette(List<Map<String, Object>> fieldVignette) {
        this.fieldVignette = fieldVignette;
    }

    public List<Map<String, Object>> getFieldVideo() {
        return fieldVideo;
    }

    public void setFieldVideo(List<Map<String, Object>> fieldVideo) {
        this.fieldVideo = fieldVideo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}