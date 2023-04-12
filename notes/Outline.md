- Setup a database

  - Virtual machine
  - Docker
  - Cluster
    - Swarm
    - Kubernetes

- Introduction- How to use a database

  - Query against a database- [R]DBMS
  - JDBC- Java API
  - JPA- ORM management

- Connection to a database

  - Driver machine
  - Connection Pooling
    - Apache Commons DBCP

- JDBC

  - Initialization
  - Create a connection instance
  - Statement
    - Base Statement
    - Prepared Statement
    - Callable Statement
  - Execution against a DBMS
    - execute
    - executeQuery
    - executeUpdate
  - ResultSet

- CRUD

- Query

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

- DB Design Pattern

  - Data Access Object (DAO) and Data Transfer Object (DTO)

- Normalization

  - 1NF
  - 2NF
  - 3NF, Boyce-Codd (BCNF)
  - 4NF
  - 5NF

- Handle exceptions

  - Bad way
  - Good way

- Transaction

  - ACID
  - Isolation
  - Locking
  - Deadlock

- Function and Stored procedures

- Triggers

  - Using functions and stored procedures in triggers

- Commit

  - Auto-commit
  - Commit and Rollback
  - Transaction savepoints

- Security
