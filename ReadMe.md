# build
`gardlew clean build -x test`
`docker-compose build`
# deploy
## docker-compose
`docker-compose up`
## manually
- start the config server
  - http://localhost:9999/book-service/default
- start the eureka service
  - http://localhost:8761
- start the hystrix dashboard
  - http://localhost:8010/hystrix
- start the official zipkin server (https://github.com/openzipkin/zipkin)
  - http://localhost:9411/zipkin/
- start the auth service
- start the bookstore service
  - http://localhost:8000/books
- start the bookstore client service
  - http://localhost:8080/books/titles

# todo
- fix security
- revisit all components and check whether they are actively supported or need replacement. Like Hystrix (Dashboard), etc. 