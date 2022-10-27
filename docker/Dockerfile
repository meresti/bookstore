FROM openjdk:11-jdk AS application

WORKDIR /usr/app

ARG PROJECT

ARG PORT
ENV PORT=${PORT:-8080}

ARG DEBUG_PORT
ENV DEBUG_PORT=${DEBUG_PORT:-5005}

EXPOSE ${PORT} ${DEBUG_PORT}

ARG JAR_FILE
ENV JAR_FILE=${JAR_FILE}

COPY ${PROJECT}/build/libs/${JAR_FILE} app.jar

ENTRYPOINT ["sh", "-c", "java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:${DEBUG_PORT} -jar app.jar"]
