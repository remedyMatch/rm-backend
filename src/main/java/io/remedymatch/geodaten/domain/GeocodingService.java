package io.remedymatch.geodaten.domain;

import io.remedymatch.geodaten.geocoding.Geocoder;
import io.remedymatch.geodaten.geocoding.domain.Adresse;
import io.remedymatch.geodaten.geocoding.domain.Point;
import io.remedymatch.institution.domain.model.InstitutionStandort;
import io.remedymatch.match.domain.MatchStandort;
import io.remedymatch.usercontext.UserContextService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Stellt Geocoding-Funktionalitaet bereit:
 * <ol>
 *     <li>Geocoding / ReverseGeocoding</li>
 *     <li>Distanzberechnung</li>
 *     <li>Adress-Vervollstaendigungs-Vorschlaege</li>
 * </ol>
 */
@AllArgsConstructor
@Service
public class GeocodingService {

    private final Geocoder geocoder;
    private final UserContextService userService;

    /**
     * Liefert Koordinaten zu einer bestimmten Adresse. In der Regel wird das erste gefundene Koordinaten-Paar
     * genommen.
     *
     * @param adressString Enthaelt die Adresse, fuer welche die Koordinaten ermittelt werden sollen
     * @return Koordinaten zu den anhand des adressStrings gefundenen Adressen.
     */
    public List<Point> findePointsByAdressString(String adressString) {
        return geocoder.findePointsByAdressString(adressString);
    }

    /**
     * Liefert Koordinaten zu einer bestimmten (strukturierten) Adresse. In der Regel wird das erste gefundene
     * Koordinaten-Paar genommen.
     *
     * @param adresse Enthaelt die Adresse, fuer welche die Koordinaten ermittelt werden sollen
     * @return Koordinaten zu den anhand der {@link Adresse} gefundenen Adressen.
     */
    public List<Point> findePointsByAdresse(@NonNull final Adresse adresse) {
        return geocoder.findePointsByAdresse(adresse);
    }

    /**
     * Liefert die Adresse (in Form eines Strings) anhand eines gegebenen Koordinaten-Paars.
     * @param point Koordinate, zu der die entsprechende Adresse gefunden werden soll.
     * @return Zur gegebenen Koordinate gehörende Adresse (als Adress-String).
     */
    public String findeAdresseByPoint(@NonNull final Point point) {
        return geocoder.findeAdresseByPoint(point);
    }

    /**
     * Liefert Adress-Vorschlaege anhand eines (unvollstaendigen) Adress-Strings. Kann fuer Auto-Vervollstaendigung
     * verwendet werden.
     * @param adressString unvollstaendiger Adress-String
     * @return Liste von vollstaendigen Adress-Strings, die basierend auf dem gegebenen adressString gefunden wurden.
     */
    public List<String> findeAdressVorschlaegeByAdressString(@NonNull final String adressString) {
        return geocoder.findeAdressVorschlaegeByAdressString(adressString);
    }

    /**
     * Berechnet die Distanz (in km) vom Hauptstandort des Benutzers zu dem gegebenen Ziel-Standort. Je nach verwendeter
     * Geocoder-Implementierung ist dies eine Strassendistanz (GoogleMaps) oder die statisch berechnete Luftlinie
     * (LocationIQ).
     * @param zielStandort Das Ziel, zu welchem die Distanz berechnet werden soll (vom Benutzer-Hauptstandort aus)
     * @return Distanz zwischen Benutzer-Hauptstandort und Ziel-Standort
     */
    public BigDecimal berechneUserDistanzInKilometer(@NonNull final InstitutionStandort zielStandort) {
        return berechneDistanzInKilometer(userService.getContextStandort().getStandort(), zielStandort);
    }

    /**
     * Berechnet die Distanz (in km) zwischen zwei gegebenen Institutions-Standorten. Je nach verwendeter
     * Geocoder-Implementierung ist dies eine Strassendistanz (GoogleMaps) oder die statisch berechnete
     * Luftlinie (LocationIQ).
     * @param zielStandort Der Start, von welchem aus die Distanz zum Ziel berechnet werden soll.
     * @param zielStandort Das Ziel, zu welchem die Distanz berechnet werden soll.
     * @return Distanz zwischen Benutzer-Hauptstandort und Ziel-Standort.
     */
    public BigDecimal berechneDistanzInKilometer(@NonNull final InstitutionStandort startStandort,
                                                 @NonNull final InstitutionStandort zielStandort) {
        return BigDecimal.valueOf(geocoder.kilometerBerechnen(startStandort, zielStandort));
    }

    /**
     * Berechnet die Distanz (in km) zwischen zwei gegebenen Match-Standorten. Je nach verwendeter
     * Geocoder-Implementierung ist dies eine Strassendistanz (GoogleMaps) oder die statisch berechnete
     * Luftlinie (LocationIQ).
     * @param startStandort Der Start, von welchem aus die Distanz zum Ziel berechnet werden soll.
     * @param zielStandort Das Ziel, zu welchem die Distanz berechnet werden soll.
     * @return Distanz zwischen Benutzer-Hauptstandort und Ziel-Standort.
     */
    public BigDecimal berechneDistanzInKilometer(@NonNull final MatchStandort startStandort,
                                                 @NonNull final MatchStandort zielStandort) {

        // Fixme - Standort als Interface umdefinieren
        return BigDecimal.valueOf(geocoder.kilometerBerechnen(startStandort, zielStandort));
    }

}
