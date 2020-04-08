# Rest API

Beschreibung der Rest-API für:
* Angebot
* Artikel und Artikel-Kategorie
* Bedarf
* Institution
* Person

# Angebot

## Übersicht

* Alle aktuelle Angebote laden

  `GET /angebot/suche`

* Alle Angebote meiner Institution laden

  `GET /angebot`

* Neues Angebot einstellen

  `POST /angebot`

* Angebot löschen

  `DELETE /angebot/{angebotId}`

* Angebot anfragen

  `POST /angebot/{angebotId}/anfrage`

* Angebot-Anfrage löschen

  `DELETE /angebot/{angebotId}/anfrage/{anfrageId}`


## Alle aktuelle Angebote laden

Liefert alle aktuelle (nicht bediente) Angebote.

* **Request:**

  `GET /angebot/suche`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "5bc4f514-c591-470e-a056-933f3ea00421",
      "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "anzahl": 10000,
      "rest": 2000,
      "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
      "standort": { "..." },
      "haltbarkeit": "2022-01-25T21:34:55",
      "steril": false,
      "originalverpackt": true,
      "medizinisch": false,
      "kommentar": "Wir haben 10000 Masken übrig",
      "entfernung": 25014
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/angebot/suche
  ```

## Alle Angebote meiner Institution laden

Liefert alle aktuelle (nicht bediente) Angebote, die Personen aus meiner Institution gemeldet haben.

* **Request:**

  `GET /angebot`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "5bc4f514-c591-470e-a056-933f3ea00421",
      "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "anzahl": 10000,
      "rest": 2000,
      "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
      "standort": { "..." },
      "haltbarkeit": "2022-01-25T21:34:55",
      "steril": false,
      "originalverpackt": true,
      "medizinisch": false,
      "kommentar": "Wir haben 10000 Masken übrig",
      "entfernung": 25014
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/angebot
  ```

## Neues Angebot einstellen

Neues Angebot melden.

* **Request:**

  `POST /angebot`

* **URL Params**

  None

* **Data Params**

  ```
  {
    "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "anzahl": 10000,
    "standortId": "6b0cd44b-5dfc-4475-8b48-491688bc0a34",
    "kommentar": "Wir haben 10000 Masken übrig",
    "haltbarkeit": "2022-01-25T21:34:55",
    "steril": false,
    "originalverpackt": true,
    "medizinisch": false
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "5bc4f514-c591-470e-a056-933f3ea00421",
    "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "anzahl": 10000,
    "rest": 2000,
    "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
    "standort": { "..." },
    "haltbarkeit": "2022-01-25T21:34:55",
    "steril": false,
    "originalverpackt": true,
    "medizinisch": false,
    "kommentar": "Wir haben 10000 Masken übrig",
    "entfernung": 25014
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Angebot löschen

Angebot löschen.

* **Request:**

  `DELETE /angebot/{angebotId}`

* **URL Params**

   **Required:**
 
   `angebotId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Angebot nicht gefunden." }`

* **Sample Call:**

  ```
   curl -X DELETE http://localhost:7000/angebot/5bc4f514-c591-470e-a056-933f3ea00421
  ```

## Angebot anfragen

Angebot anfragen.

* **Request:**

  `POST /angebot/{angebotId}/anfrage`

* **URL Params**

   **Required:**
 
   `angebotId=[UUID]`

* **Data Params**

  ```
  {
    "standortId": "6b0cd44b-5dfc-4475-8b48-491688bc0a34",
    "anzahl": 3000,
    "kommentar": "Wir brauchen 3000 Masken"
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "5bc4f514-c591-470e-a056-933f3ea00421",
    "angebot": { "..." },
    "institutionVon": { "..." },
    "standortVon": { "..." },
    "anzahl": 3000,
    "kommentar": "Wir brauchen 3000 Masken"
    "prozessInstanzId": "...",
    "status": "Offen"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Angebot nicht gefunden." }`

* **Sample Call:**

## Angebot-Anfrage löschen

Angebot-Anfrage löschen.

* **Request:**

  `DELETE /angebot/{angebotId}/anfrage/{anfrageId}`

* **URL Params**

   **Required:**
 
   `angebotId=[UUID]`
   `anfrageId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Angebot nicht gefunden." }`

* **Sample Call:**

  ```
   curl -X DELETE http://localhost:7000/angebot/5bc4f514-c591-470e-a056-933f3ea00421/anfrage/5bc4f514-c591-470e-a056-933f3ea00421
  ```

# Artikel

## Artikel-Kategorie Suche

Suche nach Artikel-Kategorien.

* **Request:**

  `GET /artikelkategorie/suche`

