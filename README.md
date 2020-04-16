# lokale Entwicklungsumgebung aufsetzen / Setup

##  benötigte installierte Werkzeuge/Tools/Frameworks

* Docker
* docker-compose
* IDE (Eclipse, IntelliJ, ...)
* git

## lokale Installation
Um Backend lokal lauffähig zu bekommen, müssen die folgenden Repositories geklont werden.

* rm-stack
* rm-backend

Alle Repositories sind zu finden unter https://github.com/remedyMatch .

### rm-stack starten

Da rm-stack mit lokalem backend gestartet werden soll, muss in rm-stack folgendes angepasst werden:

* `nginx.conf`
** In  `location /remedy/` auf internes backend switchen

  ```
    location /remedy/ {
#        proxy_pass          http://backend:8081/remedy/;
#        for local development
        proxy_pass          http://host.docker.internal:8081/remedy/;
        ...
  }
  ```

* `docker-compose.yml`
** In `reverseproxy` depends on backend auskommentieren

  ```
  reverseproxy:
    build: .
    ports:
    - 8008:8008
    depends_on:
      - auth
#      - backend
      - engine
      - frontend
  ```

** Der `backend` service komplett auskommentieren

  ```
#  backend:
#    image: remedymatch/backend:latest
# ...
  ```

### Backend

* Applikation starten (Klasse `RmBeApplication`)
** Beim start als aktives Profil "dbinit,dev" verwenden

## Rest API

siehe [API.md](https://github.com/remedyMatch/rm-backend/blob/master/API.md)

## Geocoding

siehe [GECODING.md](https://github.com/remedyMatch/rm-backend/blob/master/GEOCODING.md)