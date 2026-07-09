# REST API in Spring Boot

## what is an API

API stands for Application Programming Interface.

It is a contract that allows two software systems to communicate.

Example:
client sends request:
GET/weather

server returns:
temprature = 30
city = Delhi

## What is REST API

REST API stands for Representational state transfer.
But practically REST APIs mean:

Using HTTP + URLs + JSON to communicate between client and server.

-> Resource based
-> Stateless
-> Uses HTTP methods
-> Returns representation (usually JSON)

## Resources in REST API

Example resources:

- users
- orders
- products

GET/users
GET/users/1
POST/users
DELETE/users/1

users is a collection resource.
users/1 is a specific resource.

## HTTP request structure

Method      Example:
URL         POST/users
Headers     Headers: Content-Type: application-json
Body        Body:
            {
                "name": "Krish"
            }

method tells server what operation to perform.
URL identifies resource.
Headers contain metadata.
Body contains data.

## HTTP response structure

status code         Example
Headers             200 ok
Body                Body:
                    {
                        "id": 1,
                    }

## HTTP Methods

GET -> retrieve data
Example:
GET/users

POST -> create new resource
POST/users

PUT -> replace entire resource
PUT/users/1

PATCH -> update partial resource
PATCH/users/1

DELETE -> remove resource
DELETE/users/1

## URL Parameters

Path Variables:

used to identify specific resource.

Example:
GET/users/5

Here 5 is path variable.

## Query Parameters

Used for filtering and searching.

Example:
GET/users?age=35

Example with pagination:
GET/users?page=1&size=10

## Spring Data JPA

