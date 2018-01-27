# traintracks

# BUILD
cd <traintracks-dir>
mvn clean install

# RUN
java -jar train-tracks-engine/target/train-tracks-engine-1.0-SNAPSHOT-jar-with-dependencies.jar

# TODO
unit test:
scoring, Route.connectTo(), Ticket.isTicketCompleted()

test scoring
change score to not include tickets during game play (except maybe for player to see personal score including tickets

implement discard tickets

bug -- if exception when drawing a car, don't go back to beginning of turn, resume drawing cars
feature -- show open cars before turn starts
feature -- list completable routes in vertical list
feature -- display flavor of route when choosing to build a route
feature -- add cancel option to first car draw and build a route selection
feature -- add TTCompletedRoute.toString()
end of game when carriage count <= 2 then each player gets one more round.
add turns to turn history in Game object

could show numbers of cars in hand grouped by flavor

create containing class for car draw deck and open cars (aka car zone) 
could keep all states in one container (not in game and boardstate)
board should have entire deck, not part of deck since it should be stateless

don't allow building routes option if no routes left to build

create a new exception class to surface user errors
improve readchar and prompts -- show board and player state
add unit tests
decide on api

---

break into separate clients
ui
add ai players
client retrieves static board metadata (uuids for everything)
