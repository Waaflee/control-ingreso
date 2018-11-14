#!/usr/bin/env bash
psql -h localhost -p 5432 -U ueb -d ctrl-ingreso < purgeTables.sql
psql -h localhost -p 5432 -U ueb -d ctrl-ingreso < TableGen.sql
psql -h localhost -p 5432 -U ueb -d ctrl-ingreso < populateTables.sql