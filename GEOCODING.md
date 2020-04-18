# Geocoding

## Allgemein
Um Distanzen zwischen Standorten zu bestimmten, greifen wir im Backend auf die Funktionalität des Geocoding-Dienstleisters
LocationIQ zurück. Das heisst, wir schicken Anfragen aus dem Backend an das REST-Interface von LocationIQ und
finden so z.B. die Geokoordinaten von eingegeben Standorten und speichern diese intern beim Standort. Da so jedem
Standort in der Datenbank ein Geokoordinaten-Paar zugeordnet ist, können wir die Distanzen zwischen Standorten
berechnen (Berechnung siehe: GeoCalc#distanzBerechnen).

## Code

Das Geocoding-Interface heißt Geocoder und bietet vier Funktionen an:
* Frage Adresse zu einem vorhandenen Geokoordinaten-Paar an
* Frage die Geokoordinaten-Paare zu einer strukturierten Adresse an (Klassen AdressQuery/KoordinatenQuery)
* Frage die Geokoordinaten-Paare zu einer unstrukturierten Adresse an (z.B. "Hüttenhospital Dortmund")
* Frage Adressvorschläge zu einem eingegebenen Adress-String an (z.B. "Paracelsus" --> Paracelsus Klinik Dortmund,
Paracelsus Klinik Hamburg, ...)

Es gibt derzeit zwei Implementierungen für dieses Interface: 
* `LocationIQGeocoderClient` - Richtige Implementierung. Benutzt die LocationIQ API 
(gültiger API key nötig!). Wird benutzt, wenn das Profil "geo" verwendet wird. In diesem
Fall muss in der datei `application-geo.yaml` ein gültiger API key hinterlegt werden,
 ansonsten liefert der Service eine entsprechende Fehlermeldung, welche vom Backend weiter
  gereicht wird.
* `MockGeocoderClient` - Mock Implementierung. Wird für lokale Entwicklung verwendet.
Arbeitet mit statischen Adressen / Koordinaten. Wird benutzt, wenn __NICHT__ das Profil
 "geo" verwendet wird.

Die entsprechende Interface-Implementierung wird dann im StandortService verwendet wird. Die tatsächliche Kommunikation 
zwischen Backend und [LocationIQ-Rest-API](https://locationiq.com/docs) wird in genannter Implementierung mithilfe eines 
RestTemplates durchgeführt.

__Achtung__: Das Profil "prod" verwendet automatisch auch das Profil "geo". Es MUSS dann
also ein gültiger API Key hinterlegt sein in der `application-geo.yaml`.

## Endpunkte

Bisher bieten wir nur einen Endpunkt für das Frontend an (StandortController). Dieser dient zu einer möglichen 
Auto-Vervollständigung während einer Adresseingabe. Gibt ein Benutzer beispielsweise "Hütte" in ein Standort-(Text)Feld 
ein, so kann liefert der Endpunkt für den String "Hütte" diverse vollständige Adressen zur Vorauswahl an. 
Diese könntendem Benutzer dann angeboten werden. Das Frontend verwendet diese Funktionalität bisher jedoch 
nicht.

Adressvorschlaege liefern.

* **Request:**

  `GET /standort/vorschlaege`

* **URL Params**

  standort (String)

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  ["Adressvorschlag 1","Adressvorschlag 2"]
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`