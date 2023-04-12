# JDBC (Java Database Connectivity)

Here is a table of contents for this document:

- [JDBC (Java Database Connectivity)](#jdbc-java-database-connectivity)
  - [JDBC Architecture](#jdbc-architecture)
  - [Concepts of JDBC](#concepts-of-jdbc)
    - [Statement](#statement)
      - [Statement](#statement-1)
      - [PreparedStatement](#preparedstatement)
      - [CallableStatement](#callablestatement)
    - [Query](#query)
      - [DDL](#ddl)
      - [DML](#dml)
      - [DCL](#dcl)
      - [TCL](#tcl)
      - [DRL](#drl)
    - [Execution](#execution)
      - [execute](#execute)
      - [executeQuery](#executequery)
      - [executeUpdate](#executeupdate)
    - [ResultSet](#resultset)
      - [ResultSetMetaData](#resultsetmetadata)
      - [ResultSet](#resultset-1)
  - [DriverManager](#drivermanager)
  - [Connection](#connection)
  - [Driver](#driver)
  - [DataSource](#datasource)
  - [Transaction](#transaction)
  - [SQL Injection](#sql-injection)
  - [References](#references)

## JDBC Architecture

- JDBC is a Java API that provides a set of classes and interfaces to perform database operations.

- JDBC is a part of the Java SE (Standard Edition) platform.

Concepts of JDBC

- Statement
  1. Statement
  2. PreparedStatement
  3. CallableStatement
- Query
  1. DDL
  2. DML
  3. DCL
  4. TCL
  5. DRL
- Execution
  1. execute
  2. executeQuery
  3. executeUpdate
- ResultSet
  1. ResultSetMetaData
  2. ResultSet

## Statement

- A Statement is an `interface` which is used to execute SQL queries. It is used to execute static SQL queries.

- The SQL query to be executed is passed as a string parameter to the execute(), executeQuery() or executeUpdate() method of the Statement interface.

- Statement is available in java.sql package.

- There are three types of statements in JDBC:
  1. Statement
  2. PreparedStatement
  3. CallableStatement

### Statement

- It is the most basic type Statement interface.

- Used to execute queries that are static and do not have any parameters.

- Statement objects can be vulnerable to SQL injection attacks because the query string is directly concatenated with user input.

```java
String query = "SELECT * FROM users WHERE username = '" + username + "'";
Statement stmt = conn.createStatement();
ResultSet rs = stmt.executeQuery(query);
```

In this example, we use a Statement to execute a SQL query against the "users" table, searching for a specific user by their username. However, note that this code is vulnerable to SQL injection attacks since the username variable is concatenated directly into the SQL query.

### PreparedStatement

- Main features of PreparedStatement are:
  1. It is precompiled and stored in a PreparedStatement object.
  2. It is a parameterized query.
  3. It is more efficient than Statement.
  4. It is less vulnerable to SQL injection attacks.

- It is used to execute queries that are dynamic and have parameters.

- It extends Statement interface.

- A PreparedStatement object is used when a SQL statement needs to be executed multiple times with different parameter values, making it more efficient than Statement.

- PreparedStatements are also less vulnerable to SQL injection attacks because they use placeholders for parameters which are later replaced with user input, preventing malicious code injection.

- `?` is used as a placeholder for parameters.


```java

PreparedStatement pstmt = con.prepareStatement("INSERT INTO EMPLOYEE VALUES(?, ?, ?)");
pstmt.setInt(1, 100);
pstmt.setString(2, "John");
pstmt.setString(3, "Doe");

pstmt.executeUpdate();

```

```java
String query = "SELECT * FROM users WHERE username = ?";
PreparedStatement pstmt = conn.prepareStatement(query);
pstmt.setString(1, username);
ResultSet rs = pstmt.executeQuery();
```

In this example, we use a PreparedStatement to execute the same SQL query as before, but with a parameterized query. The "?" acts as a placeholder for the username parameter, which is later set using the setString() method. This code is safer and more efficient since it prevents SQL injection attacks and can be reused with different parameters.

### CallableStatement 

- Main features of CallableStatement are:
  1. It is used to execute stored procedures.
  2. It is used to execute functions.
  3. It is used to execute database-specific operations.

- Used to execute stored procedures that might contain both input and output parameters.

- It extends PreparedStatement interface.

- CallableStatements can also be used to execute functions and other database-specific operations.

- Like PreparedStatement, CallableStatement uses placeholders for parameters and is less vulnerable to SQL injection attacks.


example of stored procedure in SQL:

```sql
CREATE PROCEDURE getEmpName (IN empid INT, OUT ename VARCHAR(20))
BEGIN
SELECT name INTO ename FROM employee WHERE id = empid;
END
```

example of function in SQL:

```sql
CREATE FUNCTION getEmpName (empid INT) RETURNS VARCHAR(20)
BEGIN
DECLARE ename VARCHAR(20);
SELECT name INTO ename FROM employee WHERE id = empid;
RETURN ename;
END
```





```java

CallableStatement cstmt = con.prepareCall("{call getEmpName (?, ?)}");
cstmt.setInt(1, 110);
cstmt.registerOutParameter(2, java.sql.Types.VARCHAR);
cstmt.execute();
String ename = cstmt.getString(2);
System.out.println(ename);

```

```java
String query = "{call get_user(?, ?)}";
CallableStatement cstmt = conn.prepareCall(query);
cstmt.setString(1, username);
cstmt.registerOutParameter(2, Types.VARCHAR);
cstmt.execute();
String password = cstmt.getString(2);
```

In this example, we use a CallableStatement to call a stored procedure named "get_user" that takes a username parameter and returns the corresponding user's email address. We set the username parameter using the setString() method and register an output parameter for the email using the registerOutParameter() method. After executing the stored procedure, we retrieve the email value using the getString() method on the CallableStatement object.

## Query

- A query is a request for data or information from a database table or combination of tables.

- A query can be classified into four types:
  1. DDL (Data Definition Language)
     - CREATE
       - CREATE TABLE
       - CREATE VIEW
       - CREATE INDEX
       - CREATE DATABASE
     - ALTER
     - DROP
     - TRUNCATE
  2. DML (Data Manipulation Language)
     - INSERT
     - UPDATE
     - DELETE
  3. DCL (Data Control Language)
     - GRANT
     - REVOKE
  4. TCL (Transaction Control Language)
     - COMMIT
     - ROLLBACK
     - SAVEPOINT
  5. DRL (Data Retrieval Language)
     - SELECT

### DDL

- DDL is used to define the database schema.

- It includes the SQL commands such as `CREATE`, `ALTER`, `DROP`, `TRUNCATE` etc.

- DDL statements are used to create and modify the structure of database objects in the database.

- DDL statements are used to create and drop the tables in the database.

- DDL statements are used to create and drop various views in the database.

examples

CREATE TABLE

```sql
CREATE TABLE EMPLOYEE (
    ID INT NOT NULL,
    NAME VARCHAR(20) NOT NULL,
    SALARY DECIMAL(18, 2),
    PRIMARY KEY (ID)
);
```

ALTER TABLE

```sql
ALTER TABLE EMPLOYEE
ADD COLUMN ADDRESS VARCHAR(255);
```

DROP TABLE

```sql
DROP TABLE EMPLOYEE;
```

TRUNCATE TABLE

```sql
TRUNCATE TABLE EMPLOYEE;
```

### DML

- DML is used to manipulate the data present in the database.

- It includes the SQL commands such as `INSERT`, `UPDATE`, `DELETE`, `MERGE`, `CALL`, `EXPLAIN PLAN`, `LOCK TABLE` etc.

- DML statements are used to insert records in a table.

- DML statements are used to update records in a table.

examples

INSERT

```sql
INSERT INTO EMPLOYEE (ID, NAME, SALARY, ADDRESS)
VALUES (1, 'John', 1000.00, 'New York');
```

UPDATE

```sql
UPDATE EMPLOYEE
SET SALARY = 2000.00
WHERE ID = 1;
```

DELETE

```sql
DELETE FROM EMPLOYEE
WHERE ID = 1;
```

MERGE

```sql
MERGE INTO EMPLOYEE
USING DUAL
ON (ID = 1)
WHEN MATCHED THEN
    UPDATE SET SALARY = 2000.00
WHEN NOT MATCHED THEN
    INSERT (ID, NAME, SALARY, ADDRESS)
    VALUES (1, 'John', 1000.00, 'New York');
```

CALL

```sql
CALL GET_EMPLOYEE_NAME(1);
```

EXPLAIN PLAN

```sql
EXPLAIN PLAN FOR
SELECT * FROM EMPLOYEE;
```

LOCK TABLE

```sql
LOCK TABLE EMPLOYEE IN EXCLUSIVE MODE;
```

### DCL

- DCL is used to define the rights, permissions and other controls on the data present in the database.

- It includes the SQL commands such as `GRANT`, `REVOKE` etc.

- DCL statements are used to provide permissions to the users for various operations on the tables, views, and other database objects.

- DCL statements are used to revoke the permissions given to the users.

examples

GRANT

```sql
GRANT SELECT, INSERT, UPDATE, DELETE
ON EMPLOYEE
TO 'user1';
```

REVOKE

```sql
REVOKE SELECT, INSERT, UPDATE, DELETE
ON EMPLOYEE
FROM 'user1';
```

### TCL

- TCL is used to control the transaction in the database.

- It includes the SQL commands such as `COMMIT`, `ROLLBACK`, `SAVEPOINT`, `SET TRANSACTION` etc.

- TCL statements are used to manage transactions.

- TCL statements are used to set the transaction properties.

examples

COMMIT

```sql
COMMIT;
```

ROLLBACK

```sql
ROLLBACK;
```

SAVEPOINT

```sql
SAVEPOINT SAVEPOINT_1;
```

SET TRANSACTION

```sql
SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
```

### DRL

- DRL is used to retrieve the data from the database.

- It includes the SQL commands such as `SELECT` etc.

- DRL statements are used to retrieve data from one or more tables.

examples

SELECT

```sql
SELECT * FROM EMPLOYEE;
```

## Executing Queries

To execute a query, call an execute method from Statement such as the following:

1. execute
2. executeQuery
3. executeUpdate

### execute

- Returns true if the first object that the query returns is a ResultSet object.
- Use this method if the query could return one or more ResultSet objects.
- Retrieve the ResultSet objects returned from the query by repeatedly calling Statement.getResultSet.

example:

```java
boolean hasResultSet = stmt.execute("SELECT * FROM EMPLOYEES");
if (hasResultSet) {
    ResultSet rs = stmt.getResultSet();
    while (rs.next()) {
        // process the result set
    }
}
```

### executeQuery

Returns one ResultSet object.

example:

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
while (rs.next()) {
    // process the result set
}

```

### executeUpdate

- Returns an integer representing the number of rows affected by the SQL statement.

- Use this method if you are using `INSERT`, `DELETE`, or `UPDATE` SQL statements.

example:

```java
int rowCount = stmt.executeUpdate("INSERT INTO EMPLOYEES VALUES (100, 'John', 'Doe')");
```

source : https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html

## ResultSet

- A ResultSet can have different functionalities and characteristics depending on the type of SQL statement that was used to create it.

- The characteristics are type, concurrency, and holdability.

- The type of a ResultSet object determines whether it is scrollable or not.

- The concurrency of a ResultSet object determines whether it is updatable or not.

- The holdability of a ResultSet object determines whether it is closed when the Statement object that generated it is closed.




- A ResultSet object maintains a cursor that points to its current row of data. 

- Initially the cursor is positioned before the first row.

- The next method moves the cursor to the next row, and because it returns false when there are no more rows in the ResultSet object, it can be used in a while loop to iterate through the result set.

- To retrieve the data you can either use the column index number or the column name.

example of retrieving data using column index number:

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
while (rs.next()) {
    int id = rs.getInt(1);
    String firstName = rs.getString(2);
    String lastName = rs.getString(3);
}
```

example of retrieving data using column name:

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
while (rs.next()) {
    int id = rs.getInt("id");
    String firstName = rs.getString("first_name");
    String lastName = rs.getString("last_name");
}
```



- To retrieve the data from the ResultSet object, you use the getXXX methods, where XXX is the data type of the column.


- The getXXX methods retrieve column values from the current row. You can retrieve values either using the index number of the column or the name of the column. In general using the column index will be more efficient. Columns are numbered from 1.

- The getXXX methods throw a SQLException if the column type is an SQL NULL. So you should use try-catch block to handle the exception.


### ResultSet Types

- ResultSet.TYPE_FORWARD_ONLY: This type of ResultSet object can only be scrolled in the forward direction.



example:

```java
ResultSet rs = stmt.executeQuery("SELECT * FROM EMPLOYEES");
while (rs.next()) {
    int id = rs.getInt("id");
    String firstName = rs.getString("first_name");
    String lastName = rs.getString("last_name");
}
```

For more information check [here](https://docs.oracle.com/javase/tutorial/jdbc/basics/retrieving.html).

## Commit and Rollback

- A transaction is a logical unit of work that contains one or more SQL statements.


## Trigger in SQL

- A trigger is a special type of stored procedure that is automatically executed when a specified event occurs in the database.

- A trigger is a database object that is associated with a table and is automatically executed when a specified event occurs.

Some examples of events that can trigger a trigger are:

- INSERT
- UPDATE
- DELETE

Some uses of triggers are:

- To enforce referential integrity
- To enforce business rules
- To audit changes to the database

### Creating a Trigger

```sql
CREATE TRIGGER trigger_name
BEFORE INSERT ON table_name
FOR EACH ROW
BEGIN
    -- trigger body
END;
```

### Dropping a Trigger

```sql
DROP TRIGGER trigger_name;
```

### Trigger Types

- BEFORE INSERT
- AFTER INSERT
- BEFORE UPDATE
- AFTER UPDATE
- BEFORE DELETE
- AFTER DELETE

### Trigger Events

- INSERT
- UPDATE
- DELETE

### Trigger Timing

- BEFORE
- AFTER

### Trigger Scope

- FOR EACH ROW
- FOR EACH STATEMENT

### Trigger Variables

- NEW
- OLD

### Trigger Body

- The trigger body is the code that is executed when the trigger is fired.

- The trigger body can contain any valid SQL statement.

- The trigger body can contain SQL statements that modify the data in the table that the trigger is associated with.

Triger example:

```sql
CREATE TRIGGER trigger_name
BEFORE INSERT ON table_name
FOR EACH ROW
BEGIN
    -- trigger body
END;
```

Example of trigger with JDBC:

```java
String trigger = "CREATE TRIGGER trigger_name " +
                 "BEFORE INSERT ON table_name " +
                 "FOR EACH ROW " +
                 "BEGIN " +
                 "   -- trigger body " +
                 "END;";
stmt.executeUpdate(trigger);
```