* **URL Params**

   **Optional:**
 
   `nameLike=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "name": "Atemschutzmasken"
    },
    {
      "key": "cd5c00ec-c328-4c8b-8278-098b4b5ea0dc",
      "name": "Overalls"
    },
    
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikelkategorie/suche?nameLike="maske"
  ```

## Artikel-Kategorie lesen

Eine Artikel-Kategorie lesen.

* **Request:**

  `GET /artikelkategorie/{kategorieId}`

* **Path Params**

   **Required:**
 
   `kategorieId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "name": "Atemschutzmasken"
  }
  ```
  
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel-Kategorie nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikelkategorie/77977887-78e8-4ee3-8a34-ec37fcfc5f8b
  ```

## Alle Artikel einer Kategorie

Suche nach Artikel einer Kategorien.

* **Request:**

  `GET /artikelkategorie/{kategorieId}/artikel`

* **URL Params**

   **Required:**
 
   `kategorieId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719303120", 
      "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
      "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
      "hersteller": "3M"
    },
    {
      "id": "atemschutzmaske_FFP2_M3",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719382583", 
      "name":	"Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
      "beschreibung": "Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
      "hersteller": "3M"
    },
     
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel-Kategorie nicht gefunden." }`

  OR
  
  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikelkategorie/77977887-78e8-4ee3-8a34-ec37fcfc5f8b/artikel"
  ```

## Artikel-Kategorie hinzufügen

Eine Artikel-Kategorie hinzufügen.

* **Request:**

  `POST /artikelkategorie/{kategorieId}`

* **Path Params**

   **Required:**
 
   `kategorieId=[UUID]`
   
* **Data Params**

  ```
  {
    "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "name": "Atemschutzmasken"
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "name": "Atemschutzmasken"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

    OR

  * **Code:** 409 CONFLICT <br />
    **Content:** `{ error : "Category with name exists already" }`

* **Sample Call:**

## Artikel-Kategorie aktualisieren

Eine Artikel-Kategorie aktualisieren.

* **Request:**

  `POST /artikelkategorie/{}`

* **Path Params**

* **Data Params**

  ```
  {
    "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "name": "Atemschutzmasken"
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "name": "Atemschutzmasken"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel-Kategorie nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Artikel Suche

Suche nach Artikeln.

* **Request:**

  `GET /artikel/suche`

*  **URL Params**

   **Optional:**
 
   `kategorieId=[UUID]`
   `nameLike=[string]`
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719303120", 
      "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
      "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
      "hersteller": "3M"
    },
    {
      "id": "atemschutzmaske_FFP2_M3",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719382583", 
      "name":	"Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
      "beschreibung": "Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
      "hersteller": "3M"
    },
     
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikel/suche?nameLike="maske"
  ```

## Artikel lesen

Ein Artikel lesen.

* **Request:**

  `GET /artikel/{artikelId}`

*  **Path Params**

   **Required:**
 
   `artikelId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
    "hersteller": "3M"
   }
  ```
  
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikel/bbeac45e-e296-4fad-878d-7e9b6e85a3d8
  ```

## Artikel hinzufügen

Ein Artikel hinzufügen.

* **Request:**

  `POST /artikel/`

* **Path Params**

* **Data Params**

  ```
  {
    "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
    "hersteller": "3M"
   }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
    "hersteller": "3M"
   }
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Artikel aktualisieren

Ein Artikel hinzufügen.

* **Request:**

  `PUT /artikel/{artikelId}`

* **Path Params**

   **Required:**
 
   `artikelId=[UUID]`

* **Data Params**

  ```
  {
    "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
    "hersteller": "3M"
   }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3Mï¿½ ..."
    "hersteller": "3M"
   }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

# Bedarf

## Übersicht

* Alle aktuelle Bedarfe laden

  `GET /bedarf/suche`

* Alle Bedarfe meiner Institution laden

  `GET /bedarf`

* Neues Bedarf melden

  `POST /bedarf`

* Bedarf löschen

  `DELETE /bedarf/{bedarfId}`

* Bedarf bedienen

  `POST /bedarf/{bedarfId}/anfrage`

* Bedarf-Anfrage löschen

  `DELETE /bedarf/{bedarfId}/anfrage/{anfrageId}`


## Alle aktuelle Bedarfe laden

Liefert alle aktuelle (nicht bediente) Bedarfe.

* **Request:**

  `GET /bedarf/suche`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "5bc4f514-c591-470e-a056-933f3ea00421",
      "artikelId": "12e79aaf-1edc-41b3-a83d-6b4f871dd5e3",
      "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "anzahl": 10000,
      "rest": 2000,
      "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
      "standort": { "..." },
      "steril": false,
      "medizinisch": false,
      "kommentar": "Wir brauchen 5000 Masken",
      "entfernung": 25014
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/bedarf/suche
  ```

