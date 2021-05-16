# Share the Bill
Web application supporting managing shared expenses.

## Postgres docker instance initialization
```
docker pull postgres:alpine
docker run --name postgres-0 -e POSTGRES_PASSWORD=admin -d -p 5433:5432 postgres:alpine  
docker exec -it postgres-0 bash

psql -U postgres
CREATE DATABASE sharethebill;
CREATE USER sharethebill_admin WITH PASSWORD 'admin';
GRANT ALL PRIVILEGES ON DATABASE sharethebill TO sharethebill_admin;
```