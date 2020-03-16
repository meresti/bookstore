FROM openjdk:11-jdk as build
WORKDIR /workspace/app

ARG PROJECT
RUN mkdir -p ${PROJECT}

#COPY gradlew .
#COPY .gradle .gradle
#COPY gradle gradle
#COPY build.gradle .
#COPY settings.gradle .
#COPY ${PROJECT}/build.gradle ${PROJECT}/build.gradle
#COPY ${PROJECT}/src ${PROJECT}/src

#RUN ./gradlew :${PROJECT}:clean :${PROJECT}:build -x test
COPY ${PROJECT}/build ${PROJECT}/build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../../${PROJECT}/build/libs/*.jar)

FROM openjdk:11-jdk
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/build/dependency
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ARG PORT
ENV PORT=${PORT:-8080}
EXPOSE ${PORT}

ARG MAIN_CLASS
ENV MAIN_CLASS=${MAIN_CLASS}
ENTRYPOINT ["sh", "-c", "java -cp app:app/lib/* ${MAIN_CLASS}"]
