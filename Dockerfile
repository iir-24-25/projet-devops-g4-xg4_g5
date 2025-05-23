## Utilisation de l'image de base OpenJDK
#FROM openjdk:17-jdk-slim
#
## Définir le répertoire de travail
#WORKDIR /app
#
## Copier le fichier jar dans le conteneur
#COPY target/pharma-*.jar pharma.jar
#
## Exposer le port utilisé par l'application
#EXPOSE 8080
#
## Commande pour exécuter l'application
#ENTRYPOINT ["java", "-jar", "pharma.jar"]
FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG JAR_FILE=target/pharma-app.jar
COPY target/pharma-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
