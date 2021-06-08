# ArtistInfo
This is a REST application using Spring Boot which when given the name of an artist, will produce the average number of words in their songs.
Requisites - Java 1.8+ to compile and run, Maven for the build

# Can be run via 
Eclipse
Run as maven build using => spring-boot:run

Command Line (after running mvn package)

java -jar {jar name)

# Sample Run (using Curl)
curl "http://localhost:8080/averagewords?artist=ColdPlay"
curl "http://localhost:8080/averagewords?artist=Ed%20Sheeran"

250.0

# Additional notes
The application uses the REST API https://api.lyrics.ovh/v1/ to obtain the lyrics for a title. The application can also be converted to a spring-boot web application to provide a web interface to the api. 
