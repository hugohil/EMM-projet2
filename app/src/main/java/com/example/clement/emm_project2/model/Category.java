package com.example.clement.emm_project2.model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Created by Clement on 10/08/15.
 */
public class Category extends AppData{
    private final String TAG = Category.class.getSimpleName();

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
            Log.d(TAG, e.toString());
            return null;
        }
    }

    public void setSubCategories(String subCategories) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            this.subCategories = mapper.readValue(subCategories, List.class);
        } catch (Exception e){
            Log.d(TAG, e.toString());
        }
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
