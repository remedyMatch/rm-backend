package io.remedymatch.geodaten.geocoding;

import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;

import java.util.List;

public interface Geocoder {

    List<Point> findePointsByAdresse(Adresse adresse);

    List<Point> findePointsByAdressString(String adressString);

    Adresse findeAdresseByPoint(Point point);

    List<Adresse> findeAdressVorschlaegeByAdressString(String adressString);
}
