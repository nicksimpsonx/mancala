#Mancala 12 Hour Challenge
This application is the result of a programming challenge to create a version of the classic strategy game **Mancala** within 12 hours.

## Challenge Objectives:
- The game should be playable using a REST API using a tool such as CURL or Postman.
- The players will be human opponents, no AI is needed.
- There is no requirement for a graphical user interface.
- There is no requirement for a persistence layer.
- Players should submit a **POST** request to a games endpoint to initialise a game and get a game ID.
- Players should then take their turns by submitting **PUT** requests to a gameplay endpoint.
- This implementation of Mancala should use 6 stones per pit.
- The objective is to have a working game with as much documentation and unit tests as possible.
- The rules of the game can be found here: <https://en.wikipedia.org/wiki/Kalah>

## Author
This version of Mancala was programmed by **Nicholas Simpson** in October 2020

## Documentation
- The code is well documented and the application also includes interactive API documentation. 
- When the application is running you can view the interactive api documentation here:
<http://localhost:8080/swagger-ui.html>

## How to build and run the game
<p>This application is a maven project, this guide assumes you have maven installed as well as java (1.8+) and your environment variables have been configured correctly.</p>
In the root directory of the project where you can see the POM.xml file you can build and run this game from your command line.
From the command line in the root folder:<br>

- mvn clean install
- cd target 
- java -jar mancala-0.0.1-SNAPSHOT.jar

## Playing the game
The game is playable via the interactive api documentation or a rest tool like Postman or CURL

With the game running:

To create a new game submit a POST request to:
http://localhost:8080/games/

To make a move submit a PUT request to:
http://localhost:8080/games/{gameId}/pits/{pitNumber}

Please note that the game id must be a valid one which you've created with the App, and the pit number must be valid too

## My thoughts
I am pleased with this application given the time I had to work on it. It was developed using TDD methodology, I think that
the unit tests are meaningful and have resulted in a good implementation of this game which works as it should. Ideally I
would have liked another 8 hours to work on it but I have uploaded it here warts and all because it is an honest representation
of how I work in a tight timeframe.

## Known issues
<p>There was no specification for a persistence layer or for a mechanism to clean up finished or abandoned games, therefore
it is possible for this to run out of memory when too many games instantiated. It would be good to have a limit on the
number of games in progress at any one time.</p>
<p>When looking at the code you will see that the games service returns ResponseEntities thereby coupling the service layer
to the HTTP layer, I planned to refactor this but ran out of time.</p>  
<p>Arguably it is better practice these days to return Optionals instead of nulls, you'll see one or two nulls being 
returned here and there, again this is something I intended to clean up but ran out of time.</p>

# Future improvements
On a rainy day I might come back to this project and add these some or all of these things:
- Add AI players
- Add end point to get a list of current games
- Add endpoint to terminate game early
- Add mechanism to clean up abandoned games
- Limit the amount of games in progress
- Add more tests specifically for the rest controller and integration tests



