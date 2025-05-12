FROM eclipse-temurin:21-jdk

WORKDIR /build/libs/

COPY /build/libs/harmony-haven-all.jar .

CMD ["java", "-jar", "harmony-haven-all.jar"]
