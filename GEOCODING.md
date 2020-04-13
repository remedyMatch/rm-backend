# Geocoding

## Allgemein
Um Distanzen zwischen Standorten zu bestimmten, greifen wir im Backend auf die Funktionalität des Geocoding-Dienstleisters
LocationIQ zurück. Das heisst, wir schicken Anfragen aus dem Backend an das REST-Interface von LocationIQ und
finden so z.B. die Geokoordinaten von eingegeben Standorten und speichern diese intern beim Standort. Da so jedem
Standort in der Datenbank ein Geokoordinaten-Paar zugeordnet ist, können wir die Distanzen zwischen Standorten
berechnen.

## Code

Das Geocoding-Interface heißt Geocoder und bietet vier Funktionen an:
* Frage Adresse zu einem vorhandenen Geokoordinaten-Paar an
* Frage die Geokoordinaten-Paare zu einer strukturierten Adresse an (Klassen AdressQuery/KoordinatenQuery)
* Frage die Geokoordinaten-Paare zu einer unstrukturierten Adresse an (z.B. "Hüttenhospital Dortmund")
* Frage Adressvorschläge zu einem eingegebenen Adress-String an (z.B. "Paracelsus" --> Paracelsus Klinik Dortmund,
Paracelsus Klinik Hamburg, ...)

Implementiert wird das Geocoder-Interface derzeit vom LocationIQGeocoderClient, welcher bisher ausschliesslich
im StandortService verwendet wird. Die tatsächliche Kommunikation zwischen Backend und LocationIQ-Rest-API
wird in genannter Implementierung mithilfe eines RestTemplates durchgeführt.

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