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
- Gateway: http://localhost:8080/bookstore-client/books/titles
- Book service: http://localhost:8020/books
- Bookstore client service: http://localhost:8070/books/titles
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
- https://blog.jdriven.com/2019/11/spring-cloud-gateway-with-openid-connect-and-token-relay/
- https://blog.jdriven.com/2019/10/spring-security-5-2-oauth-2-exploration-part1/
- https://github.com/spring-projects/spring-security/wiki/OAuth-2.0-Migration-Guide
- https://www.baeldung.com/spring-security-5-oauth2-login
- https://cloud.spring.io/spring-cloud-static/spring-cloud-security/2.2.0.RELEASE/reference/html/#_token_relay
- https://itnext.io/microservices-with-spring-boot-and-spring-cloud-20f689b17fc7
- https://www.baeldung.com/spring-cloud-securing-services
