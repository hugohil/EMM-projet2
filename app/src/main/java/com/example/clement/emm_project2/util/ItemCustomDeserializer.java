package com.example.clement.emm_project2.util;

import android.util.Log;

import com.example.clement.emm_project2.model.Author;
import com.example.clement.emm_project2.model.Formation;
import com.example.clement.emm_project2.model.Item;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 05/09/15.
 */
public class ItemCustomDeserializer extends JsonDeserializer<Item> {
    @Override
    public Item deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String title = node.get("title").asText();
        String type = node.get("type").asText();
        int nid = node.get("nid").asInt();
        int nbCredits = node.get("nb_credits").asInt();
        String fieldPoster = "";
        if(node.get("field_poster") != null) {
            fieldPoster = node.get("field_poster").asText();
        }
        String mongoId = node.get("_id").asText();
        boolean free = node.get("free").asBoolean();
        boolean active = node.get("active").asBoolean();
        List<Map<String, Object>> fieldFiles;
        List<Map<String, Object>> fieldVignette;
        List<Map<String, Object>> fieldVideo;

        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<Map<String, Object>>> stringObjectMap = new TypeReference<List<Map<String, Object>>>(){};
        TypeReference<List<String>> stringList = new TypeReference<List<String>>(){};
        List<String> childrens = mapper.readValue(node.get("children").traverse(), stringList);
        fieldFiles = mapper.readValue(node.get("field_files").traverse(), stringObjectMap);
        fieldVignette = mapper.readValue(node.get("field_vignette").traverse(), stringObjectMap);
        fieldVideo = mapper.readValue(node.get("field_video").traverse(), stringObjectMap);


        Item item = new Item();
        item.setType(type);
        item.setTitle(title);
        item.setNid(nid);
        item.setNbCredits(nbCredits);
        item.setFree(free);
        item.setMongoId(mongoId);
        item.setActive(active);
        item.setChildrens(childrens);
        item.setFieldFiles(fieldFiles);
        item.setFieldPoster(fieldPoster);
        item.setFieldVideo(fieldVideo);
        item.setFieldVignette(fieldVignette);

        return item;
    }
}
