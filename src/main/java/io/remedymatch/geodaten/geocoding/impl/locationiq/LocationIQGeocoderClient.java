package io.remedymatch.geodaten.geocoding.impl.locationiq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.AdressQuery;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.KoordinatenQuery;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Query;
import io.remedymatch.geodaten.geocoding.impl.locationiq.domain.Response;
import io.remedymatch.properties.GeodatenProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY;
import static org.springframework.util.CollectionUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class LocationIQGeocoderClient implements Geocoder {

    private final RestTemplate restTemplate;
    private final GeodatenProperties properties;

    @Override
    public List<Point> findePointsByAdressString(@NonNull String adressString) {
        final AdressQuery adressQuery = new AdressQuery(adressString);
        final List<Response> responses = queryGeocodeService(adressQuery);
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
        final List<Response> responses = queryGeocodeService(adressQuery);
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
        final List<Response> responses = queryGeocodeService(koordinatenQuery);
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
        final AdressQuery adressQuery = new AdressQuery(adressString);
        final List<Response> responses = queryGeocodeService(adressQuery);
        if (isEmpty(responses)) {
            return List.of();
        }
        return responses.stream()
                .map(Response::getDisplay_name)
                .collect(Collectors.toList());
    }

    private List<Response> queryGeocodeService(@NonNull Query query) throws RuntimeException {
        final Map<String, String> queryParams = query.asMap();
        putApiKeyInMap(queryParams);
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(properties.getGeocoderServiceBaseUrl() +
                query.url());
        queryParams.forEach(builder::queryParam);
        final URI url = builder.build().encode().toUri();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        if (isEmpty(messageConverters) || !(messageConverters.get(0) instanceof MappingJackson2HttpMessageConverter)) {
            final HttpMessageConverter<?> messageConverter = createMessageConverter();
            restTemplate.getMessageConverters().add(0, messageConverter);
        }
        final ResponseEntity<Response[]> responseEntity = restTemplate.getForEntity(url, Response[].class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            // make the application work even if geocoder is not configured properly
            return List.of();
            //throw new RuntimeException(responseEntity.getStatusCode().name());
        }
        final Response[] gefundeneResponses = responseEntity.getBody();
        if (gefundeneResponses == null || gefundeneResponses.length <= 0) {
            return List.of();
        }
        return Arrays.stream(responseEntity.getBody()).collect(Collectors.toList());
    }

    private HttpMessageConverter<?> createMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(ACCEPT_SINGLE_VALUE_AS_ARRAY);
        converter.setObjectMapper(objectMapper);
        return converter;
    }

    private void putApiKeyInMap(Map<String, String> map) {
        map.put("key", properties.getGeocoderServiceApiKey());
    }
}
