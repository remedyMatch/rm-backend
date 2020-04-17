package io.remedymatch.geodaten.geocoding.impl.locationiq;

import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.AdressQuery;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.KoordinatenQuery;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Response;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class MockGeocoderClient implements Geocoder {

    private final List<Response> pointsByAdressStringResponses = Arrays.asList(
            mockPointResponseWith("52.516275","13.377704"),
            mockPointResponseWith("48.137021","11.575459"),
            mockPointResponseWith("47.421066","10.985365")
    );
    private final List<Response> pointsByAdresseResponses = pointsByAdressStringResponses;
    private final List<Response> adresseByPointResponses = Collections.singletonList(
            mockAdresseResponseWith("Hüttenhospital, Am Marksbach 28, 44269 Dortmund, Deutschland")
    );

    private Response mockPointResponseWith(@NonNull String lat, @NonNull String lon) {
        return Response.builder()
                .lat(lat)
                .lon(lon)
                .build();
    }

    private Response mockAdresseResponseWith(@NonNull String displayName) {
        return Response.builder()
                .display_name(displayName)
                .build();
    }

    @Override
    public List<Point> findePointsByAdressString(@NonNull String adressString) {
        final AdressQuery adressQuery = new AdressQuery(adressString);
        final List<Response> responses = pointsByAdressStringResponses;
        if (isEmpty(responses)) {
            return List.of();
        }
        return responses.stream()
                .map(response -> new Point(Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon())))
                .collect(Collectors.toList());
    }

    @Override
    public List<Point> findePointsByAdresse(@NonNull Adresse adresse) {
        final AdressQuery adressQuery = new AdressQuery(adresse);
        final List<Response> responses = pointsByAdresseResponses;
        if (isEmpty(responses)) {
            return List.of();
        }
        return responses.stream()
                .map(response -> new Point(Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon())))
                .collect(Collectors.toList());
    }

    @Override
    public String findeAdresseByPoint(@NonNull Point point) {
        final KoordinatenQuery koordinatenQuery = new KoordinatenQuery(point);
        final List<Response> responses = adresseByPointResponses;
        if (isEmpty(responses)) {
            return "";
        }
        return responses.stream()
                .map(Response::getDisplay_name)
                .collect(Collectors.toList())
                .iterator()
                .next();
    }

    @Override
    public List<String> findeAdressVorschlaegeByAdressString(@NonNull String adressString) {
        return new ArrayList<>();
    }
}
