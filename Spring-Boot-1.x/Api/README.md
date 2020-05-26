# Spring-Boot-1.x API Sample

    This samples uses Spring Boot 1.5.7 RELEASE.

# Running

Run the following command inside the project directory.

    ./mvnw spring-boot:run

# Using the endpoints

Use any application to make http requests easier e.g. [Postman](https://www.postman.com/). The web api will be running on [http://localhost:8092](http://localhost:8092) and there are three available endpoints `/home` which requires **no authentication**, `/secret` and `/claims` both requiring a Bearer token issued by GrantId in the authorization header .

**tip:** you can easily obtain a valid access token for this api by running this [webapp]().