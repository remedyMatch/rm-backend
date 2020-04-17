package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

import java.util.List;

@JsonDeserialize(builder = Response.ResponseBuilder.class)
@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {

    private float place_id;
    private String licence;
    private String osm_type;
    private String osm_id;
    private List<String> boundingbox;
    private String lat;
    private String lon;
    private String display_name;
    @JsonProperty("class")
    private String clazz;
    private String type;
    private String importance;

    @JsonPOJOBuilder(withPrefix = "")
    public static class ResponseBuilder {
    }
}
