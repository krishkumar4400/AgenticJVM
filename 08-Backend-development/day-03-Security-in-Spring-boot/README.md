# Exceptions and security

## what is an Exception ?

runtime problem or an unexpected event that interrupts normal flow of program.

examples:

- user not found
- invalid input

Types of exceptions

1. checked exceptions(compile-time)
must handle or declare

example:

- IOException
- SQLException

1. unchecked exceptions(runtime)
occur at runtime
most common in spring boot

example:

- NullPointerException
- IllegalArgumentException

in backend system, most real problem are runtime exceptions.

### what happens without handling

in a spring boot API:

server throws exception
user gets ugly response like:

{
    "timestamp": "...",
    "status": "500",
    "error": "internal server error",
    "trace": "full stack trace"
}

problems:

- leaks internal details
- bad frontend experience
- no consistency
- no standardization

### Exception in Spring Boot

in spring boot:

- Exceptions flow like this:

controller -> service -> exception throws -> spring caches it -> returns HTTP response.

But by default:

- not clean
- not controlled
- not standardized

### Goal of Exception Handling

1. meaningful error response
2. centralized handling
3. clean controller codes
4. proper http status codes

### Types of Exception handling in Spring Boot

1. Local(try-catch in method)

@GetMapping("/id")
public User getUser(@PathVariable Long id) {
    try{
        return userService.getUser(id);
    } catch (Exception e) {
        throws new RuntimeException("Something went wrong");
    }
}

problems:

- boilderplate
- repeated everywhere

"not scalable"

1. Controller level(@ExceptionHandler)

@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<String> handleUserNotFound(UserNotFoundException ex) {
    return new ResponseEntity<>(ex.getMessage(). HTTPStatus.NOT_FOUND);
}

better but:

- only for one controller

1. Global Exception Handling (Recommended)

step1: create custm exception
step2: throw exception in service
step3: create global handler
