FROM --platform=linux/amd64 gradle:jdk17-alpine as build
COPY . ./app
WORKDIR app
RUN ["gradle","clean", "build", "--no-daemon"]


FROM --platform=linux eclipse-temurin:17.0.5_8-jre-ubi9-minimal
EXPOSE 8080
COPY --from=build ./build/distributions/app.tar .
RUN ["tar", "-xf" ,"./app.tar"]
CMD ["java", "-jar", "./app/lib/app.jar"]
#CMD ["/app/bin/app"]
#
# # CMD ["java", "-jar","/app/app.jar"]