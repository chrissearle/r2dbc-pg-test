FROM eclipse-temurin:17

RUN mkdir -p /opt/app
COPY target/test-pg-r2dbc.jar /opt/app/app.jar
CMD ["java", "-jar", "/opt/app/app.jar"]