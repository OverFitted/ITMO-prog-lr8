#!/bin/bash

read -p 'Max servers: ' max

./gradlew build

# Start the first three JAR files with nohup and logging
nohup java -jar GatewayLBService/build/libs/GatewayLBService-1.0-SNAPSHOT-all.jar > gateway.log &
for i in `seq 1 $max`
do
	nohup java -jar server/build/libs/server-1.0-SNAPSHOT-all.jar > server$i.log &
done

# Start the fourth JAR file with system.in
java -jar client/build/libs/client-1.0-SNAPSHOT-all.jar
