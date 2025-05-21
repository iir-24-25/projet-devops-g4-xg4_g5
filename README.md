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

---

## Design patterns utilisés

- **Singleton (via @Service)**
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

Ils sont écrits avec JUnit 5 et Mockito pour simuler les dépendances.

---

Pour lancer les tests, utilise la commande Maven suivante :  
```bash
mvn test




## Demonstration
lien: https://youtu.be/J4Sg0SQTUhc

