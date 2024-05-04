# ScoreBoard
The aim of this project is to provide a simple library for Live Football World Cup Score Board.
## Requirements definition
### Functional requirements description
* Adding a new match for two teams. One is "Home Team" and the second is "Away Team"
* Match should start with result 0–0
* Update score with a pair of absolute scores of "Home Team" and "Away Team"
* Finish match. This removes match from the scoreboard.
* Get a summary of matches in progress ordered by their total score.
  If matches have the same total score order by most recently 
started match

### Problem description
To describe the problem, there is a need to find actual entities that create scoreboard.
Inspiration for further problem description is simply TV Football match. 
During the TV broadcast, we can see that there are:
* **Match** this is the main entity that encapsulates all changes, events, results, etc. of the competition.
* **Match** is built of two competing **teams**. Namely, Home Team and Away Team.
* **Match** has **Result bar** witch indicates a current result of **teams**
* For measuring the progress of different matches, additional entity is used. It is indeed the **scoreboard**.

