.PHONY: up down build run test clean logs

up:
	docker compose up -d

down:
	docker compose down

build:
	mvn clean package -DskipTests

run:
	docker compose up

test:
	mvn test

clean:
	mvn clean
	rm -rf target/

logs:
	docker compose logs -f warehouse-app