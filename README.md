# traintracks

# BUILD
cd <traintracks-dir>
mvn clean install

# RUN SERVER
java -jar traintracks-server-cli/target/traintracks-server-cli-1.0-SNAPSHOT-jar-with-dependencies.jar

# CALL SERVER
http://localhost:8200/tt/Boards

# RUN SERVER CLI
???

# TODO
fix servletContext ListenerHolder so that in servlet we can get the context with the game
cache the TTBoards so we don't process json setup on every request

draw design document of object containment

start up clients with a player uuid so each client knows who they are
work on client CLI to make requests to server
work on server to modify BoardStates and PlayerStates to do updates
break down Turns into TurnParts so half turns can be queried (eg. draw the first Car)
add polling loop to client to get updates about other players turns

later add ability for client to create game and/or join a game
later remove the instance of the game in the GameFactory
later add checksum for all state objects with master checksum for polling efficiency (perf enhancement for later)
later divide PlayerState into public and private versions so players can't see each other's statuses
later divide BoardState into public and private versions so players can't see server's private board state

unit test:
scoring of Longest Route
Route.connectTo()
Ticket.isTicketCompleted()

change score to show public score (and not reveal ticket score to other players)

add PlayerStation for europe built stations by player and pass in to updateScore to compute stationScore;

anything left to do to implement discarding of tickets?

bug? may be resolved -- if exception when drawing a car, don't go back to beginning of turn, resume drawing cars
feature -- show open cars before turn starts
feature -- list completable routes in vertical list
feature -- display flavor of route when choosing to build a route
feature -- add cancel option to first car draw and build a route selection
feature -- add TTCompletedRoute.toString()
end of game when carriage count <= 2 then each player gets one more round.
add turns to turn history in Game object

could show numbers of cars in hand grouped by flavor

create containing class for car draw deck and open cars (aka car zone) 

don't allow building routes option if no routes left to build

create a new exception class to surface user errors
improve readchar and prompts -- show board state and player state

add graphical ui for client
add ai players
