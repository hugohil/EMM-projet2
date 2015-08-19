package com.example.clement.emm_project2.model;

import android.util.Log;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class Formation extends AppData {
    /*
     * Represents a formation item as described in the api doc
     */
    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("product_url")
    private String productUrl;

    @JsonProperty("ean13")
    private String ean;

    @JsonProperty("price")
    private Float price;

    @JsonProperty("description")
    private String description;

    @JsonProperty("duration")
    private int duration;

    @JsonProperty("objectives")
    private String objectives;

    @JsonProperty("prerequisites")
    private String prerequisites;

    @JsonProperty("qcm")
    private String qcm;

    @JsonProperty("teaser_text")
    private String teaserText;

    @JsonProperty("category")
    private String catId;

    @JsonProperty("subcategory")
    private String subCatId;

    @JsonProperty("teaser")
    private String teaser;

    @JsonProperty("publishedDate")
    private String publishedDate;

    @JsonProperty("poster")
    private String poster;

    @JsonProperty("authors")
    private List<Author> authors;

    @JsonProperty("images")
    private Map<String, Map<String, String>> images;

    @JsonProperty("free")
    private boolean free;

    @JsonProperty("rating")
    private Map<String, Float> rating;

    @JsonProperty("video_count")
    private int videoCount;

    @JsonProperty("active")
    private boolean active;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getQcm() {
        return qcm;
    }

    public void setQcm(String qcm) {
        this.qcm = qcm;
    }

    public String getTeaserText() {
        return teaserText;
    }

    public void setTeaserText(String teaserText) {
        this.teaserText = teaserText;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getSubCatId() {
        return subCatId;
    }

    public void setSubCatId(String subCatId) {
        this.subCatId = subCatId;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getAuthors() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.authors);
        } catch (JsonProcessingException e){
            Log.d(Author.class.getSimpleName(), e.toString());
            return null;
        }
    }

    public <T> T setAuthors(final TypeReference<T> type, String authors) {
        T data = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(authors, type);
            this.authors = (List<Author>) data;
        } catch (Exception e){
            Log.d(Author.class.getSimpleName(), e.toString());
        }
        return data;
    }

    public String getImages() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.images);
        } catch (JsonProcessingException e){
            Log.d(Category.class.getSimpleName(), e.toString());
            return null;
        }
    }

    public <T> T setImages(final TypeReference<T> type, String images) {
        T data = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(images, type);
            this.images = (Map<String, Map<String, String>>) data;
        } catch (Exception e){
            Log.d(Category.class.getSimpleName(), e.toString());
        }
        return data;
    }

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public String getRating() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this.rating);
        } catch (JsonProcessingException e){
            Log.d(Category.class.getSimpleName(), e.toString());
            return null;
        }
    }

    public <T> T setRating(final TypeReference<T> type, String rating) {
        T data = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            data = mapper.readValue(rating, type);
            this.rating = (Map<String, Float>) data;
        } catch (Exception e){
            Log.d(Category.class.getSimpleName(), e.toString());
        }
        return data;
    }

    public int getVideoCount() {
        return videoCount;
    }

    public void setVideoCount(int videoCount) {
        this.videoCount = videoCount;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }


}
