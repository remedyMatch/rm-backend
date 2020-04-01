package io.remedymatch.geodaten.geocoding.domain;

import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Response;
import lombok.Getter;
import lombok.NonNull;
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
