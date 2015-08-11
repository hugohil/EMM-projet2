package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Clement on 10/08/15.
 */
public class AppData {

    @JsonProperty("_id")
    private String mongoID;

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMongoID() {
        return mongoID;
    }

    public void setMongoID(String mongoID) {
        this.mongoID = mongoID;
    }
}
