package io.remedymatch.standort.geocoding.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Adresse {
    private String strasse;
    private String stadt;
    private String bezirk;
    private String bundesland;
    private String land;
    private String plz;
}
