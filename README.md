# Backend for freelancer job advertising web app.

## Running container:
    In order to run container you need to run 
    docker-compose up
    command while being inside project folder.
    Command requires Docker installed.

    Java executable will be compiled automaticly

# Endpoints
    The app provides the following endpoints:
    POST localhost:8080/api/auth/register 
        - for registering new account
        - returns JSON Web Token used for authentication
        - takes JSON with fields: "name": String, "email": String, "password": String
    POST localhost:8080/api/auth/authenticate 
        - for logging in
        - returns JSON Web Token used for authentication
        - takes JSON with fields: "email": String, "password": String
    
## Using endpoint listed below requires providing a bearer token that can be acquired through the first two endpoints

    POST localhost:8080/api/posts
        - for creating new post
        - takes JSON with fields: "title": String, "text": String, "marker_location_lng":  float, "marker_location_lat": float
    GET localhost:8080/api/posts
        - for accessing all created posts
    GET localhost:8080/api/posts/user
        - for accessing all posts associated with current user
    GET localhost:8080/api/posts/{title}
        - for accessing posts with a given title
    PUT localhost:8080/api/posts/{id}
        - for updating posts with a given id
        - takes JSON with fields: "title": String, "text": String, "marker_location_lng":  float, "marker_location_lat": float
    DELETE localhost:8080/api/posts/{id}
        - for deleting post with a given id


