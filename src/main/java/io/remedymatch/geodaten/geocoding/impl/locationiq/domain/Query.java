package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import io.remedymatch.geodaten.geocoding.domain.Adresse;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

@Getter
@Setter
public class Query {

    private static final String DEUTSCHLAND = "Deutschland";

    /**
     * Free-form query string to search for. Commas are optional, but improves performance by reducing the complexity
     * of the search. Do not combine with structured/postalcode parameters.
     */
    private String q;

    private String street;
    private String city;
    private String county;
    private String state;
    @NonNull
    private String country;
    private String postalcode;

    public Query(@NonNull String adressString) {
        q = adressString;
    }

    public Query(@NonNull final Adresse adresse) {
        if (StringUtils.isNotEmpty(adresse.getStrasse())) {
            street = adresse.getStrasse();
        }
        if (StringUtils.isNotEmpty(adresse.getBezirk())) {
            county = adresse.getBezirk();
        }
        if (StringUtils.isNotEmpty(adresse.getBundesland())) {
            state = adresse.getBundesland();
        }
        if (StringUtils.isNotEmpty(adresse.getStadt())) {
            city = adresse.getStadt();
        }
        if (StringUtils.isNotEmpty(adresse.getPlz())) {
            postalcode = adresse.getPlz();
        }
        country = Optional.ofNullable(adresse.getLand()).orElse(DEUTSCHLAND);
    }
}
