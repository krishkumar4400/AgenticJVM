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

Java App                Database

Every layer in java persistence exists because the previous one had problems.

JDBC -> Hibernate -> JPA -> Spring Data JPA

1. why JDBC existed ?
problem before JDBC

early days:

every database had its own API
oracle API != MySQL API != PostgreSQL API

problem:

- no standard way to talk to database.

Solution:

- JDBC (java database connectivity)
- what jdbc solved:
standard api to connect db
send sql queries
get results

java app -> [jdbc -> sql] -> database

example:
...

### what jdbc did not solve

This is the most important part:

1. no object mapping
User user = new User();
user.setName(rs.getString("name"))
-> Manual mapping every time

2. Biolerplate

- connection handling
- statement handling
- exception handling

1. tight coupling with sql

sql everywhere in code

1. hard to maintain
Schema change -> break queries
Repetitive code

[JDBC solved communication... but not abstraction.]

Solution Hibernate:

1. Why hibernate (ORM) ?

what hibernate solved

1. object <-> table mapping

userRepository.save(user)

- no sql needed

1. Automate sql generation

- hibernate generates:
INSERT INTO users

1. Relationships

@OneToMany

java object -> hibernate -> database

### Problems with Hibernate

1. no standard
hibernate = one implementation

2. vendor lock-in
Session session = sessionFactory.openSession();

-> Hibernate-specific API

1. Multiple ORM tools

- hibernate
- eclipsLink
- openJPA

-> All different

1. No portability

switching ORM = rewrite code

[Hibernate solved mapping... but created fragementation]

### Why JPA ?

problem after hibernate

-> Too many ORMs, no standard

Solution: Java persistence API

What JPA solved

1. standardization
All ORMs follow same rules

2. common API

entityManager.persist(user);

1. portability
Switch ORM without changing code

2. standard anotations
@Entity
@Id
@OneToMany

3. Standard query language
JPQL

java app -> [jap -> hibernate] -> database

- problems with JPA

1. Too much boilerplate
EntityManager em *...
em.persist(user);

2. DAO layer needed
you still write:
UserDao.save(user);

3. complex setup
config
transactions
EntityManager handling

4. Repetitive code
CRUD methods again and again

[JPA standardized ORM... but still required a lot of code.]

Solution: Spring Data JPA

### Why Spring data JPA ?

1. No DAO implementation
public interface UserRepository extends JpaRepository<User, Long> {}

2. Auto CRUD
save()
findAll()
delete()

3. Query generation
findByName(String name)

4. Pagination & Sorting

5. Integration with Spring

- Transactions
- Dependency Injection

JDBC -> Hibernate -> JPA -> Spring Data JPA

### Complete Evolution Summary

Step 1: JDBC

- solved: DB connection
- problem: no abstraction

step 2: hibernate

- solved: object mapping
- problem: no standard

step 3: jpa

- solved: standardization
- problem: boilerplate

step 4: spring data jpa

- solved: boilerplate

[Each layer didn't replace the previous one it fixed the problems of the previous one.]
