package io.remedymatch.standort.geocoding.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Point {

    // Usage by Jackson
    public Point() {
    }

    private double latitude;
    private double longitude;
}
