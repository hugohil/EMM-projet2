package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Clement on 10/08/15.
 */
public class AppData {

    @JsonProperty("_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Long.parseLong(id);
    }
}