## Alle Bedarfe meiner Institution laden

Liefert alle aktuelle (nicht bediente) Bedarfe, die Personen aus meiner Institution gemeldet haben.

* **Request:**

  `GET /bedarf`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "5bc4f514-c591-470e-a056-933f3ea00421",
      "artikelId": "12e79aaf-1edc-41b3-a83d-6b4f871dd5e3",
      "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
      "anzahl": 10000,
      "rest": 2000,
      "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
      "standort": { "..." },
      "steril": false,
      "medizinisch": false,
      "kommentar": "Wir brauchen 5000 Masken",
      "entfernung": 25014
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/bedarf
  ```

## Neues Bedarf einstellen

Neues Bedarf melden.

* **Request:**

  `POST /bedarf`

* **URL Params**

  None

* **Data Params**

  ```
  {
    "artikelId": "12e79aaf-1edc-41b3-a83d-6b4f871dd5e3",
    "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "anzahl": 10000,
    "standortId": "6b0cd44b-5dfc-4475-8b48-491688bc0a34",
    "kommentar": "Wir haben 10000 Masken übrig",
    "steril": false,
    "medizinisch": false
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "5bc4f514-c591-470e-a056-933f3ea00421",
    "artikelId": "12e79aaf-1edc-41b3-a83d-6b4f871dd5e3",
    "artikelVarianteId": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8",
    "anzahl": 10000,
    "rest": 2000,
    "institutionId": "43d30ef8-852b-41ae-9e5d-8f6b35d7b5b2",
    "standort": { "..." },
    "steril": false,
    "medizinisch": false,
    "kommentar": "Wir brauchen 5000 Masken",
    "entfernung": 25014
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Bedarf löschen

Bedarf löschen.

* **Request:**

  `DELETE /bedarf/{bedarfId}`

* **URL Params**

   **Required:**
 
   `bedarfId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Bedarf nicht gefunden." }`

* **Sample Call:**

  ```
   curl -X DELETE http://localhost:7000/bedarf/5bc4f514-c591-470e-a056-933f3ea00421
  ```

## Bedarf bedienen

Bedarf bedienen.

* **Request:**

  `POST /bedarf/{bedarfId}/anfrage`

* **URL Params**

   **Required:**
 
   `bedarfId=[UUID]`

* **Data Params**

  ```
  {
    "standortId": "6b0cd44b-5dfc-4475-8b48-491688bc0a34",
    "anzahl": 3000,
    "kommentar": "Wir haben 3000 Masken"
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "5bc4f514-c591-470e-a056-933f3ea00421",
    "bedarf": { "..." },
    "institutionVon": { "..." },
    "standortVon": { "..." },
    "anzahl": 3000,
    "kommentar": "Wir brauchen 3000 Masken"
    "prozessInstanzId": "...",
    "status": "Offen"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Bedarf nicht gefunden." }`

* **Sample Call:**

## Bedarf-Anfrage löschen

Bedarf-Anfrage löschen.

* **Request:**

  `DELETE /bedarf/{bedarfId}/anfrage/{anfrageId}`

* **URL Params**

   **Required:**
 
   `bedarfId=[UUID]`
   `anfrageId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Bedarf nicht gefunden." }`

* **Sample Call:**

  ```
   curl -X DELETE http://localhost:7000/bedarf/5bc4f514-c591-470e-a056-933f3ea00421/anfrage/5bc4f514-c591-470e-a056-933f3ea00421
  ```

# Institution

## Alle Institutionen laden

Liefert alle aktuelle Institutionen.

* **Request:**

  `GET /institution`

*  **URL Params**

  None
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
      "key": "isar_klinikum",
      "name":	"ISAR Klinikum®",
      "typ": "krankenhaus"
    },
    {
      "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
      "key": "arzt_praxis_xyz",
      "name":	"Arzt Praxis XYZ®",
      "typ": "doktor"
    },
     
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution
  ```

## Institutionen Suche (noch nicht umgesetzt)

Suche nach Institutionen.

* **Request:**

  `GET /institution/suche`

*  **URL Params**

   **Optional:**
 
   `nameLike=[string]`
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
      "key": "isar_klinikum",
      "name":	"ISAR Klinikum®",
      "typ": "krankenhaus"
    },
    {
      "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
      "key": "arzt_praxis_xyz",
      "name":	"Arzt Praxis XYZ®",
      "typ": "doktor"
    },
     
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution/suche?nameLike="xyz"
  ```

## Institution lesen (noch nicht umgesetzt)

Eine Institution lesen.

