Based on the blog series:
https://callistaenterprise.se/blogg/teknik/2015/05/20/blog-series-building-microservices/

# build
`gardlew clean build -x test`
`docker-compose build`
# deploy
## docker-compose
`docker-compose up`
## manually
- start the config server
  - http://localhost:9999/book-service/default
- HashiCorp Consul: http://localhost:8500/
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
- revisit all components and check whether they are actively supported or need replacement. Like Hystrix (Dashboard), Ribbon, Zuul, etc. 

# links
- https://dzone.com/articles/the-future-of-spring-cloud-microservices-after-net
- https://dzone.com/articles/microservices-with-spring-boot-spring-cloud-gatewa
- http://progressivecoder.com/a-detailed-guide-to-spring-cloud-consul/
