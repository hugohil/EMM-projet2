package com.example.clement.emm_project2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Clement on 10/08/15.
 */
public class AppData implements Serializable{

    @JsonProperty("_id")
    private String mongoID;

    @JsonIgnore
    private int id;

    @JsonIgnore
    public int getId() {
        return id;
    }

    @JsonIgnore
    public void setId(int id) {
        this.id = id;
    }

    public String getMongoID() {
        return mongoID;
    }

    public void setMongoID(String mongoID) {
        this.mongoID = mongoID;
    }
}
