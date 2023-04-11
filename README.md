# Java Project Template

This is a template for a Java project using the [Makefile](https://www.gnu.org/software/make/) build system.

## Structure

```bash
src/
├── main
│   ├── java
│   │   └── com
│   │       └── pokeverse
│   │           └── pokemon
│   │               └── App.java
│   └── resources
│       └── db.properties
└── test
    └── java
        └── com
            └── pokeverse
                └── pokemon
                    └── AppTest.java
```

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
