package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimResultado {

    @JsonProperty("display_name")
    private String displayName;

    private String lat;
    private String lon;
    private String type;

    @JsonProperty("class")
    private String categoria;

    public NominatimResultado() {
    }

    public NominatimResultado(String displayName, String lat, String lon, String type, String categoria) {
        this.displayName = displayName;
        this.lat = lat;
        this.lon = lon;
        this.type = type;
        this.categoria = categoria;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

