FROM tomcat:latest

LABEL maintainer="Gabriela"

ADD ./target/mycalcwebapp.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]
