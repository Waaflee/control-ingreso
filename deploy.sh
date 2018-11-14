#!/usr/bin/env bash
sudo docker-compose down 
mvn package
sudo docker build -t ctrl-ingreso .
sudo docker tag ctrl-ingreso waaflee/ueb-control-ingreso
xdg-open http://localhost:8888/ctrl-ingreso/login.xhtml
sudo docker-compose up
