package io.remedymatch.geodaten.geocoding;

import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;

import java.math.BigDecimal;
import java.util.List;

public interface Geocoder {

    List<Point> findePointsByAdresse(Adresse adresse);

    List<Point> findePointsByAdressString(String adressString);

    String findeAdresseByPoint(Point point);

    List<String> findeAdressVorschlaegeByAdressString(String adressString);

    double kilometerBerechnen(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2);

    double kilometerBerechnen(double lat1, double lon1, double lat2, double lon2);

    double kilometerBerechnen(InstitutionStandort von, InstitutionStandort nach);

    double kilometerBerechnen(MatchStandort von, MatchStandort nach);
}