* **Request:**

  `GET /institution/{institutionKey}`

*  **Path Params**

   **Required:**
 
   `institutionKey=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
   }
  ```
  
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Institution nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution/krankenhaus_xy
  ```

## Institution hinzufugen (noch nicht umgesetzt)

Eine Institution hinzufügen.

* **Request:**

  `POST /institution/`

* **Path Params**

* **Data Params**

  ```
  {
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
   }
  ```

** ** Typ **
  `krankenhaus` / `doktor`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
   }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Institution aktualisieren (noch nicht umgesetzt)

Eine Institution aktualisieren.

* **Request:**

  `POST /institution/{institutionKey}`

* **Path Params**

   **Required:**
 
   `institutionKey=[string]`

* **Data Params**

  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
  }
  ```

** ** Typ **
  `krankenhaus` / `doktor`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Institution nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Meine Institution lesen 

Institution des angemeldetes Benutzer lesen.

* **Request:**

  `GET /institution/assigned`

*  **Path Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
   }
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution/assigned
  ```

## Meine Institution aktualisieren

Institution des angemeldetes Benutzer aktualisieren.

* **Request:**

  `PUT /institution/`

* **Path Params**

   **Required:**
 
   `institutionKey=[string]`

* **Data Params**

  ```
  {
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
  }
  ```

** ** Typ **
  `krankenhaus` / `doktor`

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b8de7af3-05a2-4fe4-8b07-f74071eb71f1",
    "key": "isar_klinikum",
    "name":	"ISAR Klinikum®",
    "typ": "krankenhaus"
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Alle Angebote einer Institution laden

Liefert alle Angebote der Institution von angemeldeten User. 

* **Request:**

  `GET /institution/angebot`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "5bc4f514-c591-470e-a056-933f3ea00421",
      "artikel": {
        "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8"
      },
      "anzahl": 10000,
      "kommentar": "Wir haben 10000 Masken übrig",
      "standort": "...",
      "haltbarkeit": "2022-01-25T21:34:55",
      "steril": false,
      "originalverpackt": true,
      "medizinisch": false
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution/angebot
  ```

## Alle Bedarfe einer Institution laden

Liefert alle Bedarfe der Institution von angemeldeten User. 

* **Request:**

  `GET /institution/bedarf`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "b872561f-dab9-43c2-bec9-d4e3694a7ea1",
      "artikel": {
        "id": "bbeac45e-e296-4fad-878d-7e9b6e85a3d8"
      },
      "anzahl": 10000
    },
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/institution/bedarf
  ```

# Person

## Alle Personen laden

Liefert alle Personen.

* **Request:**

  `GET /person`

*  **URL Params**

  None
   
* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  [
    {
      "id": "b4e17bb0-8558-44b1-ad2a-ec97bb012d45",
      "username": "peterarzt",
      "vorname": "Peter",
      "nachname": "Arzt",
      "telefon": "049..."
    },
    {
      "id": "9c20eeb1-1c9f-433c-8a20-4a01eca30770",
      "username": "juliahatetwas",
      "vorname": "Julia",
      "nachname": "Hat-Etwas",
      "telefon": "049..."
    },
     
    ...
  ]
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/person
  ```
  
## Person hinzufügen

Person erstellen.

* **Request:**

  `POST /person`

* **URL Params**

  None

* **Data Params**

  ```
  {
    "username": "peterarzt",
    "vorname": "Peter",
    "nachname": "Arzt",
    "telefon": "049..."
  }
  ```

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b4e17bb0-8558-44b1-ad2a-ec97bb012d45",
    "username": "peterarzt",
    "vorname": "Peter",
    "nachname": "Arzt",
    "telefon": "049..."
  }
  ```
  
* **Error Response:**

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

## Person lesen (noch nicht umgesetzt)

Person lesen.

* **Request:**

  `GET /person/{personId}`

* **URL Params**

   **Required:**
 
   `personId=[UUID]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b4e17bb0-8558-44b1-ad2a-ec97bb012d45",
    "username": "peterarzt",
    "vorname": "Peter",
    "nachname": "Arzt",
    "telefon": "049..."
  }
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

  OR

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Person nicht gefunden." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/person/b4e17bb0-8558-44b1-ad2a-ec97bb012d45
  ```

## Meine Person lesen 

Daten der angemeldeter Person lesen.

* **Request:**

  `GET /person/userInfo`

* **URL Params**

  None

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "id": "b4e17bb0-8558-44b1-ad2a-ec97bb012d45",
    "username": "peterarzt",
    "vorname": "Peter",
    "nachname": "Arzt",
    "telefon": "049..."
  }
  ```
  
* **Error Response:**

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/person/userInfo
  ```
