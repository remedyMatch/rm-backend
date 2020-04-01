package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import java.util.Map;

public interface Query {
    Map<String, String> asMap();
    String url();
}
