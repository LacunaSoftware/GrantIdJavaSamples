# Spring-Boot-2.x API Sample

    This samples uses Spring Boot 2.2.6.RELEASE.

# Files

    +---src
      +---main
         +---java
         ¦   +---lacuna
         ¦       +---sample
         ¦           +---api
         ¦                   Application.java                - SpringBoot main
         ¦                   BaseController.java             - Rest controller that holds common controller methods.
         ¦                   JwtRequestFilter.java           - Jwt Authentication Filter
         ¦                   JwtUtil.java                    - Jwt core method
         ¦                   ResourceController.java         - Rest controller with authentication
         ¦                   SecurityConfigurer.java         - Security customizer
         ¦                   TokenValidationService.java     - Class responsible to validate and authenticate tokens.
         ¦                   
         +---resources                                       - Constant values and configurations.
                 application.properties                      
                 application.yml               
                         
# Customizing GrantId's Credentials

Replace the following properties in `application.properties` with your credentials:

```yml
    grantid.issuer: https://<your_subscription>.grantid.com
    grantid.jwks-uri: https://<your_subscription>.grantid.com/.well-known/openid-configuration/jwks
    grantid.api-scope: <your_api_scope>
```
# Running

Run the following command inside the project directory.

    ./mvnw spring-boot:run

# Using the endpoints

Use any application to make http requests easier e.g. [Postman](https://www.postman.com/). The web api will be running on [http://localhost:8092](http://localhost:8092) and there are three available endpoints `/home` which requires **no authentication**, `/secret` and `/claims` both requiring a Bearer token issued by GrantId in the authorization header .

**tip:** you can easily obtain a valid access token for this api by running this [webapp](https://github.com/LacunaSoftware/GrantIdJavaSamples/tree/master/Spring-Boot-2.x/WebApp).