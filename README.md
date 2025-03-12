# Spring JPA Demo

## ObjectOptimisticLockingFailureException or StaleObjectStateException
Refer to `UserRepositoryTests`, injecting `TestEntityManager` for detachment of an Entity.   

```java

@Version
private Integer version;

```
