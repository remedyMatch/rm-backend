package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import io.remedymatch.geodaten.geocoding.domain.Point;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class KoordinatenQuery implements Query {

    private String lat;
    private String lon;
    private String format = "json";

    public KoordinatenQuery(@NonNull final Point point) {
        lat = String.valueOf(point.getLatitude());
        lon = String.valueOf(point.getLongitude());
    }

    @Override
    public Map<String, String> asMap() {
        final Map<String, String> map = new HashMap<>();

        map.put("format", "json");
        map.put("lat", lat);
        map.put("lon", lon);
        return map;
    }

    @Override
    public String url() {
       return "reverse.php";
    }
}
