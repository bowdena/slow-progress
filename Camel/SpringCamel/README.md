spring-boot camel sample route with test case.  Has been rewritten from gradle to maven.

Originally comes from: https://dzone.com/articles/spring-boot-and-apache-camel
(git repository: https://github.com/gkatzioura/egkatzioura.wordpress.com/tree/master/SpringCamel)

before pushing to the repository, make sure that you do a mvn clean.

test with mvn clean install
run with mvn spring-boot:run

run as a docker environment with:
& minikube docker-env | Invoke-Expression
mvn fabric8:run
or
mvn fabric8:deploy (or undeploy)
