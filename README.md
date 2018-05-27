# traintracks

# BUILD
cd <traintracks-dir>
mvn clean install

# RUN
java -jar traintracks-server-cli/target/traintracks-server-cli-1.0-SNAPSHOT-jar-with-dependencies.jar

# CALL SERVER
http://localhost:8200/tt/Boards

# TODO
fix servletContext ListenerHolder so that in servlet we can get the context with the game
fix the path so the PathInfo in the servlet doesn't have a leading / (maybe?)

return json results for Board, BoardState, Players, PlayerStates, other objects?
figure out how to return multiple rows -- eg. for a query returning 3 PlayerStates
work on client CLI to make requests to server
work on server to modify BoardStates and PlayerStates
break down Turns into TurnParts so half turns can be queried (eg. draw the first Car)
add polling loop to client to get updates about other players turns

later remove the instance of the game in the GameFactory
later make client polling smarter to detect if queried objects changed before requesting entire objects
later divide PlayerState into public and private versions so players can't see each other's statuses
later divide BoardState into public and private versions so players can't see server's private board state

unit test:
scoring of Longest Route
Route.connectTo()
Ticket.isTicketCompleted()

change score to show public score (and not reveal ticket score to other players)

add PlayerStation for europe built stations by player and pass in to updateScore to compute stationScore;

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
