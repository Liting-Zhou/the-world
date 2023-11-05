# Project of The World

### Name: Liting Zhou

### How to Run

Run command => `java -jar <path of jar file> <path of configuration file> <max number of turns>`

### How to Use the Program

1. Run the jar file as mentioned in How to Run.
2. Once the program is running, you will see the description of the game, and a menu of options to choose from.
3. Add human/computer controlled players with carry limit and starting room.
4. Choose to play next turn. Human player can choose different actions from move, pick up weapon and look around, while
   computer player chooses automatically. In each turn, target moves first and then one player can act.
5. You can choose to display the description of a specific player or a specific space in the world.
6. There is an option to quit the game. In this case, the program will be interrupted and the game will end.
7. Game is over if the max turns are exhausted or the target dies.

### Example Runs

Example run for milestone 1 can be found here: `res/exampleRun_m1.txt`.

Example run for milestone 2 can be found here: `res/exampleRun_m2.txt`. Specifically, the following function were shown
in this example run:

- line 23 - 41: adding a human-controlled player to the world
- line 45 - 63: adding a computer-controlled player to the world
- line 67 - 107: the player moving around the world
- line 111 - 149: the player picking up an item
- line 153 - 199: the player looking around
- line 67 - 240: taking turns between multiple players
- line 309 - 334: displaying the description of a specific player
- line 244 - 289: displaying information about a specific space in the world
- line 294 - 305: creating and saving a graphical representation of the world map to the current directory
- line 338 - 379: demonstrates the game ending after reaching the maximum number of turns

### Changes compared to milestone 1

- Add computer and human player classes which inherit the Player Class.
- Add player actions (move, pick up weapons and look around).
- Add a Random interface and corresponding class.
- Add methods to display information where needed.

### UML, Testing Plan, and updated UML

In `./res`

### Assumptions

- Target start from Room 0, which is the Billiards Room.
- Players are assigned different starting room and maximum number of weapons carried. The carry limit is not decided
  yet. I am thinking of 5.

### Citations

Using of multiple
inputs - <ins>https://stefanbirkner.github.io/system-rules/apidocs/org/junit/contrib/java/lang/system/TextFromStandardInputStream.html </ins>