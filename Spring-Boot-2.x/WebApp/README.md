# Spring-Boot-2.x WebApp Sample

    This samples uses Spring Boot 2.2.7.RELEASE.

# Files

    +---src
        +---main
            +---java
            ¦   +---lacuna
            ¦       +---sample
            ¦           +---webappintegration
            ¦                   BaseController.java                - Controller MVC that holds common contrller methods.
            ¦                   ResourceController.java            - Controller MVC that requires authentication.
            ¦                   SecurityConfigurer.java            - Security customizer that implements OpenId Authentication.
            ¦                   WebappIntegrationApplication.java  - SpringBoot main.
            ¦                   
            +---resources                                          - Constant values and configurations.
                ¦   application.properties
                ¦   application.yml
                ¦   
                +---templates                                      - Application front-end.
                    ¦   index.html
                    ¦   privacy.html
                    ¦   privateRoute.html
                    ¦   
                    +---fragments
                            footer.html
                            header.html
                            main_layout.html
                                          
# Customizing GrantId's Credentials

Replace the following properties in `application.yml` with your credentials:

```yml
spring:
    security:
        oauth2:
            client:
                registration: 
                    grant-id: 
                        client-id: <you_client_id>
                        client-secret: <your_client_secret>
                        client-authentication-method: post
                        authorization-grant-type: authorization_code
                        scope: 
                            - openid
                            - profile
                            - <your_api_scope>
                provider:
                    grant-id:
                        issuer-uri: https://<your_subscription>.grantid.com
```

# Running

Run the following command inside the project directory.

    ./mvnw org.springframework.boot:spring-boot-maven-plugin:run

# The application

The application demonstrate the use of the Authorization Code Flow already builtin in the Spring Security framework. The only protected route is the route [http://localhost:8091/Home/PrivateRoute](http://localhost:8091/Home/PrivateRoute) and if the user has no authentication, he will be redirected to GrantId to perform a login, after this he is redirected back to the application where he'll be able to see his access and the identity tokens and the token's claim.