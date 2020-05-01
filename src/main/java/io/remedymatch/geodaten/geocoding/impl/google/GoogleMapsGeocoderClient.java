package io.remedymatch.geodaten.geocoding.impl.google;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.*;
import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.properties.GeodatenProperties;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.CollectionUtils.isEmpty;

@Profile("prod | (geo & !geo-liq)")
@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleMapsGeocoderClient implements Geocoder {

    private final GeodatenProperties properties;

    private GeoApiContext geoApiContext;

    @Override
    public List<Point> findePointsByAdressString(@NonNull String adressString) {
        final List<GeocodingResult> responses = geocode(adressString);
        if (isEmpty(responses)) {
            return List.of();
        }
        return responses.stream()
                .map(response -> new Point(response.geometry.location.lat, response.geometry.location.lng))
                .collect(Collectors.toList());
    }

    @Override
    public List<Point> findePointsByAdresse(@NonNull Adresse adresse) {
        return findePointsByAdressString(adresse.toString());
    }


    @Override
    public String findeAdresseByPoint(@NonNull Point point) {
        final List<GeocodingResult> responses = reverseGeocode(point);
        if (isEmpty(responses)) {
            return "";
        }
        return responses.stream()
                .map(geocodingResult -> geocodingResult.formattedAddress)
                .collect(Collectors.toList())
                .iterator()
                .next();
    }

    @Override
    public List<String> findeAdressVorschlaegeByAdressString(@NonNull String adressString) {
        final List<GeocodingResult> responses = geocode(adressString);
        if (isEmpty(responses)) {
            return List.of();
        }
        return responses.stream()
                .map(geocodingResult -> geocodingResult.formattedAddress)
                .collect(Collectors.toList());
    }

    private List<GeocodingResult> geocode(@NonNull String adressString) {
        final GoogleMapsQueryParameter parameter = GoogleMapsQueryParameter.builder().query(adressString).build();
        return queryGeocodeService(parameter, GeocodingOperation.GEOCODE);
    }

    private List<GeocodingResult> reverseGeocode(@NonNull Point point) {
        final LatLng latLng = new LatLng(point.getLatitude(), point.getLongitude());
        final GoogleMapsQueryParameter parameter = GoogleMapsQueryParameter.builder().latLng(latLng).build();
        return queryGeocodeService(parameter, GeocodingOperation.REVERSE_GEOCODE);
    }

    private List<GeocodingResult> queryGeocodeService(@NonNull GoogleMapsQueryParameter queryParameter,
                                                      @NonNull GoogleMapsGeocoderClient.GeocodingOperation operation) {
        initApiContext();

        if (operation == GeocodingOperation.GEOCODE) {
            if (StringUtils.isEmpty(queryParameter.query)) {
                log.warn("Didn't execute geocoding for empty query string.");
                return List.of();
            }
        } else {
            if (queryParameter.latLng == null) {
                log.warn("Didn't execute reverse geocoding for null coordinates.");
                return List.of();
            }
        }

        try {
            GeocodingResult[] results = null;
            if (operation == GeocodingOperation.GEOCODE) {
                results = GeocodingApi.geocode(geoApiContext, queryParameter.query).await();
            } else if (operation == GeocodingOperation.REVERSE_GEOCODE) {
                results = GeocodingApi.reverseGeocode(geoApiContext, queryParameter.latLng).await();
            }
            if (results == null || (results.length == 0)) {
                log.error("Received an empty or null response from Google Maps API.");
                return List.of();
            }
            return Arrays.stream(results).collect(Collectors.toList());
        } catch (IllegalStateException | InterruptedException | ApiException | IOException e) {
            log.error("Error when trying to {} with Google Maps API: ", operation.name(), e);
        }
        return List.of();
    }

    private List<DirectionsRoute> queryDirectionsService(@NonNull LatLng origin, @NonNull LatLng destination) {
        initApiContext();

        try {
            final DirectionsApiRequest directionsApiRequest = DirectionsApi.newRequest(geoApiContext);
            final DirectionsResult result = directionsApiRequest.mode(TravelMode.DRIVING)
                    .origin(origin)
                    .destination(destination).await();

            if (result == null || (result.routes.length == 0)) {
                log.error("Received empty or null response from Google Maps Directions API.");
                return List.of();
            }
            return Arrays.asList(result.routes);
        } catch (InterruptedException | ApiException | IOException e) {
            log.error("Error when trying to request route distance with Google Maps Directions API: ", e);
        }
        return List.of();
    }

    private void initApiContext() {
        if (geoApiContext == null) {
            geoApiContext = new GeoApiContext.Builder()
                    .apiKey(properties.getGeocoderServiceApiKey())
                    .build();
        }
    }

    @Override
    public double kilometerBerechnen(@NonNull BigDecimal lat1, @NonNull BigDecimal lon1,
                                     @NonNull BigDecimal lat2, @NonNull BigDecimal lon2) {
        return kilometerBerechnen(lat1.doubleValue(), lon1.doubleValue(), lat2.doubleValue(), lon2.doubleValue());
    }

    @Override
    public double kilometerBerechnen(double lat1,double lon1, double lat2, double lon2) {

        final LatLng origin = new LatLng(lat1, lon1);
        final LatLng destination = new LatLng(lat2, lon2);
        final List<DirectionsRoute> routes = queryDirectionsService(origin, destination);
        if (isEmpty(routes) || (routes.get(0).legs.length == 0)) {
            return 0.0d;
        }
        return routes.iterator().next().legs[0].distance.inMeters / 1000.0d;

    }

    @Override
    public double kilometerBerechnen(@NonNull InstitutionStandort von, @NonNull InstitutionStandort nach) {
        return kilometerBerechnen(von.getLatitude().doubleValue(), von.getLongitude().doubleValue(),
                nach.getLatitude().doubleValue(), nach.getLongitude().doubleValue());
    }

    @Override
    public double kilometerBerechnen(@NonNull MatchStandort von, @NonNull MatchStandort nach) {
        return kilometerBerechnen(von.getLatitude(), von.getLongitude(), nach.getLatitude(), nach.getLongitude());
    }

    @Builder
    private static class GoogleMapsQueryParameter {
        private final LatLng latLng;
        private final String query;
    }

    private enum GeocodingOperation {
        GEOCODE,
        REVERSE_GEOCODE
    }
}


