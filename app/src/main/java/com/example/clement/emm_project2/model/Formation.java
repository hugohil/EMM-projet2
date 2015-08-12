package com.example.clement.emm_project2.model;

import android.media.Image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
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

    /*
     * Specific rss feed URL
     */
    @JsonProperty("guid")
    private String guid;

    @JsonProperty("description")
    private String description;

    @JsonProperty("pubdate")
    private Date pubDate;

    @JsonProperty("link")
    private String link;

    /*
     * ean code of the formation (nmuber under barcode)
     */
    @JsonProperty("ean")
    private String ean;

    /*
     * Total duration in seconds
     */
    @JsonProperty("duration")
    private float duration;

    @JsonProperty("category")
    private Category category;

    @JsonProperty("subcategory")
    private SubCategory subCategory;

    @JsonProperty("image")
    private Image image;

    /*
     * Link to QCM page
     */
    @JsonProperty("qcm")
    private String qcm;

    /*
     * Images of the formation
     * First index: Type (thumbs/Landscapes)
     * Second index: Size (small, medium, big)
     */
    @JsonProperty("images")
    private Map<String, Map<String, Image>> images;

    /*
     * Formation teasing video
     */
    @JsonProperty("teaser")
    private TeaserVideo teaser;

    @JsonProperty("price")
    private float price;

    /*
     * Can be a list of String, let's see the data...
     */
    @JsonProperty("prerequisites")
    private String prerequisites;

    /*
     * Idem
     */
    @JsonProperty("objectives")
    private String objectives;

    @JsonProperty("authors")
    private List<Author> authors;

    /*
     * Ratings of the formation (indexes : average [ average rating ] / Count [ ratings nb ])
     */
    @JsonProperty("rating")
    private Map<String, Float> rating;

    /*
     * Total number of videos in the formation
     */
    @JsonProperty("videocount")
    private int videoCount;

    /*
     * Last formation update date
     */
    @JsonProperty("updatedAt")
    private Date updatedAt;

    @JsonProperty("updatedAt")
    private boolean active;

    @JsonProperty("items")
    private List<Item> items;


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

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getEan() {
        return ean;
    }

    public void setEan(String ean) {
        this.ean = ean;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getQcm() {
        return qcm;
    }

    public void setQcm(String qcm) {
        this.qcm = qcm;
    }

    public Map<String, Map<String, Image>> getImages() {
        return images;
    }

    public void setImages(Map<String, Map<String, Image>> images) {
        this.images = images;
    }

    public TeaserVideo getTeaser() {
        return teaser;
    }

    public void setTeaser(TeaserVideo teaser) {
        this.teaser = teaser;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(String prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public Map<String, Float> getRating() {
        return rating;
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

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
