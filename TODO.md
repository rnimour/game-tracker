add features:
 - [x] @ControllerAdvice
 - [x] @WebMvcTest
 - [x] @DataJPATest
 - [x] use Zalando Problem for error handling (more useful error messages than HTTP 400 bad request)
     see https://github.com/zalando/problem-spring-web?tab=readme-ov-file
 - [x] Testcontainers
 - [x] extra entities, e.g. players, high scores, etc.
   - [x] add player entity
 - [x] throw error on existing game (game with same name)
 - [ ] Use Spring Modulith, which enforces correct package structure with ArchUnit
     see https://spring.io/projects/spring-modulith#overview, https://www.archunit.org/
   - [ ] add the rule "no repository should be accessed from a controller"
 - [x] whenever instead of `when`
 - [ ] add pagination/prevent loading too many entities
 - [ ] merge exception handling for player and game?
 - [ ] add generic api

