# Mavan

## Installation

You can download the latest version of Maven from [the official website](https://maven.apache.org/download.cgi).

```bash
wget https://downloads.apache.org/maven/maven-3/3.9.1/binaries/apache-maven-3.9.1-bin.tar.gz

sudo tar -xvzf apache-maven-3.9.1-bin.tar.gz -C /path/to/your/directory
```

Then, create a symbolic link to the maven directory in the /opt directory.
```bash
sudo ln -s /home/mohsen/maven/apache-maven-3.9.1 /usr/local/maven
```

Now, you need to set the environment variables for Maven. To do this, open the /etc/profile file and add the following lines at the end of the file.

```bash
export M2_HOME=/usr/local/maven
export PATH=${M2_HOME}/bin:${PATH}
```

Finally, run the following command to apply the changes.

```bash
source /etc/profile
```

Now, you can verify the installation by running the following command.

```bash
mvn -version
```

[1]: https://maven.apache.org/download.cgi

Initialize a Java project with Maven:

```bash
mvn archetype:generate -DgroupId=com.company.maven -DartifactId=projectname -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false
```
This command will create a new directory named hello-world in the current directory. This directory contains the following files:
```bash
hello-world
├── pom.xml
└── src
    ├── main
    │    └──java
    │       └── com
    │           └── company
    │               └── projectname
    │                   └── App.java
    │   
    └── test
        └── java
            └── com
                └── company
                    └── projectname
                        └── AppTest.java
```

The pom.xml file contains the project information and the dependencies.


## Maven Commands

To build the project, run the following command:
```bash
mvn package
```

This command will create a new directory named target in the hello-world directory. This directory contains the following files:
```bash
target
├── classes
│   └── com
│       └── company
│           └── projectname
│               └── App.class
├── projectname-1.0-SNAPSHOT.jar
├── maven-archiver
│   └── pom.properties
├── maven-status
│   └── maven-compiler-plugin
│       └── compile
│           └── default-compile
│               ├── createdFiles.lst
│               └── inputFiles.lst
└── surefire-reports
    └── com.company.prjectname.AppTest.txt
```

To add a new dependency, open the pom.xml file and add the following lines to the dependencies section:
```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>
```

Now, you can use the dependency in the App.java file:
```java
package com.company.maven;
```

To run the project, run the following command:

```bash
java -cp target/projectname-1.0-SNAPSHOT.jar com.company.projectname.App
```

To run the tests, run the following command:
```bash
mvn test
```

To run the tests and build the project, run the following command:
```bash
mvn verify
```

To run the tests and build the project and install the project in the local Maven repository, run the following command:
```bash
mvn install
```

To run the tests and build the project and install the project in the local Maven repository and deploy the project to the remote Maven repository, run the following command:

```bash
mvn deploy
```

## Project Object Model (POM)

It contains the following sections:

Project Information

```xml
<groupId>com.mohsen.maven</groupId>
<artifactId>hello-world</artifactId>
<version>1.0-SNAPSHOT</version>
```
Project Dependencies
- Dependencies
which is the list of dependencies of the project. The dependencies are stored in the local Maven repository. For example, the Maven project is stored in the following directory in the local Maven repository:
```bash
~/.m2/repository/org/apache/maven/maven-core/3.9.1
```
To execute .jar files, you need to download jar files.

```bash
mvn dependency:copy-dependencies -DoutputDirectory=target/dependency
```

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-lang3</artifactId>
        <version>3.12.0</version>
    </dependency>
</dependencies>
```

Project Build
- build, is the configuration of the project. The build configuration contains the following sections:
- plugins, which is the list of plugins that are used to build the project. For example, the Maven project is stored in the following directory in the local Maven repository:

```bash
~/.m2/repository/org/apache/maven/maven-core/3.9.1
```

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.8.1</version>
            <configuration>
                <source>1.8</source>
                <target>1.8</target>
            </configuration>
        </plugin>
    </plugins>
</build>
```


```xml
<dependency>
    <groupId>org.apache.commons</groupId>
    <artifactId>commons-lang3</artifactId>
    <version>3.12.0</version>
</dependency>
```


## Maven version 4.0.0
It is the alpha version you can find the release notes of Maven version 4.0.0 [here](https://maven.apache.org/ref/4-LATEST).