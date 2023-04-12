# The build process can be done either by using the Makefile or Maven.
# Here are the commands to build the project using Maven.

MVN := /home/mohsen/apps/maven/apache-maven-3.9.1/bin/mvn
# MVN := /usr/bin/maven/mvn/bin/mvn

DgroupId = $(FPN)
DartifactId = $(PACKAGE)
DarchetypeArtifactId := maven-archetype-quickstart

# The following target will generate the project- no need to run this target, because the pom.xml file is already generated.
mv-generate-project:
	$(MVN) archetype:generate -DgroupId=$(DgroupId) -DartifactId=$(DartifactId) -DarchetypeArtifactId=$(DarchetypeArtifactId) -DinteractiveMode=false
# mvn help:generate-pom -DgroupId=com.example -DartifactId=my-project -Dversion=1.0-SNAPSHOT

# The following command will install the dependencies in pom.xml file.
mv-install-deps:
	$(MVN) install

# The following target will validate the project is correct and all necessary information is available.
mv-validate:
	$(MVN) validate

# The following target will run any checks on code quality.
mv-check:
	$(MVN) checkstyle:check

# The following target will compile the source code of the project.
mv-build:
	$(MVN) package


# The following target will generate any additional source code.
mv-generate:
	$(MVN) generate-sources

# The following target will compile the test source code of the project.
mv-test:
	$(MVN) test

# The following target will generate any additional test source code.
mv-generate-test:
	$(MVN) generate-test-sources

# The following target will compile and run the tests for the project.
mv-test-run:
	$(MVN) verify

# The following target will generate the Javadoc for the project.
mv-javadoc:
	$(MVN) javadoc:javadoc

# The following target will generate the classpath, and put it in the file classpath.txt.
mv-classpath:
	$(MVN) dependency:build-classpath -Dmdep.outputFile=classpath.txt

# The following target will download the dependencies and put them in the lib folder, which is nessary to execute the .jar file.
mv-download-deps:
	$(MVN) dependency:copy-dependencies -DoutputDirectory=$(LIB)

# The following target will run the project.
mv-run:
	$(MVN) exec:java -Dexec.mainClass="$(FPN).App"

