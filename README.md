# JDBC Data Access Object (DAO) Pattern

This is a template for a Java project using the [Makefile](https://www.gnu.org/software/make/) build system.

## Structure

```bash
main
├── java
│   └── com
│       └── cwru
│           └── pokeverse
│               ├── App.java
│               ├── dao
│               │   ├── abstraction
│               │   │   ├── CRUD.java
│               │   │   └── PokemonDAO.java
│               │   └── implementation
│               │       └── PokemonImp.java
│               ├── models
│               │   └── Pokemon.java
│               └── utils
│                   ├── DatabaseConnection.java
│                   └── InitDatabase.java
└── resources
    └── db.properties
```

## Dependencies

- [Java](https://www.java.com/en/download/)
- [Make](https://www.gnu.org/software/make/)
- [JDBC](https://www.oracle.com/technetwork/java/javase/jdbc/index.html)
- [PostgreSQL](https://www.postgresql.org/)
- [PostgreSQL JDBC Driver](https://jdbc.postgresql.org/)
- [commons-dbcp2](https://commons.apache.org/proper/commons-dbcp/)


## Makefile commands

```bash
make source
```

This target finds all Java source files in the `$(SRC)` directory and saves their paths to a file named `sources.txt`.

```bash
make compile
```

This target compiles the Java source files using `$(JC) (the Java compiler)` and saves the compiled class files to the `$(DIST)` directory. It also includes the classpath of the `$(DIST)` directory and all JAR files in the `$(LIB)` directory.

```bash
make run
```

This target runs the main class of the project using `$(JAVA) (the Java runtime)` and the classpath of the `$(DIST)` directory and all JAR files in the `$(LIB)` directory.

```bash
make build
```

This target creates a `JAR` file named App.jar containing the compiled class files and all files in the `$(LIB)` directory, using `$(JAR) (the Java archiver)`.

```bash
make exec
```

This target runs the main class of the project using the `JAR` file created by the build target, as well as the classpath of the`$(DIST)` directory and all JAR files in the `$(LIB)` directory.
