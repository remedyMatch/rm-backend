package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import java.util.List;

// DO NOT DELETE / DO NOT REPLACE WITH LOMBOK @Builder
public final class ResponseBuilder {
    private float place_id;
    private String licence;
    private String osm_type;
    private String osm_id;
    private List<String> boundingbox;
    private String lat;
    private String lon;
    private String display_name;
    private String clazz;
    private String type;
    private String importance;

    private ResponseBuilder() {
    }

    public static ResponseBuilder getInstance() {
        return new ResponseBuilder();
    }

    public ResponseBuilder place_id(float place_id) {
        this.place_id = place_id;
        return this;
    }

    public ResponseBuilder licence(String licence) {
        this.licence = licence;
        return this;
    }

    public ResponseBuilder osm_type(String osm_type) {
        this.osm_type = osm_type;
        return this;
    }

    public ResponseBuilder osm_id(String osm_id) {
        this.osm_id = osm_id;
        return this;
    }

    public ResponseBuilder boundingbox(List<String> boundingbox) {
        this.boundingbox = boundingbox;
        return this;
    }

    public ResponseBuilder lat(String lat) {
        this.lat = lat;
        return this;
    }

    public ResponseBuilder lon(String lon) {
        this.lon = lon;
        return this;
    }

    public ResponseBuilder display_name(String display_name) {
        this.display_name = display_name;
        return this;
    }

    public ResponseBuilder clazz(String clazz) {
        this.clazz = clazz;
        return this;
    }

    public ResponseBuilder type(String type) {
        this.type = type;
        return this;
    }

    public ResponseBuilder importance(String importance) {
        this.importance = importance;
        return this;
    }

    public Response build() {
        Response response = new Response();
        response.setPlace_id(place_id);
        response.setLicence(licence);
        response.setOsm_type(osm_type);
        response.setOsm_id(osm_id);
        response.setBoundingbox(boundingbox);
        response.setLat(lat);
        response.setLon(lon);
        response.setDisplay_name(display_name);
        response.setClazz(clazz);
        response.setType(type);
        response.setImportance(importance);
        return response;
    }
}
