package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Clement on 10/08/15.
 */
public class AppData {

    @JsonProperty("_id")
    private String mongoID;

    @JsonIgnore
    private Long id;

    public Long getId() {
        return id;
    }

    @JsonIgnore
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
