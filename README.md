# movies-graphql
A simple Java server API exposing data through GraphQL

Instructions to run:

## Add a file under src/main/resources/ called "config.properties" with the following content:
`sampleDataPath=src/main/resources/sampledata.json`

### Also add these lines(Optional, to retrieve "real" data from The Movie Database):

`movieEndpointUrl=https://api.themoviedb.org/3/discover/movie?api_key={API_KEY}&language=en-US&sort_by=popularity.desc&include_adult=false&include_video=false&page=1`

`configurationEndpointUrl=https://api.themoviedb.org/3/configuration?api_key={API_KEY}`

**NOTE**: Replace {API_KEY} with your own API_KEY, you can register for one for free. Check this link:
https://developers.themoviedb.org/3/getting-started/introduction

## Build and run

Make sure you have Maven installed and run:
`mvn clean install`

and then run the application with:
`mvn jetty:run`

navigate to http://localhost:8080 and you should see the GraphiQL editor
