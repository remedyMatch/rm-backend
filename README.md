# lokale Entwicklungsumgebung aufsetzen / Setup

##  benötigte installierte Werkzeuge/Tools/Frameworks

* Docker
* docker-compose
* IDE (IntelliJ, VSCode, ...)
* git
* node / npm

## lokale Installation
Um sowohl Frontend als auch Backend lokal lauffähig zu bekommen, müssen die folgenden
Repositories geklont werden.

* rm-gateway
* rm-auth
* rm-engine
* rm-backend
* rm-frontend

Alle Repositories sind zu finden unter https://github.com/remedyMatch .

### Gateway

* Applikation starten (Klasse `GatewayApplication`)

### Auth

* `docker-compose up` im Project-Root ausführen (Stammverzeichnis wo auch die Datei docker-compose.yml liegt)
* Alle bisherigen manuellen Settings sind in _conf/rmio.realm.json_ hinterlegt. 
* _conf/rmio.realm.json_ wird beim Startup importiert und legt den Realm _rmio_ mit all seinen Settings an.
* Die Credentials für den Emailversand sind *nicht* hinterlegt. Diese bitte bei den Verantwortlichen erfragen.

### Engine

* `cd etc/postgres`
* `./build_pg.sh`
* `./run_pg.sh`
* Applikation starten (Klasse `RmeEngineApplication`)

### Backend

* `cd etc/postgres`
* `./build_pg.sh`
* `./run_pg.sh`
* Applikation starten (Klasse `RmBeApplication`)
    * Beim __erstmaligen__ start als aktives Profil "dbinit,prod" verwenden 
    (In der RunConfiguration)
        * Grund --> Damit wird die DbInit Config angezogen und die pusht dann 
        Testdaten in die Db
    * Bei jedem weiteren Start das Profil "prod" verwenden

### Frontend

* npm install
* npm start

Nach Ausführung der obigen Schritte kann http://localhost:8080 aufgerufen werden und 
man kann sich mit dem admin-Benutzer einloggen. Wurde die Backend-Applikation einmalig 
mit dem profile "test" gestartet, sollten nun auch Beispiel-Daten in der Applikation zur 
Verfügung stehen. Dies kann einfach getestet werden indem man über den Bedarf-Button versucht 
einen neuen Bedarf anzulegen. Hier sollten nun Beispiel-Kategorien und -Artikel zur Auswahl 
angeboten werden. Das Anlegen eines neuen Bedarfs sollte ebenso funktionieren.


### Details

Hier eine genauere Beschreibung was mit den Punkten "Applikation starten (Klasse `XYZ`)" 
gemeint ist:

Als Beispiel betrachten wir das Gateway --> *Applikation starten (Klasse `GatewayApplication`)*

Um das zu tun, kann man beispielsweise IntelliJ benutzen. Hier eine Klick-Anleitung:

* In IntelliJ: `File` - `Open...`
* `rm-gateway` Ordner auswählen (darin liegen z.B. die Dateien `Dockerfile` und `docker-compose.yml`)
* Java-JDK auswählen: `File` - `Project Structure...`
* Sicherstellen, dass ein valides JDK mit Java Version 11 ausgwählt ist --> `Apply`
* Sicherstellen, dass das Projekt als gradle Projekt von IntelliJ erkannt wurde (IntelliJ
fragt in der Regel, nachdme das Java-JDK ausgewählt wurde, ob das Projekt ein Gradle-Projekt
ist. Das bestätigen. Anschliessend zur Klasse `src/main/java/io/remedymatch/gateway/GatewayApplication.java`
navigieren. IntelliJ sollte diese Klasse nun korrekt, als Spring Boot application class
registrieren und einen entsprechenden "Play-Button" anzeigen, mit welchem man die Applikation
starten kann. Falls dies nicht der Fall ist, kann man die Run Configuration auch manuell 
konfigurieren (rechts oben die Kombobox neben dem "Run"- und "Debug"-Button.)

# Geocoding

siehe [GECODING.md](https://github.com/remedyMatch/rm-backend/blob/master/GEOCODING.md)