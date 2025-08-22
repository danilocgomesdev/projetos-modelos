####################################################################################################
# Build
####################################################################################################
FROM registry.fieg.com.br/maven:3.9.6-eclipse-temurin-21 as build
RUN mkdir -p /app/src
WORKDIR /app
ADD . .
ENV MAVEN_CLI_OPTS="--quiet --batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true -Dverify=false -Pgeapp -DskipTests -Dquarkus.package.type=fast-jar -Dquarkus.profile=devspace"
ENV GITLAB_TOKEN_GET_RAW=fm6Yi_unrd9_kBNZ3J1G

RUN curl -ks --header "PRIVATE-TOKEN: $GITLAB_TOKEN_GET_RAW" "https://git.fieg.com.br/api/v4/projects/templates%2Fmaven-settings/repository/files/settings.xml/raw?ref=master" -o /usr/share/maven/conf/settings.xml
RUN --mount=type=cache,target=/root/.m2 mvn -T 1C clean package $MAVEN_CLI_OPTS

####################################################################################################
# Runner
####################################################################################################
FROM registry.fieg.com.br/arquitetura-software/fieg-jdk:jdk-21 as production
USER root

# Configure the JAVA_OPTIONS, you can add -XshowSettings:vm to also display the heap size.
ENV JAVA_OPTIONS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"

COPY --from=build --chown=1001 /app/target/quarkus-app /deployments/
COPY --from=build /app/src/main/resources/application.properties /deployments/config/application.properties

EXPOSE 8080
EXPOSE 5005
USER 1001

ENTRYPOINT [ "/deployments/run-java.sh" ]