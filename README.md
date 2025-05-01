# Spring JPA Demo

## ObjectOptimisticLockingFailureException or StaleObjectStateException
Refer to `UserRepositoryTests`, injecting `TestEntityManager` for detachment of an Entity.   

```java

@Version
private Integer version;

```


## List to Map<K, List<V>>
**Reference:**  
https://www.baeldung.com/java-groupingby-collector  
https://blog.csdn.net/winterking3/article/details/116457573  

Use List.stream().collect(Collectors.toMap(key-function, value-function)) to convert List<T> => Map<K, V)  
Use List.stream().collect(Collectors.groupingBy(key-function)) to convert List<T> => Map<K, List\<V>)  

```java
Map<UserEntity, List<AddressEntity>> userEntityAddressEntityListMap1 = 
        addressEntityList3.stream()
        .collect(Collectors.groupingBy(AddressEntity::getUserEntity));
```



