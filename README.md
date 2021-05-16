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

## Sample requests
### register user
```
POST http://localhost:8080/api/user
{
"login": "jkowalski",
"email": "jkowalski@example.pl",
"role": "CLIENT"
}
```
### add group
```
POST http://localhost:8080/api/group/create
{
"name": "testowa",
"isActive": true,
"currencyCode": "EUR"
}
```
### add user to group
```
POST http://localhost:8080/api/group/addUser/{group_id}/{user_id}
```
### add purchase
```
POST http://localhost:8080/api/group/addPurchase/{group_id}/{user_id}
{
"title": "pope souvenirs",
"value": 21.37,
"description": "pope John Paul II suvenirs"
}
```
### calculate
```
GET http://localhost:8080/api/group/calculate/{group_id}
```           