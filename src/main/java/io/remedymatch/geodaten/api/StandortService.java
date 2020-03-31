package io.remedymatch.geodaten.api;

import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
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
