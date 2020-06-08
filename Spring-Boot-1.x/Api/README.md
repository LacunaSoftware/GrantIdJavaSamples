# Spring-Boot-1.x API Sample

    This samples uses Spring Boot 1.5.7 RELEASE.

# Files
    
    +---src
        +---main
            +---java
            ¦   +---lacuna
            ¦       +---sample
            ¦           +---api
            ¦                   Application.java               - Spring Boot main
            ¦                   BaseController.java            - Rest Controller that holds common method for controllers.
            ¦                   JwtAuthentication.java         - Authentication wrapper to make the code semantic clear.
            ¦                   JwtRequestFilter.java          - Authentication filter.
            ¦                   JwtUtil.java                   - Jwt parsing methods
            ¦                   Principal.java                 - Principal object wrapper to make the code semantic clear
            ¦                   ResourceController.java        - Rest controller that requires authentication.
            ¦                   SecurityConfigurer.java        - Security configurer used to add the jwt filter.
            ¦                   TokenValidationService.java    - token validation service.
            ¦                   
            +---resources                                      - Constant values and configurations.
                    application.properties
                    application.yml
              
       
# Customizing GrantId's Credentials

Replace the following properties in `application.properties` with your credentials:

```yml
    grantid.issuer: https://<your_subscription>.grantid.com
    grantid.api-scope: <your_api_scope>
```

# Running

Run the following command inside the project directory.

    ./mvnw spring-boot:run

# Using the endpoints

Use any application to make http requests easier e.g. [Postman](https://www.postman.com/). The web api will be running on [http://localhost:8092](http://localhost:8092) and there are three available endpoints `/home` which requires **no authentication**, `/secret` and `/claims` both requiring a Bearer token issued by GrantId in the authorization header .

**tip:** you can easily obtain a valid access token for this api by running this [webapp](https://github.com/LacunaSoftware/GrantIdJavaSamples/tree/master/Spring-Boot-1.x/WebApp).