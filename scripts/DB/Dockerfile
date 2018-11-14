FROM postgres:11-alpine
ENV POSTGRES_USER ueb   
ENV POSTGRES_PASSWORD pwd
ENV POSTGRES_DB ctrl-ingreso
ADD 1_TableGen.sql /docker-entrypoint-initdb.d/
ADD 2_PopulateTables.sql /docker-entrypoint-initdb.d/
