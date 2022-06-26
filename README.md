# StarWars
Ejercicio de SPRING.
Para generar el jar y luego ejecutarlo:
```
mvn clean package
java -jar target/demo-0.0.1-SNAPSHOT.jar
```
Para generar la imagen Docker y lanzar el contenedor en local:
```
docker build -t springio/spring-starwars .
docker run -p 8080:8080 -t springio/spring-starwars
``` 
