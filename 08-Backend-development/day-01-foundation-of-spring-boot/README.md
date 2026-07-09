# Spring Boot

> Spring boot is an extension of spring framework that makes building backend applications:

- Faster
- Easier
- Less configuration-heavy
- Production heavy

In simple words:

- Spring Boot lets you create a complete backend application with Spring in minutes instead of hours.

Spring Boot is Not a new framework replacing Spring

-> It uses spring internally

## why backend frameworks exists (life before spring)

Before frameworks like spring, backend development in java was mainly done using:

- servlets
- jsp(java server pages)
- jdbc for database
- application servers like tomcat, weblogic

Typical flow:

Browser -> servlet -> JDBC -> database

sounds simple - but in reality it was messy.

- Problem in early backend development:

1. A lot of boilerplate code

Example in JDBC:

- open connection
- create statement
- execute query
- handle result set
- close connection
- handle exceptions

1. Tight coupling (hard dependency)

example:

MySQLDatabase db = new MySQLDatabase();

if tomorrow you want postgreSQL -> you change code everywhere.

this makes:

- code hard to maintain
- hard to test
- hard to scale

1. No proper structure

each developer wrote code differently:

- No standard architecture
- No dependency management
- No lifecycle management

1. Manual configuration hell

Everything was configured manually in:

- XML files
- web.xml
- server configs

-> small mistake = server won't start

## conclusion

Backend without frameworks was:

- slow to develop
- hard to develop
- error prone
- not scalable

That's why backen frameworks were needed.

## Problems with servlets, JDBC, and XML configuration

servlets problem:

1. Too low level
you had to manage:

- Request parsing
- Response writing
- Thread handling
- session management

Everything manually.

1. No separation of concerns

Business logic + request mixed together.

Result: spaghett code

JDBC problems

1. Bilerplate code
2. resource leaks
If you forget to clode connection -> memory leak.
3. Hard to switch databases.

XML configuration problems

spring early days used heavy XML.

example:
<bean id="userService" class="com.app.UserService">

Large projects had:
1000+ lines of XML.

Problems:

- hard to read
- hard to debug
- not developer friendly

## Spring

spring was created to fix these exact problems.
core idea of spring
-> Make java backend simple, clean, and loosely coupled.

spring introduced:

1. Dependency Injection (DI)

instead of:
new MySQLDatabase()

spring gives you object automatically.

This means:

- easy to change implementations
- easy testing
- loose cupling

1. Inversion of control (IoC)

spring manages:

- object creation
- object lifecycle
- dependency wiring

you focus only on business logic.

1. Spring JDBC & ORM support

It simplified DB work:

- less bilerplate
- better exeception handling
- integration with hibernate / JPA

1. MVC architecture

Spring MVC separated:

- controller
- services
- repository

clean architecture

## why spring become popular

- solved biolerplate problem
- loose coupling
- clean architecture
- huge ecosystem
- enterprise - ready

## What problems Spring had

spring was powerful - but not easy

problem 1: Too much configuration
old spring projects required

- xml files
- bean definitions
- web configs
- dispatcher servlet setup

starting a project took Too much files

problem 2: you had to

- download jars manually (early days)
- configure server
- setup web.xml
- configure spring context

for beginners -> nightmare.

problem 3: version compatibility

one wrong dependency version and:

- app won't start

problem 4: Not developer friendly for fast development
in startups & microservices world:

people wanted:

- quick setup
- less config
- production ready defaults

spring was heavy.

## why spring boot was created

spring boot was created to:

- make spring easy
- remove configuration pain
- enable rapid development

Main goal of Spring Boot:

- zero(or minimal) configuration
- auto setup everything
- embedded servers
- optionated defaults
- production ready features

key innovations of Spring Boot:

1. Auto Configuration

spring boot automatically configures:

- database
- web server
- security
- JSON handling

based on:
Dependency you add

Example:
Add spring-boot-starter-web -> Spring boot auto sets:

