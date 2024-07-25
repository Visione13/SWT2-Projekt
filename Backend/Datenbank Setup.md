Es sollte jetzt ganz einfach funktionieren.

1. Habt Docker installiert
2. In einem CLI, gebt folgende 2 Commands ein:
   1. docker pull jonathan061/frodo_db:latest
   2. docker run -p 3307:3306 --name frodo_db -v frodo-db-volume:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=frodo_logging_db -d jonathan061/frodo_db:latest

Erklärung:
docker pull -> Zieht euch das Image, auf welchem ich eine Test Datenbank angelegt habe.
docker run -> Erstellt und lässt den Container laufen.
-p 3307:3306 -> Port forwarding damit man mit lokalem Port 3307 auf den MySQL Port 3306 zugreift (Keine Ahnung warum das relevant ist)
--name -> Name des Containers
-v -> Erstellt ein Volumen in dem Daten gespeichert werden (der DB), auch wenn der Container gelöscht wird.
-e MYSQL_ROOT_PASSWORD -> Passwort mit dem man über root User auf die DB zugreift
-e MYSQL_DATABASE -> Name der Datenbank
-d Container wird detached gestartet, nimmt also nicht euer Terminal in anspruch
jonathan061/frodo_db:latest -> Das Image aus dem der Container erstellt werden soll.

Da sollten hoffentlich jetzt bereits Datenbank und ein Datensatz zum Testen sein.