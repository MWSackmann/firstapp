package com.example.model;

import com.example.util.JsonHandling;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractModel {
    protected Map<String, Object> jsonMap;

    protected AbstractModel() {
        jsonMap = new LinkedHashMap<>();
    }

    protected AbstractModel(Map<String, Object> deserializedJsonRepresentation) {
        this.jsonMap = deserializedJsonRepresentation;
    }

    @JsonIgnore
    protected void setProperty(String name, Object value) {
        this.jsonMap.put(name, value);
    }

    @JsonAnySetter
    public void addProperty(String name, Object value) {
        this.jsonMap.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> getProperties() {
        return jsonMap;
    }

    @JsonIgnore
    protected Boolean getPropertyAsBoolean(String name) {
        return jsonMap.get(name) == null ? null : (Boolean) jsonMap.get(name);
    }

    @JsonIgnore
    protected String getPropertyAsString(String path) {
        return (String) JsonHandling.getFromNestedMaps(jsonMap, path);
    }

    @JsonIgnore
    public boolean isEmpty() {
        return jsonMap == null || jsonMap.isEmpty();
    }
}
