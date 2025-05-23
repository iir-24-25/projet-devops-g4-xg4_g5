# Pharma_Project
# Pharma - Application de gestion des médicaments

Pharma est une application web développée avec Spring Boot 3.4.5 permettant la gestion complète des médicaments, des fournisseurs, des ventes, des utilisateurs, ainsi qu’un tableau de bord statistique. L'application inclut également des fonctionnalités de recherche, d'import/export de données et une interface d'authentification sécurisée.


## Technologies utilisées

- Java 17
- Spring Boot 3.4.5
- Spring Data JPA
- Spring Security
- Spring Web
- Spring Validation
- Thymeleaf
- ModelMapper
- Lombok
- MySQL
- Maven

## Fonctionnalités
Authentification
Enregistrement et connexion des utilisateurs

Gestion des rôles (admin, utilisateur)

Sécurisation des pages sensibles avec Spring Security

Médicaments
Ajouter, modifier, supprimer, consulter

Rechercher par nom, référence, ou catégorie

Suivi de stock et des quantités

Fournisseurs
Ajouter, modifier, supprimer, consulter

Lier les fournisseurs aux médicaments

Recherche par nom ou localisation

Ventes
Enregistrement des ventes de médicaments

Calcul automatique du total de chaque vente

Mise à jour du stock après vente

Dashboard
Statistiques globales : ventes mensuelles, stock faible, top produits

Visualisation graphique des données (à intégrer si besoin)

Recherche
Recherche dynamique sur les médicaments, fournisseurs, ventes

Import / Export
Export des données en formats CSV ou Excel

Import de fichiers pour mise à jour en masse

## Conteneurisation et Orchestration de l’Application

Objectif
Le but de cette section est de présenter le processus de conteneurisation de l’application à l’aide de Docker, ainsi que son déploiement et sa gestion via un orchestrateur de conteneurs, en l’occurrence Docker Swarm. Cette approche permet de garantir la portabilité, la scalabilité et la résilience de l’application Pharma.

Conteneurisation avec Docker
La conteneurisation consiste à emballer l'application avec toutes ses dépendances dans une image exécutable, appelée conteneur. Cela garantit que l'application s'exécutera de manière cohérente sur tous les environnements.

Un fichier Dockerfile a été créé pour construire l’image de l’application pharma-app.

Cette image est ensuite utilisée avec Docker Compose pour créer les conteneurs nécessaires à l’application :

pharma-app : conteneur de l'application web

pharma-db : conteneur de la base de données MySQL

Orchestration avec Docker Swarm
Docker Swarm est un outil d’orchestration de conteneurs natif de Docker. Il permet de gérer efficacement le déploiement, la mise à l’échelle, les mises à jour continues, et la tolérance aux pannes.

Les étapes suivies :

Initialisation du Swarm :
docker swarm init

Déploiement du stack :
docker stack deploy -c docker-compose.yml pharma_stack

Vérification des services déployés :
docker stack services pharma_stack

Ce système assure la haute disponibilité et l’équilibrage de charge entre les services déployés.

Conclusion
Grâce à Docker et Docker Swarm, l’application Pharma est désormais conteneurisée, facilement déployable et prête pour un environnement de production. Cette architecture permet une gestion centralisée et efficace des services, ainsi qu'une meilleure résilience face aux pannes et une montée en charge simplifiée.

## Monitoring avec Prometheus et Grafana
Le système de monitoring a été mis en place avec succès en utilisant Prometheus comme collecteur de métriques et Grafana pour la visualisation.


     Configuration
Prometheus est configuré pour scrapper les métriques exposées par l’application Spring Boot via l’endpoint
Grafana est connecté à Prometheus en tant que source de données.
La métrique up confirme que l’instance pharma-app est bien surveillée.

