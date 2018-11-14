FROM tomcat:8-alpine
COPY ./target/UEB-control-ingreso-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ctrl-ingreso.war
