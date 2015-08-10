package com.example.clement.emm_project2.model;

import android.media.Image;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class Formation {
    /*
     * Represents a formation item as described in the api doc
     */

    private String title;

    private String subtitle;

    /*
     * Specific rss feed URL
     */
    private String guid;

    private String description;

    private Date pubDate;

    private String link;

    /*
     * ean code of the formation (nmuber under barcode)
     */
    private String ean;

    /*
     * Total duration in seconds
     */
    private float duration;

    private Category category;

    private SubCategory subCategory;

    private Image image;

    /*
     * Link to QCM page
     */
    private String qcm;

    /*
     * Images of the formation
     * First index: Type (thumbs/Landscapes)
     * Second index: Size (small, medium, big)
     */
    private Map<String, Map<String, Image>> images;

    /*
     * Formation teasing video
     */
    private TeaserVideo teaser;

    private float price;

    /*
     * Can be a list of String, let's see the data...
     */
    private String prerequisites;

    /*
     * Idem
     */
    private String objectives;

    private List<Author> authors;

    /*
     * Ratings of the formation (indexes : average [ average rating ] / Count [ ratings nb ])
     */
    private Map<String, Float> rating;

    /*
     * Total number of videos in the formation
     */
    private int videoCount;

    /*
     * Last formation update date
     */
    private Date updatedAt;

    private boolean active;

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
