package com.example.clement.emm_project2.util;

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
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Clement on 05/09/15.
 */
public class FormationCustomDeserializer extends JsonDeserializer<Formation> {
    @Override
    public Formation deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String title = node.get("title").asText();
        String subtitle = node.get("subtitle").asText();
        String productUrl = node.get("product_url").asText();
        String ean13 = node.get("ean13").asText();
        String type = node.get("type").asText();
        Float price = node.get("price").floatValue();
        String description = node.get("description").asText();
        int duration = node.get("duration").asInt();
        String objectives = "Pas d'objectifs";
        if(node.get("objectives") != null) {
            objectives =  node.get("objectives").asText();

        }
        String prerequisites = "Prérequis";
        if(node.get("prerequisites") != null) {
            prerequisites =  node.get("prerequisites").asText();
        }
        boolean canDownload = node.get("can_download").asBoolean();
        String qcm = node.get("qcm").asText();
        String teaserText = node.get("teaser_text").asText();
        String category = node.get("category").asText();
        String subCategory = node.get("subcategory").asText();
        String teaser = node.get("teaser").asText();
        String urlPath = node.get("url_path").asText();
        String publishedDate = node.get("publishedDate").asText();
        String poster = node.get("poster").asText();
        //            int __v;


        ObjectMapper mapper = new ObjectMapper();
        TypeReference<Map<String,Map<String, String>>> typeRefImages = new TypeReference<Map<String,Map<String, String>>>(){};
        Map<String, Map<String, String>> images = mapper.readValue(node.get("images").traverse(), typeRefImages);

        TypeReference<List<Author>> typeRefAuthors = new TypeReference<List<Author>>(){};
        List<Author> authors = mapper.readValue(node.get("authors").traverse(), typeRefAuthors);

        TypeReference<List<Item>> typeRefItems = new TypeReference<List<Item>>(){};
        List<Item> items = mapper.convertValue(node.get("items"), typeRefItems);

        TypeReference<Map<String, Float>> typeRerating = new TypeReference<Map<String, Float>>(){};
        Map<String, Float> rating = mapper.readValue(node.get("rating").traverse(), typeRerating);

        TypeReference<List<String>> typechildren = new TypeReference<List<String>>(){};
        List<String> children = mapper.readValue(node.get("children").traverse(), typechildren);

        boolean free = node.get("free").asBoolean();
        String updatedAt = node.get("updatedAt").asText();

        boolean active = node.get("active").asBoolean();
        int videoCount = node.get("video_count").asInt();

        Formation formation = new Formation();
        formation.setActive(active);
        formation.setTitle(title);
        formation.setSubtitle(subtitle);
        formation.setProductUrl(productUrl);
        formation.setEan(ean13);
        formation.setType(type);
        formation.setPrice(price);
        formation.setDescription(description);
        formation.setDuration(duration);
        formation.setCanDownload(canDownload);
        formation.setQcm(qcm);
        formation.setTeaserText(teaserText);
        formation.setCatId(category);
        formation.setSubCatId(subCategory);
        formation.setTeaser(teaser);
        formation.setUrlPath(urlPath);
        formation.setPublishedDate(publishedDate);
        formation.setPoster(poster);
        formation.setFree(free);
        formation.setUpdatedAt(updatedAt);
        formation.setVideoCount(videoCount);
        formation.setImages(images);
        formation.setObjectives(objectives);
        formation.setPrerequisites(prerequisites);
        formation.setAuthors(authors);
        formation.setItems(items);
        formation.setRating(rating);
        formation.setChildren(children);

        return formation;
    }
}
