package io.remedymatch.geodaten.geocoding.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Adresse {
    private String strasse;
    private String stadt;
    private String bezirk;
    private String bundesland;
    private String land;
    private String plz;

    @Override
    public String toString() {
        return Stream.of(strasse, plz, stadt, bezirk, bundesland, land)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }

}
