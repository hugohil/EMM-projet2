package com.example.clement.emm_project2.model;

import android.media.Image;

/**
 * Created by Clement on 10/08/15.
 */
public class TeaserVideo extends AppData {

    private String videoUrl;

    private Image videoPoster;

    private String title;

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public Image getVideoPoster() {
        return videoPoster;
    }

    public void setVideoPoster(Image videoPoster) {
        this.videoPoster = videoPoster;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
