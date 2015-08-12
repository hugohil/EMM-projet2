package com.example.clement.emm_project2.model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by Clement on 10/08/15.
 */
public class Category extends AppData{
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

    @JsonProperty("subcategories")
    private List<SubCategory> subCategories;

    @JsonProperty("__v")
    private String v;

    public boolean getActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSubCategories() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.subCategories);
        } catch (JsonProcessingException e){
            Log.d(Category.class.getSimpleName(), e.toString());
            return null;
        }
    }

    public <T> T setSubCategories(final TypeReference<T> type, String subCategories) {
        T data = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(subCategories, type);
            this.subCategories = (List<SubCategory>) data;
        } catch (Exception e){
            Log.d(Category.class.getSimpleName(), e.toString());
        }
        return data;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public String toString() {
        return "[" + title + " ," + description + "]";
    }
}
