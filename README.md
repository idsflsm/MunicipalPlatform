# PROGETTO IDS 2023/2024

## Componenti
- Luca Falappa, matricola 118387
- Maggye Salvucci, matricola 120065

## Descrizione del progetto
Il progetto propone la creazione di una piattaforma collaborativa per la valorizzazione e digitalizzazione di territori comunali, permettendo il caricamento di informazioni e/o contenuti (POI, Itinerary ed Attachment), i quali sono associati a punti fisici geolocalizzati. I contenuti potranno essere caricati direttamente a seconda del ruolo dello User (es. AuthorizedContributor) oppure essere sottoposti a validazione (es. AuthenticatedTourist) e validati successivamente.

## Backend
Il backend dell'applicativo è stato realizzato sfruttando il framework open-source SpringBoot, utilizzando il suo modulo web per lo sviluppo di API REST. Per quanto riguarda la persistenza dei dati, il servizio web fornito fa affidamento sulle JPA (Java Persistence API) per comunicare con il database PostgreSQL sottostante.
![image](https://github.com/idsflsm/MunicipalPlatform/assets/151014361/e23bf43a-2072-4d8c-8cd9-b4483aaa7ecf)

## Utilizzo
Nella cartella di progetto, contenente il file pom.xml, sono presenti due file di shell scripting (MunicipalPlatform-start, per ambienti Unix e Windows) per avviare automaticamente l’applicazione. Usare le API tramite Postman.
