# Call Service

This project uses Quarkus, the Supersonic Subatomic Java Framework.
If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Pre requisites

You need java 11 to build and run the application and a mongodb instance.
You can run a mongodb instance using:

```
docker-compose up -d
```

## Configurations in application.yaml:

Define period of cache in /api/calll/statistics requests (in seconds)

```
statistics-cache: 60
```

Define block of minutes X cost

```
rates:
  - 
    until: 5
    cost: 0.10
  - 
    until: 43200
    cost: 0.05
```

## Build the application

You can build your application using:

```
./mvnw clean install -DskipTestsClient
```

## Running the application in dev mode

After that, you can run your application in dev mode that enables live coding using:

```
./mvnw -f call-service/pom.xml quarkus:dev
```

## Use the client 

You can run your client in test mode using:

```
./mvnw -f call-client/pom.xml clean package -DskipTestsClient=false
```

Create a simple call client:

```
CallClient callClient = new CallClient.Builder().build();
```

Create a call client with custom configurations:

```
CallClient callClient = new CallClient.Builder()
				.withUrl("<your_custom_url>")
				.withReadTimeout(10)
				.withConnectiontimeout(60)
				.build();
```

Use the client:

```
callClient.createCall(call);
```


## Access the swagger-ui 

You can run your application in dev mode that enables live coding using:

```
http://localhost:8080/q/swagger-ui/#/
```



## Create a topic in kafka

You can create a topic in kafka using:

```
./opt/bitnami/kafka/bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic calls
```





