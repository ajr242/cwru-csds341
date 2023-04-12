# SQL Fast Tutorial

SQL is a standard language for storing, manipulating and retrieving data in databases.


Create Database
----------------

```sql
CREATE DATABASE database_name;
```

Example, create a database called "Pokemoverse": 

```sql
CREATE DATABASE Pokemoverse;
```

Create Table
------------

```sql
CREATE TABLE table_name (
    column1 datatype,
    column2 datatype,
    column3 datatype,
   ....
);
```

Example, create a table called "Pokemons":

```sql
CREATE TABLE Pokemons (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(32) NOT NULL,
    power VARCHAR(64),
    age INT,
    PRIMARY KEY ( id )
);
```

Insert Into Table
-----------------

```sql
INSERT INTO table_name (column1, column2, column3, ...)
VALUES (value1, value2, value3, ...);
```

Example, insert a new Pokemon into the "Pokemons" table:

```sql
INSERT INTO Pokemons (name, power, age)
VALUES ("Pikachu", "Electric", 3);
```

Select From Table
-----------------

```sql
SELECT column1, column2, column3, ...
FROM table_name;
```

Example, select all the Pokemons from the "Pokemons" table:

```sql
SELECT * FROM Pokemons;
```


Select From Table Where
-----------------------

```sql
SELECT column1, column2, column3, ...
FROM table_name
WHERE condition;
```

Example, select all the Pokemons from the "Pokemons" table that are older than 5 years old:

```sql
SELECT * FROM Pokemons WHERE age > 5;
```

Update Table
------------

```sql
UPDATE table_name
SET column1 = value1, column2 = value2, ...
WHERE condition;
```

Example, update the age of all the Pokemons from the "Pokemons" table that are older than 5 years old:

```sql
UPDATE Pokemons SET age = 5 WHERE age > 5;
```

Delete From Table
-----------------

```sql
DELETE FROM table_name WHERE condition;
```

Example, delete all the Pokemons from the "Pokemons" table that are older than 5 years old:

```sql
DELETE FROM Pokemons WHERE age > 5;
```
