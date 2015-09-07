package com.example.clement.emm_project2.model;

import android.media.Image;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Clement on 10/08/15.
 */
public class Author extends AppData implements Serializable {

    @JsonProperty("fullname")
    private String fullName;

    @JsonProperty("picture_large")
    private String pictureLarge;

    @JsonProperty("picture_small")
    private String pictureSmall;

    private String link;

    @JsonProperty("picture")
    private String picture;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullname) {
        this.fullName = fullname;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPictureLarge() {
        return pictureLarge;
    }

    public void setPictureLarge(String pictureLarge) {
        this.pictureLarge = pictureLarge;
    }

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String toString() {
        return "Author = [fullname: "+ this.fullName + ", link: "+ this.link + "]";
    }

}
