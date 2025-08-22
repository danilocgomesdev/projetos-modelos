CI_PROJECT_DIR=/app/.cache
YARN_OPTS=" --cache-folder ${CI_PROJECT_DIR}/.yarn  --network-concurrency 1 --network-timeout=30000 --no-progress --non-interactive --prefer-offline"
MAVEN_IMAGE=registry.fieg.com.br/maven/3.8.4-openjdk-17


MAVEN_OPTS=-DskipTests -Pgeapp -Dquarkus.package.type=uber-jar -Dquarkus.profile=dev  \
	-Dmaven.repo.local=/app/.cache/.m2/repository  \
	-Dhttps.protocols=TLSv1.2 \
	-Dorg.slf4j.simpleLogger.log.org.apache.maven.cli.transfer.Slf4jMavenTransferListener=WARN \
	-Dorg.slf4j.simpleLogger.showDateTime=true \
	-Djava.awt.headless=true \
	--batch-mode --errors --fail-at-end --show-version -DinstallAtEnd=true -DdeployAtEnd=true 


TAG_BASE=registry.fieg.com.br/area-meio/financeiro/cr5/cr5-jobs

MAX_RAM=768m
JAVA_OPTIONS=-Dglowroot.agent.id=financeiro-staging::cr5-jobs-localhost \
	-Dglowroot.collector.address=http://PRDLNXAPL013.fieg.com.br:8181 \
	-javaagent:/app/glowroot/glowroot.jar \
	-XX:MaxRAM=${MAX_RAM} \
	-Djava.net.preferIPv4Stack=true -Djava.awt.headless=true \
	-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager \
	-Dquarkus.profile=dev



KUSTOMIZE=kustomize build ./geapp/src/main/kustomize/overlays

.PHONY: eletron.build frontend.fuild java.build docker.buildf docker.buildb docker.runb  kustomize.prod kustomize.staging kustomize.validate

date:
	date; 

java.build:
	docker run \
	--user $$(id -u):$$(id -g) \
	-v ${PWD}:/app \
	-v ~/.m2/settings.xml:/usr/share/maven/conf/settings.xml:ro \
	-w /app \
	-it ${MAVEN_IMAGE} \
	sh -c "mvn clean package $$(echo ${MAVEN_OPTS}) "


java.exec:
	java -jar backend/target/cr5-jobs-runner.jar -executaConciliacao


docker.buildb: 
	docker build --tag ${TAG_BASE} . -f ./geapp/src/main/docker/Dockerfile.uber-jar

docker.runb: date
	docker run \
	-e JAVA_OPTIONS="${JAVA_OPTIONS}" \
	-e TZ="America/Bahia" \
	-v ${PWD}/src/main/resources/application.properties:/deployments/config/application.properties \
	-it ${TAG_BASE} \
	-executaConciliacao


kustomize.prod: date
	$(KUSTOMIZE)/production

kustomize.staging:
	$(KUSTOMIZE)/staging

kustomize.validate: date
	@printf "\nValidando producao\n"; \
	$(KUSTOMIZE)/production | kubectl apply --dry-run --validate  -f  - ; \
	printf "\n\nValidando staging\n"; \
	$(KUSTOMIZE)/staging | kubectl apply --dry-run --validate  -f  - ;