- tomcat
- spring mvc
- jackson JSON

No XML.

1. Starters(dependency bundles)

Instead of 10 libraries:
spring-boot-starter-web

It includes everything needed.

1. Embedded server
No need external Tomcat.

Just run:
java -jar app.jar

Boom server running.

Perfect for:

- Microservices
- cloud
- Docker

1. production ready features

spring boot actuator gives:

- health checks
- metrics
- monitoring
- logs

Important for modern systems.

-> Spring fixed backend complexity.
-> Spring boot fixed spring's complexity.

## Spring Boot

>> Spring Boot is one of the most widely used backend frameworks in enterprice and cloud - native applications.

If someone learns:
 -> java + spring boot

 They are highly employable in:

- product companies
- service companies
- startups

## Spring Core Concepts

- Lets try to achive loose coupling

Tightly coupled systems(problem)

Lets say we have:

-> A service that saves user data
-> it directly uses MySQL database

Problems:

- UserService depends directly on MySQLDatabase
- If tomorrow we switch to postgresql -> code change
- Hard to test (can't mock easily)
- Huge systems become unmanageable

we want:

UserService should not care:

- which database
- how it's created

It should just say:

- I need something that can save data

so we introduce on INTERFACE (best practice)

Now we achived:

- Loose coupling
- Easy change
- Easy testing

But...

- still a problem
- who will create objects ?
- somewhere you still need:

Database db = new MySQLDatabase();
UserService service = new UserService(db);

In big projects:

- hundreds of objects
- thousands of dependency

This object creation code becomes a mess

This is where spring core enters

spring says:

"Give me responsibility of creating and connecting objects."

This leads to:

[Inversion of control(IoC)]

Before:
Your code controls object creation

After:
Spring controls object creation

-> so control is inverted

Instead of:
new MySQLDatabase()

You say:

"Spring you create it"

[IoC container (the brain)]

spring has a container called:
-> ApplicationContext

It:

- Creates Objects
- Stores them
- Injects them
- Manages lifecycle

Think of it like:
-> A big box of ready - made objects

[Beans]
objects managed by spring

Example:

@Component
class DBService {}

Spring will:

- create DBService object
- Store it in container
- Manage it

now DBService is a Bean

Not every object is bean.
Only those creted by spring are beans.

## How spring knows what to create (Annotations)

Spring scans your project and looks for special annotations.

Main ones:

@component
Generic bean

@component
class DBService {}

means:
-> "spring, create this object"

@Service
for business logic layer

@Service
class UserService {}

@Repository
for database layer

@Repository
For database layer

class UserRepository {}

-> Are they different internally ?

- no (almost same)
- but: they add semantic meaning:

Annotation              Purpose

@Component              General
@Service                Business logic
@Repository             DB layer (extra exception handling)

Modern best practice:

-> Use proper one for clarity

## Dependency Injection(DI)

Spring automatically provides required object to a class.

You don't do:

new MySQLDatabase()

Spring does:
-> inject it

This achives:

- loose coupling
- clean code
- easy testing

why constructor injection is best(modern)

Because:
forces required dependency
no null values
Immutable
Works best with testing frameworks

That's why Spring Boot encourages it.

---

---

@Repository
class PostgreSQLDatabase implements Database{}

Spring gets confused

Option 1 - @Primary

Example:

@Primary
@Repository
class MySQLDatabase implements Database{}

Now Spring says:

-> "Use this one by default"

When to use @Primary:

when one implementation is main/default
Others are rarely used
Simple projects

-> Downside:

- Hardcoded in code
- cannot change per environment easily

another option is @Qualifier but not recommended

Option 2 - Spring profiles

Example:
@Profile("dev")
@Repository
class MySQLDatabase implements Database{}

@Profile("prod")
@Repository
class PostgreSQLDatabase implements Database{}

Activate profile:
spring.profiles.active=dev

(or environment variable in cloud)

Benefits:

- clean separation per environment
- no if-else logic
- very scalable

Option 3 - @ConditionalOnProperty (BEST)
it means:

"create this bean only if a config property has a specific value"

so spring decides implementation based on:

- application.properties
- application.yml
- environment variables

(no code change)

[application.properties]
[db.type = mysql]

-> MySQL implementation

@Repository
@ConditionalOnProperty(name="db.type", havingValue="mysql")
class MySQLDatabase implements Database {}

-> PostgreSQL implementation
@Repository
@ConditionalOnProperty(name="db.type", havingValue="postgres")
class PostgreSQLDatabase implements Database {}

Spring will automatically:

- create only the matching bean
- inject it

no conflict, no ambiguity

```text
@Profile is mainly used for environment based configuration, while @ConditionalOnProperty provides fine-grained, property-driven bean creation. In modern Spring Boot applications, 

Conditional configuration is preferred for feature toggles and provider selection, while profiles are used for environment separation.
```

## Bean scope

By default:

-> One object per bean (singleton)

meaning:

UserService everywhere = same object
Database everywhere = same object

why ?

- memory efficient
- fast
- thread safe usually

Other scopes (rare but useful):

prototype -> new everytime
request -> per HTTP request
session -> per user session

## Bean lifecycle

spring doesn't just create object instantly.

Flow

- create object
- inject dependency
- call init method
- bean is ready
- on shutdown -> destroy

Hooks:

@PostConstruct
public void start() {
    System.out.println("Bean ready");
}

@PreDestroy
public void stop() {
    System.out.println("cleaning up");
}

used for:

- opening resource
- closing connections

## Configuration (why & how)

we don't hardcode values like:

DB url
parts
credentials

Instead use config files.

application.properties:

db.type=mysql
server.port=8080

inject:
@value("${db.type}")
String dbType;

Better modern way:
@CinfigurationProperties(prefix="db")
class DBConfig{
    private String type:
}

much cleaner

[Java config beans]
sometimes we need custom objects.

@Configuration
class AppConfig {

@Bean
    public Database.database() {
        return new MySQLDatabae();
    }
}

now Spring manages it as bean.

## Spring Boot Internals: SpringApplication.run() + Auto Configuration

In spring core we learned about:

- IoC container
- Dependency Injection
- Bean Lifecycle
- Component Scanning

But in Spring Boot we usually just write: SpringApplication.run(Application.class, args);

And magically:

- the application starts
- the web server starts
- REST APIs start working
- configuration happens automatically

So the important question is:

- what actually happens when this line executes ?
- specifically we will understand two major things:
-> what happens inside SpringApplication.run()
-> How Auto Configuration works internally

## High level startup pipeline

main() -> SpringApplication.run() -> [
    1. create SpringApplication
    2. prepare environment
    3. create Application context
    4. select auto configurations
    5. context.refresh()
] -> Application ready

- How Spring Boot works - How each phase of spring boot works:

## Final recap

main() -> SpringApplication.run() -> Create Spring Application -> Prepare Environment -> Create ApplicationContext -> Load Autoconfiguration.imports -> Apply conditional filtering -> register configurations -> contaxt.refresh() -> Application Ready

Spring boot startup is not magic.

It is a deterministic pipeline consisting of:

- environment preparation
- context creation
- conditional configuration selection
- container initialization

understanding this pipeline makes the framework much easier to debug and customize.

## Spring MVC Request Flow

Till now we saw how spring boot starts: SpringApplication.run()

That process:

- prepares the environment
- creates ApplicationContext
- loads auto configuration
- starts the application

Once Boot finishes startup, the application is ready to handle requests.

So the next question is:

what happens when a client sends an http request to our application ?
To answer this we must understand Spring MVC architecture.

## High level Request flow

client(browser/postman) -> web server(tomcat) -> DispatcherServlet -> HandlerMapping -> Controller -> Service -> Repository -> Database.

What spring MVC actually is:
It is the web framework inside spring that implements the model-view-controller pattern
Its responsibility is to handle http request and responses.
Spring MVC infrastructure includes components like:

- DispatcherServlet(front controller)
- HandlerMapping
- HandlerAdopter
- Controller
- ViewResolver
- HttpMessageConverters

So Spring MVC is basically the web request processing engine.

It is based on the front controller design pattern.

Front controller means:
All http requests first go to a central component before reaching controllers.

In spring MVC, that central component is called:

DispatcherServlet

- A typical spring boot web application follows three layered architecture:

1. controller layer
2. service layer
3. repository layer

Each layer has a specific responsibility.

Controller -> handles http requests
service -> contains business logic
repository -> interacts with database

However, before reaching the controller, requests must pass through the spring MVC infrastructure.

Web server receives request
Spring Boot uses an embedded server.

By default Apache Tomcat

when Boot starts, it automatically starts an embedded Tomcat server. Tomcat listens on a port (default 8080)

when a client sends a request:

GET/users

Tomcat receives it.
But Tomcat does not knwo about controllers.

Instead it forwards the request to a special servlet called:
"DispatcherServlet".

## DispatcherServlet(The heart of spring mvc)

DispatcherServlet is the front controller in spring mvc.
front controller means:
All requests first go to a single central component.

DispatcherServlet coordinates the entire request lifecycle.
It does not handle business logic.
Instead it delegates tasks to other components.

## Handler Mapping

when DispatcherServlet receives a request, the first question is:
which controller method should handle this request?

To answer this spring mvc uses hadnle mapping.

Handlemapping maps a request URL to a controller method.

Example:
GET/users

Mapped to:
UserController.getUser()

Spring mvc supports several handler mappings, but the most commonly used one is:
"RequestMappingHandlerMapping"

This mapping reads annotations like:

@GetMapping
@PostMapping
@RequestMapping

## Handler Adapter

once spring identifies the correct controller method, it must execute it.
However, controllers can have different method signatures.

Example:
@GetMapping("/users")
public List<User> getUser()

Another controller might look like:

@GetMapping("/users/{id}")
public user gerUser(@PathVariable Long id)

Spring needs a way to invoke these methods generally.
This is done by Handleradopter.

HandlerAdopter knows how to call controller mathods.
"RequestMappingHandlerAdopter"

## Controller Execution

Now the controller method executes.

Example:

@GetMapping("/users")
public List<User> getUser() {
    return userService.getUser();
}

controller should not contain business logic.
Instead it delegates to the service layer.

## Service layer

Service layer contains business logic.

Example:
UserService

Responsibilities include:

validations
business rules
orchestration of operations

Example:
public List<User> getUser() {
    return userRepository.findAll()
}

## Repository layer

Repository layer interacts with the database.

spring boot commonly uses:
Spring Data JPA

Repository perform:
CRUD operations
database queries

Example:
UserRepository extends JpaRepository

## Returning the Response

After controller returns data, spring MVC processes the response.

Example return:

List<User>

Spring must convert this java object into JSON.
This handled by HttpMessageConverters.

The default converter for JSON is provided by:Jackson

Jackson converts Java objects into JSON automatically.

java object -> HttpMessageConverter -> JSON

Response Flow:
controller -> HandlerAdopter -> Dispatcher -> Tomcat -> Client

## Complete Request Lifecycle

```text
Client -> Tomcat -> DispatcherServlet -> HandlerMapping -> HandlerAdopter -> Controller -> Service -> Repository -> Database -> Controller Response -> HttpMessageConverter -> JSON response -> client.
```

## Where Auto Configuration Fits

we saw:
Auto configuration loads:
WebMVCAutoConfiguration

This automatically configures:

DispatcherServlet
HandlerMapping
HandlerAdapter
MessageConverters

So developers only write:

@Controller
@GetMapping

Spring Boot configures the infrastructure.
