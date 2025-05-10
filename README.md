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

## Configuration de la base de données

Configurer les informations de connexion à la base de données dans le fichier `application.properties` :

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/pharma
spring.datasource.username=ton_utilisateur
spring.datasource.password=ton_mot_de_passe

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

Fonctionnalités
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

Structure du projet
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


