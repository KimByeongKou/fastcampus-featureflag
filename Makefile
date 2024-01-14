#!/bin/bash

.PHONY: run lint build test graphqlSchema image

run:
	./gradlew clean :featureflag-app:bootRun

build:
	./gradlew build -x test

tester:
	./gradlew :featureflag-app:tester:bootRun

docker:
	docker-compose up -d
image:
	./gradlew clean :featureflag-app:jibDockerBuild