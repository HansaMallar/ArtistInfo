# ArtistInfo
This is a REST application using Spring Boot(Java 1.8 +) which when given the name of an artist, will produce the average number of words in their songs.

# Can be run via 
Eclipse
Run as maven build using => spring-boot:run

Command Line (after running mvn package)

java -jar {jar name)

# Sample Run (using Curl)
curl "http://localhost:8080/averagewords?artist=Beatles"

250.0

# The applications reads the list of titles for artists/groups from the src/resources folder. Currently, the folder contains samples for Beatles, ColdPlay, Elbow. 
This information can be easily be moved to another datasource or accessed through another api.
The application uses the REST API https://api.lyrics.ovh/v1/ to obtain the lyrics for a title.  
