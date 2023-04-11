# https://www.gnu.org/software/make/manual/make.html
include .env.dev
export

include *.make

VERSION := $(shell cat VERSION)
PROJECT := $(shell basename $(CURDIR))

# JAVA := /home/mohsen/compiler/jdk/bin/java
JAVA := /usr/bin/java
# JAR := /home/mohsen/compiler/jdk/bin/jar
JAR := /usr/bin/jar
# JC := /home/mohsen/compiler/jdk/bin/javac
JC := /usr/bin/javac

DOCKER := /usr/bin/docker

PATH := ./bin:$(PATH)

.PHONY: all dev clean

SRC = src
DIST = target
LIB = lib

COMPANY = com
PROJECT = pokeverse
PACKAGE = pokemon

FPN = $(COMPANY).$(PROJECT).$(PACKAGE)

JFLAGS = -g -d $(DIST)

.PHONY: env test all dev clean dev gen-commands $(SRC) $(DIST) $(BUILD)

.DEFAULT_GOAL := test

.ONESHELL:

%: # https://www.gnu.org/software/make/manual/make.html#Automatic-Variables 
		@:

test:
		@$(JAVA) --version
		@$(JAR) --version
		@$(JC) --version

source:
		find $(SRC) -name "*.java" > sources.txt

compile:
		$(JC) $(JFLAGS) -cp "$(DIST):$(LIB)/*" @sources.txt

run:
		$(JAVA) -cp "$(DIST):./lib/*" $(FPN).App

all: source compile run

build:
		find $(DIST) -name "*.class" > classes.txt
		$(JAR) cvfm App.jar Manifest.txt -C $(DIST) . -C $(LIB) .

exec:
		$(JAVA) -cp "$(DIST):$(LIB)/*:./App.jar" $(FPN).App

clean:
		rm -r ./$(DIST)

test-main:
		$(JAVA) -cp "$(DIST):$(LIB)/*" org.junit.runner.JUnitCore $(FPN).AppTest

download-dependencies:
		# download postgresql to ./lib
		wget https://repo1.maven.org/maven2/org/postgresql/postgresql/42.6.0/postgresql-42.6.0.jar -P $(LIB)
		# download junit to ./lib
		wget https://repo1.maven.org/maven2/junit/junit/4.13/junit-4.13.jar -P $(LIB)
		# download commons-dbcp2 to ./lib
		wget https://repo1.maven.org/maven2/org/apache/commons/commons-dbcp2/2.8.0/commons-dbcp2-2.8.0.jar -P $(LIB)
