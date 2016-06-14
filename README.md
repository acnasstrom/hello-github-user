# Hello GitHub User #

An example microservice using Spring Boot/Cloud/MVC + Hystrix annotations + Netflix Feign and RxJava that looks up users on GitHub and displays a message using the information.

## Run application

```
./gradlew bootRun
```  

This will build the application and deploy to a local Tomcat at http://localhost:8080

## Try it out

Go to http://localhost:8080/users/{your-github-username}