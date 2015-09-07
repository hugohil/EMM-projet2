package com.example.clement.emm_project2.model;

import android.util.Log;

import com.example.clement.emm_project2.util.FormationCustomDeserializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
@JsonDeserialize(using = FormationCustomDeserializer.class)
public class Formation extends AppData {
    /*
     * Represents a formation item as described in the api doc
     */
    public enum FORMATION_TYPE {
        book, chapter, video
    }

    @JsonProperty("title")
    private String title;

    @JsonProperty("subtitle")
    private String subtitle;

    @JsonProperty("product_url")
    private String productUrl;

    @JsonProperty("ean13")
    private String ean;

    @JsonProperty("type")
    private String type;

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

    @JsonProperty("can_download")
    private boolean canDownload;

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

    @JsonProperty("__v")
    private String v;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("items")
    private List<Item> items;

    @JsonProperty("children")
    private List<String> children;

    @JsonProperty("teaser_info")
    private Map<String, String> teaserInfo;

    @JsonProperty("url_path")
    private String urlPath;

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Map<String, String> getTeaserInfo() {
        return teaserInfo;
    }

    public void setTeaserInfo(Map<String, String> teaserInfo) {
        this.teaserInfo = teaserInfo;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public boolean getCanDownload() {
        return canDownload;
    }

    public void setCanDownload(boolean canDownload) {
        this.canDownload = canDownload;
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

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Map<String, Map<String, String>> getImages() {
        return images;
    }

    public void setImages(Map<String, Map<String, String>> images) {
        this.images = images;
    }

    public boolean getFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }

    public Map<String, Float> getRating() {
        return this.rating;
    }

    public void setRating(Map<String, Float> rating) {
        this.rating = rating;
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

    public int getLessonNumber() {
        int count = 0;
        for(Item item : this.items) {
            if(item.getChildrens().size() == 0){
                count ++;
            }
        }
        return count;
    }
}
