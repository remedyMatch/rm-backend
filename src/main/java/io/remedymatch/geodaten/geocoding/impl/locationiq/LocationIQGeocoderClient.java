package io.remedymatch.geodaten.geocoding.impl.locationiq;

import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Query;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Response;
import io.remedymatch.properties.RmBackendProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor
public class LocationIQGeocoderClient implements Geocoder {

    private final RestTemplate restTemplate = new RestTemplate();
    private final RmBackendProperties properties;

    @Override
    public List<Point> findePointsByAdressString(@NonNull String adressString) {
        final Query query = new Query(adressString);
        return queryGeocodeService(query);
    }

    @Override
    public List<Point> findePointsByAdresse(@NonNull Adresse adresse) {
        final Query query = new Query(adresse);
        return queryGeocodeService(query);
    }


    @Override
    public Adresse findeAdresseByPoint(@NonNull Point point) {
        throw new NotImplementedException("TBD");
    }

    @Override
    public List<Adresse> findeAdressVorschlaegeByAdressString(@NonNull String adressString) {
        throw new NotImplementedException("TBD");
    }

    private List<Point> queryGeocodeService(Query query) throws RuntimeException {
        final Map<String, String> queryParams = buildQueryParamsFromQuery(query);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getGeocoderServiceBaseUrl() +
                "/search.php");
        queryParams.forEach(builder::queryParam);
        final URI url = builder.build().encode().toUri();
        final ResponseEntity<Response[]> responseEntity = restTemplate.getForEntity(url, Response[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException(responseEntity.getStatusCode().name());
        }
        final Response[] gefundeneKoordinaten = responseEntity.getBody();
        if (gefundeneKoordinaten == null || gefundeneKoordinaten.length <= 0) {
            return List.of();
        }
        return Arrays.stream(responseEntity.getBody())
                .map(response -> new Point(Double.parseDouble(response.getLat()), Double.parseDouble(response.getLon())))
                .collect(Collectors.toList());
    }

    private Map<String, String> buildQueryParamsFromQuery(@NonNull Query query) {
        final Map<String, String> queryParams = new HashMap<>();
        queryParams.put("key", properties.getGeocoderServiceApiKey());
        queryParams.put("format", "json");
        if (isNotEmpty(query.getQ())) {
            queryParams.put("q", query.getQ());
            return queryParams;
        } else {
            if (isNotEmpty(query.getStreet())) {
                queryParams.put("street", query.getStreet());
            }
            if (isNotEmpty(query.getCounty())) {
                queryParams.put("county", query.getCounty());
            }
            if (isNotEmpty(query.getState())) {
                queryParams.put("state", query.getState());
            }
            if (isNotEmpty(query.getCity())) {
                queryParams.put("city", query.getCity());
            }
            if (isNotEmpty(query.getPostalcode())) {
                queryParams.put("postalcode", query.getPostalcode());
            }
            queryParams.put("country", query.getCountry());
        }
        if (!(queryParams.containsKey("street")) &&
                !(queryParams.containsKey("city")) &&
                !(queryParams.containsKey("county")) &&
                !(queryParams.containsKey("state")) &&
                !(queryParams.containsKey("postalcode"))) {
            throw new RuntimeException("Bei strukturierter Suche muss mindestens eine weitere Eigenschaft (neben dem " +
                    "Land) gesetzt sein.");
        }
        return queryParams;
    }
}
