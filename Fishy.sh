#!/bin/bash

cd -- "$(dirname "$BASH_SOURCE")"
FILE1=$(dirname "$BASH_SOURCE")/loading/target
FILE2=$(dirname "$BASH_SOURCE")/loading/target
if [ -d "$FILE1" ]; then
    cd loading
    echo "$FILE1 exists."
else 
    cd loading
    mvn package
    echo "$FILE1 does not exist."
fi
java -jar target/loading-0.1-Beta.jar &
cd ..
cd game
mvn clean 
mvn compile 
mvn package
pkill java
java -jar target/game-0.1-Beta.jar &

exit