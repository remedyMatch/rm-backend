# Rest API

# Artikel

## Artikel-Kategorie Suche

Suche nach Artikel-Kategorien.

* **Request:**

  `GET /artikelkategorie/suche`

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
      "key": "atemschutzmasken",
      "name": "Atemschutzmasken"
    },
    {
      "key": "overalls,
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

## Artikel Suche

Suche nach Artikeln.

* **Request:**

  `GET /artikel/suche`

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
      "key": "atemschutzmaske_FFP1_M3",
      "kategorieKey": "atemschutzmasken",
      "ean": "4046719303120", 
      "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
      "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
      "hersteller": "3M"
    },
    {
      "key": "atemschutzmaske_FFP2_M3",
      "kategorieKey": "atemschutzmasken",
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

  `GET /artikel/{artikelKey}`

*  **Path Params**

   **Required:**
 
   `artikelKey=[string]`

* **Data Params**

  None

* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
  {
    "key": "atemschutzmaske_FFP1_M3",
    "kategorieKey": "atemschutzmasken",
    "ean": "4046719303120", 
    "name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
    "beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
    "hersteller": "3M"
   }
  ```
  
* **Error Response:**

  * **Code:** 404 NOT FOUND <br />
    **Content:** `{ error : "Artikeln doesn't exist." }`

  OR

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**

  ```
   curl -i -H 'Accept: application/json' http://localhost:7000/artikel/atemschutzmaske_FFP1_M3
  ```

## Artikel hinzufügen

Ein Artikel hinzufügen.

* **Request:**

  `POST /artikel/{artikelKey}`

*  **Path Params**

   **Required:**
 
   `artikelKey=[string]`

* **Data Params**

  ```
  {
    "key": "atemschutzmaske_FFP1_M3",
    "kategorieKey": "atemschutzmasken",
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
    "key": "atemschutzmaske_FFP1_M3",
    "kategorieKey": "atemschutzmasken",
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

  * **Code:** 401 UNAUTHORIZED <br />
    **Content:** `{ error : "You are unauthorized to make this request." }`

* **Sample Call:**
