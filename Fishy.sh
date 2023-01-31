#!/bin/bash

cd -- "$(dirname "$BASH_SOURCE")"
FILE1=$(dirname "$BASH_SOURCE")/loading/target
if [ -d "$FILE1" ]; then
    cd loading
    java -jar target/loading-0.1-Beta.jar &
    cd ..
    cd game
    mvn compile
    pkill java
    java -jar target/game-0.1-Beta.jar &
else 
    cd loading
    mvn clean 
    mvn compile 
    mvn package
    java -jar target/loading-0.1-Beta.jar &
    cd ..
    cd game
    mvn clean 
    mvn compile 
    mvn package
    pkill java
    java -jar target/game-0.1-Beta.jar & 
fi

exit