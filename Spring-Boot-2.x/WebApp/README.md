# Spring-Boot-2.x WebApp Sample

    This samples uses Spring Boot 2.2.7.RELEASE.

# Running

Run the following command inside the project directory.

    ./mvnw spring-boot:run

# The application

The application demonstrate the use of the Authorization Code Flow already builtin in the Spring Security framework. The only protected route is the route [http://localhost:8091/Home/PrivateRoute](http://localhost:8091/Home/PrivateRoute) and if the user has no authentication, he will be redirected to GrantId to perform a login, after this he is redirected back to the application where he'll be able to see his access and the identity tokens and the token's claim.