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



## SoftDelete by ParentEntity.column issue
### Exception if changing delete to update
Error:  
```text
could not prepare statement [Column "UE1_0.ID" not found; SQL statement:
update tbl_addresses ae1_0 set is_deleted=true where ue1_0.id=? and ae1_0.is_deleted=false [42122-220]] [update tbl_addresses ae1_0 set is_deleted=true where ue1_0.id=? and ae1_0.is_deleted=false]
org.hibernate.exception.SQLGrammarException: could not prepare statement [Column "UE1_0.ID" not found; SQL statement:
update tbl_addresses ae1_0 set is_deleted=true where ue1_0.id=? and ae1_0.is_deleted=false [42122-220]] [update tbl_addresses ae1_0 set is_deleted=true where ue1_0.id=? and ae1_0.is_deleted=false]
	at app//org.hibernate.exception.internal.SQLExceptionTypeDelegate.convert(SQLExceptionTypeDelegate.java:66)
	at app//org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:58)
...
	at app//org.hibernate.query.sqm.internal.QuerySqmImpl.executeUpdate(QuerySqmImpl.java:493)
	at app//org.springframework.data.jpa.repository.support.SimpleJpaRepository.delete(SimpleJpaRepository.java:493)
	at java.base@21.0.6/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
	at java.base@21.0.6/java.lang.reflect.Method.invoke(Method.java:580)
	at app//org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:359)
	at app//org.springframework.data.repository.core.support.RepositoryMethodInvoker$RepositoryFragmentMethodInvoker.lambda$new$0(RepositoryMethodInvoker.java:277)
...
	at app//com.siukatech.poc.spring.jpa.demo.repository.AddressRepositoryTests.test_addressSpec_delete_basic(AddressRepositoryTests.java:294)
	at java.base@21.0.6/java.lang.reflect.Method.invoke(Method.java:580)
	at java.base@21.0.6/java.util.ArrayList.forEach(ArrayList.java:1596)
	at java.base@21.0.6/java.util.ArrayList.forEach(ArrayList.java:1596)
Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Column "UE1_0.ID" not found; SQL statement:
update tbl_addresses ae1_0 set is_deleted=true where ue1_0.id=? and ae1_0.is_deleted=false [42122-220]
	at app//org.h2.message.DbException.getJdbcSQLException(DbException.java:514)
...
	at app//org.h2.jdbc.JdbcConnection.prepareCommand(JdbcConnection.java:1116)
	at app//org.h2.jdbc.JdbcPreparedStatement.<init>(JdbcPreparedStatement.java:92)
	at app//org.h2.jdbc.JdbcConnection.prepareStatement(JdbcConnection.java:288)
	at app//org.hibernate.engine.jdbc.internal.StatementPreparerImpl$1.doPrepare(StatementPreparerImpl.java:96)
	at app//org.hibernate.engine.jdbc.internal.StatementPreparerImpl$StatementPreparationTemplate.prepareStatement(StatementPreparerImpl.java:180)
	... 35 more
...
```

### Add foreign key column xxx_id instead of using ParentEntity.column
Child Entity: AddressEntity  
```java
...
@Entity
@SoftDelete(columnName = "is_deleted")
@Table(name = "tbl_addresses")
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity userEntity;

    // Added for delete with SoftDelete annotation
    @Column(name = "user_id", insertable = false, updatable = false)
    private String userId;

...
```

```java
...
// given
Long userId = 1L;
Long addressId = 1L;
UserEntity userEntity = this.userRepository.findById(userId).orElseThrow();
AddressEntity addressEntity = this.addressRepository.findById(addressId).orElseThrow();
Specification<AddressEntity> addressSpec = ((root, query, cb) -> {
    Predicate predicate = null;
    Path<String> path = null;

    // Original logic for delete with SoftDelete annotation
    // ue1_0 is an unknown alias
    // update tbl_addresses ae1_0 set is_deleted=true 
    // where ue1_0.id=? and ae1_0.is_deleted=false [42122-220]
//    path = root.get("userEntity").get("userId");
//    predicate = cb.equal(path, userEntity.getUserId());

    // Refined logic for delete with SoftDelete annotation
    // ae1_0 is the correct alias
    // update tbl_addresses ae1_0 set is_deleted=true 
    // where ae1_0.user_id=? and ae1_0.is_deleted=false
    path = root.get("userId");
    predicate = cb.equal(path, userEntity.getUserId());

    return predicate;
});

// when
addressEntity.setAddressLine(addressEntity.getAddressLine() + "_updated");
this.addressRepository.delete(addressSpec);

...
```
