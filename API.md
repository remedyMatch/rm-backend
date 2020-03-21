# Rest API

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
      "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
      "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
      "hersteller": "3M"
    },
    {
      "id": "atemschutzmaske_FFP2_M3",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719382583", 
      "name":	"Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
      "beschreibung": "Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
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
      "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
      "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
      "hersteller": "3M"
    },
    {
      "id": "atemschutzmaske_FFP2_M3",
      "kategorieId": "77977887-78e8-4ee3-8a34-ec37fcfc5f8b",
      "ean": "4046719382583", 
      "name":	"Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
      "beschreibung": "Spezialmaske \"9926\" FFP2 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
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
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
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

  * **Code:** 400 BAD REQUEST <br />
    **Content:** `{ error : "..." }`

  OR
  
  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikel nicht gefunden." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

# Institution

## Institutionen Suche

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

## Institution lesen

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

## Institution hinzufugen 

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


## Institution aktualisieren 

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
