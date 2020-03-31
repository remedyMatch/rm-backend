package io.remedymatch.standort.geocoding;

import io.remedymatch.standort.geocoding.domain.Adresse;
import io.remedymatch.standort.geocoding.domain.Point;

import java.util.List;

public interface Geocoder {

    List<Point> findePointsByAdresse(Adresse adresse);
    List<Point> findePointsByAdressString(String adressString);
    Adresse findeAdresseByPoint(Point point);
    List<Adresse> findeAdressVorschlaegeByAdressString(String adressString);
}
