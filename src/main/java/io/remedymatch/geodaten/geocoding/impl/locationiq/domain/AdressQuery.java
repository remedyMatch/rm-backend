package io.remedymatch.geodaten.geocoding.impl.locationiq.domain;

import io.remedymatch.geodaten.geocoding.domain.Adresse;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@Setter
public class AdressQuery implements Query {

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

    public AdressQuery(@NonNull String adressString) {
        q = adressString;
    }

    public AdressQuery(@NonNull final Adresse adresse) {
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

    @Override
    public Map<String, String> asMap() {
        final Map<String, String> map = new HashMap<>();
        map.put("format", "json");
        
        if (isNotEmpty(q)) {
            map.put("q", q);
            return map;
        } else {
            if (isNotEmpty(street)) {
                map.put("street", street);
            }
            if (isNotEmpty(county)) {
                map.put("county", county);
            }
            if (isNotEmpty(state)) {
                map.put("state", state);
            }
            if (isNotEmpty(city)) {
                map.put("city", city);
            }
            if (isNotEmpty(postalcode)) {
                map.put("postalcode", postalcode);
            }
            map.put("country", country);
        }
        if (!(map.containsKey("street")) &&
                !(map.containsKey("city")) &&
                !(map.containsKey("county")) &&
                !(map.containsKey("state")) &&
                !(map.containsKey("postalcode"))) {
            throw new RuntimeException("Bei strukturierter Suche muss mindestens eine weitere Eigenschaft (neben dem " +
                    "Land) gesetzt sein.");
        }
        return map;
    }

    @Override
    public String url() {
        return "search.php";
    }
}
