FROM openjdk:17-jdk-slim
LABEL maintainer="srikanth.naidu65@gmail.com"
COPY target/payconiq-stock-1.0.0-RELEASE.jar payconiq-stock.jar
ENTRYPOINT ["java", "-jar", "/payconiq-stock.jar"]