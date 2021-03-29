# SQLeckus

 - A simple and easy to use JDBC lib using `kotlinx-serialization` to parse information around.
 - Dataclasses are required to be `@Serializable` from `kotlinx-serialization` for the lib to work. 

## Starting a connection with the database

 - First you will need a `SQLeckusConnection` instance using `val conn = SQLeckusConnection()`
 - Then you will have two options to start a connection to your jdbc
### Passing the whole URL

`conn.startConnection(jdbc:db_type://host:port/db?user=usr&password=pwd)`

### Passing field by field

```
conn.startConnection(
    databaseType = DatabaseType.*,
    usr = "usr",
    pwd = "pwd",
    db = "db",
    port = "port",
    host = "host"
)
```

## Making calls
 - Calls are queries executions that will not return anything to the user.
 - The options available for `Calls` are inside `Call.*`

 ### Creating Schema as example
 
 ```
 conn.createSchema(
    SqlCall.CreateSchema(schema)
 )
 ```

## Making queries
 - Queries are the ones that will return something to the user.
 - Queries starts with a `Query.selection()` and then cascade to what the user wants ending with a `.<reified T>executeQuery()` that will return a list of the type `T` encountered on the database query.
 - ex: `Query.selection(args).innerJoin(args).innerJoin(args).where(args).executeQuery(args)`

# Dependencies

To use this in a project, place the following in your project files:

```
allprojects{
  repositories {
    maven { url 'https://jitpack.io' }
  }
}
```

Add this to the dependencies on the module you want to use it:

```
dependencies{
  implementation 'com.github.WillianKleckus.sqleckus:sql:$current_version'
} 
```
