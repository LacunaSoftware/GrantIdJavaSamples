# Spring-Boot-1.x WebApp Sample

    This samples uses Spring Boot 1.5.7.RELEASE.

# Files


    +---src
      +---main
          +---java
          ¦   +---lacuna
          ¦       +---sample
          ¦           +---webappgrantid
          ¦                   OAuth2LogoutHandler.java            - Custom logout handler for spring security: logout both on application and GrantId.
          ¦                   ResourceController.java             - Controller MVC that requires authentication.
          ¦                   SecurityConfigurer.java             - Security configurer for OAuth2.
          ¦                   TokenUtil.java                      - Claims extractor.
          ¦                   WebappGrantidApplication.java       - SpringBoot main.
          ¦                   
          +---resources                                           - Constant values and configurations.
              ¦   application.properties 
              ¦   application.yml
              ¦   
              +---templates                                       - Application front end.
                  ¦   index.html
                  ¦   privacy.html
                  ¦   privateRoute.html
                  ¦   
                  +---fragments
                          footer.html
                          header.html
                          main_layout.html
                      

# Customizing GrantId's Credentials

```yml
security:
  oauth2:
    client:
      clientId: <your_client_id>
      clientSecret: <your_client_secret>
      accessTokenUri: https://<your_subscription>.grantid.com/connect/token
      userAuthorizationUri: https://<your_subscription>.grantid.com/connect/authorize
      clientAuthenticationScheme: form
      scope:
        - openid
        - profile
        - <your_api_scope>
    resource:
      user-info-uri: https://<your_subscription>.grantid.com/connect/userinfo
``` 

# Running

Run the following command inside the project directory.

    ./mvnw spring-boot:run

# The application

The application demonstrate the use of the Authorization Code Flow already builtin in the Spring Security framework. The only protected route is the route [http://localhost:8091/Home/PrivateRoute](http://localhost:8091/Home/PrivateRoute) and if the user has no authentication, he will be redirected to GrantId to perform a login, after this he is redirected back to the application where he'll be able to see his access and the identity tokens and the token's claim.