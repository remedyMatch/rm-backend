package io.remedymatch.standort.api;

import io.remedymatch.standort.geocoding.domain.Adresse;
import io.remedymatch.standort.geocoding.Geocoder;
import io.remedymatch.standort.geocoding.domain.Point;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class StandortService {

    private final Geocoder geocoder;

    public List<Point> findePointsByAdressString(String adressString) {
        return geocoder.findePointsByAdressString(adressString);
    }

    public List<Point> findePointsByAdresse(Adresse adresse) {
        return geocoder.findePointsByAdresse(adresse);
    }

    public Adresse findeAdresseByPoint(Point point) {
        return geocoder.findeAdresseByPoint(point);
    }

    public List<Adresse> findeAdressVorschlaegeByAdressString(String adressString) {
        return geocoder.findeAdressVorschlaegeByAdressString(adressString);
    }
}
