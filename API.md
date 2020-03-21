# Rest API

# Artikel

## Artikel Suche

Suche nach Artikeln.

* *Request:*

  `GET /artikel/suche`

    curl -i -H 'Accept: application/json' http://localhost:7000/artikel/


* **Success Response:**

  * **Code:** 200 <br />
    **Content:** 
  ```
     {
		"key": "atemschutzmaske_FFP1_M3",
		"kategorieKey": "atemschutzmaske",
		"ean": "4046719303120", 
		"name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
		"beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
		"hersteller": "3M"
	}
  ```

* Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

    {
		"key": "atemschutzmaske_FFP1_M3",
		"kategorieKey": "atemschutzmaske",
		"ean": "4046719303120", 
		"name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
		"beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
		"hersteller": "3M"
	}

## Artikel lesen

Ein Artikel lesen.

### Request 

  `GET /artikel/{artikelKey}`

    curl -i -H 'Accept: application/json' http://localhost:7000/artikel/atemschutzmaske_FFP1_M3

### Response

    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

    {
		"key": "atemschutzmaske_FFP1_M3",
		"kategorieKey": "atemschutzmaske",
		"ean": "4046719303120", 
		"name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
		"beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
		"hersteller": "3M"
	}

	
## Artikel hinzufügen

Ein Artikel hinzufügen.

### Request 

  `GET /artikel/{artikelKey}`

    curl -i -H 'Accept: application/json' http://localhost:7000/artikel/atemschutzmaske_FFP1_M3

### Response

  ```
    HTTP/1.1 200 OK
    Date: Thu, 24 Feb 2011 12:36:30 GMT
    Status: 200 OK
    Connection: close
    Content-Type: application/json
    Content-Length: 2

    {
		"key": "atemschutzmaske_FFP1_M3",
		"kategorieKey": "atemschutzmaske",
		"ean": "4046719303120", 
		"name":	"Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M®",
		"beschreibung": "Atemschutzmaske \"8812\" FFP1 NR D vorgeformte Partikelmaske mit Ausatemventil - 3M® ..."
		"hersteller": "3M"
	}
  ```