#!/bin/bash

.PHONY: run lint build test graphqlSchema image

run:
	./gradlew clean :featureflag-app:bootRun

build:
	./gradlew build -x test

test:
	./gradlew test -x build

docker:
	docker-compose up -d
image:
	./gradlew clean :featureflag-app:jibDockerBuild