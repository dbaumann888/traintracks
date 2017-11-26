# traintracks

# BUILD
cd <traintracks-dir>
mvn clean install

# RUN
java -jar train-tracks-engine/target/train-tracks-engine-1.0-SNAPSHOT-jar-with-dependencies.jar

# TODO
maybe don't show routes that you don't have the cards to build
could show numbers of cars in hand grouped by flavor
could show open cars before deciding what action to take in the turn
could show buildable routes

create containing class for car draw deck and open cars (aka car zone) 

don't allow building routes if no routes left to build

compute scores
create a new exception class to surface user errors
build out the maps
play the game for real and input moves from computer
improve readchar and prompts -- show board and player state
add unit tests

---

break into separate clients
ui
add ai players
client retrieves static board metadata (uuids for everything)

