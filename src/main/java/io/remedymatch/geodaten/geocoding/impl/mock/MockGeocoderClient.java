package io.remedymatch.geodaten.geocoding.impl.mock;

import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Profile("!prod & !geo")
@RequiredArgsConstructor
public class MockGeocoderClient implements Geocoder {

    private final List<Point> pointsByAdressString = Arrays.asList(
            mockPointWith(52.516275, 13.377704),
            mockPointWith(48.137021, 11.575459),
            mockPointWith(47.421066, 10.985365)
    );
    private final List<Point> pointsByAdresse = pointsByAdressString;
    private final String adresseByPoint = "Hüttenhospital, Am Marksbach 28, 44269 Dortmund, Deutschland";

    private Point mockPointWith(double lat, double lon) {
        return new Point(lat, lat);
    }

    @Override
    public List<Point> findePointsByAdressString(@NonNull String adressString) {
        return pointsByAdressString;
    }

    @Override
    public List<Point> findePointsByAdresse(@NonNull Adresse adresse) {
        return pointsByAdressString;
    }

    @Override
    public String findeAdresseByPoint(@NonNull Point point) {
        return adresseByPoint;
    }

    @Override
    public List<String> findeAdressVorschlaegeByAdressString(@NonNull String adressString) {
        return new ArrayList<>();
    }

    @Override
    public double kilometerBerechnen(BigDecimal lat1, BigDecimal lon1, BigDecimal lat2, BigDecimal lon2) {
        return 0;
    }

    @Override
    public double kilometerBerechnen(double lat1, double lon1, double lat2, double lon2) {
        return 0;
    }

    @Override
    public double kilometerBerechnen(InstitutionStandort von, InstitutionStandort nach) {
        return 0;
    }

    @Override
    public double kilometerBerechnen(MatchStandort von, MatchStandort nach) {
        return 0;
    }
}
