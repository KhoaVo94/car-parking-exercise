# Dockerfile

# Runner
FROM --platform=linux/amd64 amazoncorretto:17-alpine

RUN apk update \
    apk add curl && \
    rm -rf /var/cache/apk/*

RUN mkdir -p /usr/share/fonts/ipagp
COPY /font/ipagp.ttf /usr/share/fonts/ipagp

RUN apk add --no-cache fontconfig \
  && fc-cache -fv

COPY ./carparking-api.jar /usr/local/share/carparking-api/carparking-api.jar

ENTRYPOINT ["java", \
    "-Xms1024m", \
    "-Xmx1024m", \
    "-Dspring.profiles.active=prod", \
    "-jar","/usr/local/share/carparking-api/carparking-api.jar"]
