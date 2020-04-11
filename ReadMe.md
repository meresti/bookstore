Based on the blog series:
https://callistaenterprise.se/blogg/teknik/2015/05/20/blog-series-building-microservices/

# build
`gardlew clean build -x test`

`docker-compose build`
# deploy
`docker-compose up`
## Services
- Auth service
- HashiCorp Consul: http://localhost:8500/
- Zipkin server: http://localhost:9411/zipkin/
- Prometheus: http://localhost:9090/graph
- Grafana: http://localhost:3000/
- Kibana: http://localhost:5601/
- Bookstore service: http://localhost:8020/books
- Bookstore client service: http://localhost:8080/books/titles
- Hello service: http://localhost:8010/encrypted-message

# todo
- fix security
- revisit all components and check whether they are actively supported or need replacement. Like Hystrix (Dashboard), Ribbon, Zuul, etc. 

# links
- https://dzone.com/articles/the-future-of-spring-cloud-microservices-after-net
- https://dzone.com/articles/microservices-with-spring-boot-spring-cloud-gatewa
- http://progressivecoder.com/a-detailed-guide-to-spring-cloud-consul/
- https://github.com/resilience4j/resilience4j-spring-boot2-demo
- https://github.com/spring-cloud/spring-cloud-sleuth
- https://cassiomolin.com/2019/06/30/log-aggregation-with-spring-boot-elastic-stack-and-docker/