Exemple de métrique visible: ![t](https://github.com/user-attachments/assets/24ca9643-d061-480f-8433-cb5777db8770)

Visualisation des performances
Des tableaux de bord personnalisés ont été créés dans Grafana pour suivre les métriques clés :

Latence moyenne des requêtes HTTP

Taux de requêtes par code de statut (2xx, 4xx, 5xx)

Nombre total de requêtes sur une période donnée

Utilisation de la fonction rate()
Les compteurs comme http_server_requests_seconds_count sont monotones (toujours croissants). Pour extraire des données exploitables, on utilise rate() afin d’obtenir la variation du compteur dans un intervalle :

## Bonnes pratiques appliquées


- **Architecture en couches (Layered Architecture)**
    - Séparation claire entre `controllers`, `services`, `repositories` et `models`.
    - Facilite la maintenance, les tests, et la compréhension du code.

- **Injection de dépendances avec Spring (@Autowired)**
    - Favorise le découplage des composants.
    - Simplifie les tests unitaires.

- **Validation des données**
    - Utilisation des annotations `@Valid`, `@NotEmpty`, `@Email` pour garantir la qualité des données reçues.

- **Gestion sécurisée des mots de passe**
    - Encodage des mots de passe via `PasswordEncoder`.

- **Utilisation de Lombok**
    - Réduction du code répétitif (getters/setters, constructeurs).
  - Séparation claire entre `controllers`, `services`, `repositories` et `models`.
  - Facilite la maintenance, les tests, et la compréhension du code.

- **Injection de dépendances avec Spring (@Autowired)**
  - Favorise le découplage des composants.
  - Simplifie les tests unitaires.

- **Validation des données**
  - Utilisation des annotations `@Valid`, `@NotEmpty`, `@Email` pour garantir la qualité des données reçues.

- **Gestion sécurisée des mots de passe**
  - Encodage des mots de passe via `PasswordEncoder`.

- **Utilisation de Lombok**
  - Réduction du code répétitif (getters/setters, constructeurs).

---

## Design patterns utilisés

- **Singleton (via @Service)**
    - Les services Spring sont instanciés une seule fois par le conteneur Spring, ce qui garantit une instance unique.

- **Repository pattern**
    - Les interfaces `UserRepository` étendent `JpaRepository` pour gérer l’accès aux données de manière abstraite.

- **Dependency Injection**
    - Les dépendances sont injectées automatiquement par Spring, ce qui permet un couplage faible et facilite les tests.
  - Les services Spring sont instanciés une seule fois par le conteneur Spring, ce qui garantit une instance unique.

- **Repository pattern**
  - Les interfaces `UserRepository` étendent `JpaRepository` pour gérer l’accès aux données de manière abstraite.

- **Dependency Injection**
  - Les dépendances sont injectées automatiquement par Spring, ce qui permet un couplage faible et facilite les tests.

---
## Tests unitaires

Ce projet inclut des tests unitaires pour garantir la fiabilité et la qualité du code métier.

Les classes testées incluent notamment :
- `ClientServiceImpl`
- `FournisseurServiceImpl`
- `VenteServiceImpl`

Ces tests couvrent les principales fonctionnalités de chaque service, comme :
- La gestion des clients
- La gestion des fournisseurs
- Le traitement des ventes
Les classes testées incluent notamment :  
- `ClientServiceImpl`  
- `FournisseurServiceImpl`  
- `VenteServiceImpl`  

Ces tests couvrent les principales fonctionnalités de chaque service, comme :  
- La gestion des clients  
- La gestion des fournisseurs  
- Le traitement des ventes  

Ils sont écrits avec JUnit 5 et Mockito pour simuler les dépendances.

---

Pour lancer les tests, utilise la commande Maven suivante :

Pour lancer les tests, utilise la commande Maven suivante :  

mvn test

---
## Structure du projet

src/

├── main/

│   ├── java/org/example/pharma/

│   │   ├── controllers/

│   │   ├── services/

│   │   ├── models/

│   │   ├── repositories/

│   │   └── PharmaApplication.java

│   └── resources/

│       ├── templates/

│       ├── static/

│       └── application.properties

└── test/

![ar](https://github.com/user-attachments/assets/a775b461-fd46-4a8c-a132-210713895cfa)


(https://github.com/user-attachments/assets/356a1883-6f8e-472f-9862-0dcd9ec7cce3)

## Configuration de la base de données

Configurer les informations de connexion à la base de données dans le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pharma_db
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
server.port=8080
Lancement de l'application
Cloner le dépôt :
git clone https://github.com/ton-utilisateur/pharma.git
cd pharma


Lancer l'application avec Maven :
mvn spring-boot:run
Accéder à l'application :

http://localhost:8080/register

```





## Demonstration
lien: https://youtu.be/J4Sg0SQTUhc

