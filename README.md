# Project of The World

### Name: Liting Zhou

### How to Run

    java -jar <path of jar file> <path of configuration file> <max number of turns>
    java -jar ./res/the-world.jar ./res/mansion.txt 50



### How to Use the Program

1. Run the jar file as mentioned in How to Run.
2. Once the program is running, you will see the description of the game, and a menu of options to choose from.
3. Add human/computer controlled players with weapon carry limit and starting room.
4. Choose to play next turn. Human player can choose different actions from move, pick up weapon, look around, move the pet or attack if the target is in the same room, while
   computer player always attacks when there is a chance, otherwise performs random choice. In each turn, target moves first and then one player can act.
5. You can choose to display the description of the target, a specific player or a specific space in the world.
6. There is an option to quit the game. In this case, the program will be interrupted and the game will end.
7. Game is over if the max turns are exhausted or the health of target is less or equal to zero.

### Example Runs

Example run for milestone 1 can be found here: `res/exampleRun_m1.txt`.

Example run for milestone 2 can be found here: `res/exampleRun_m2.txt`. 

Example run for milestone 3 can be found here: `res/exampleRun_m3.txt`. Specifically, the following function were shown
in this example run:

- line 3 - 50: the target character's pet effect on the visibility of a space from neighboring spaces
- line 53 - 90: the player moving the target character's pet
- line 95 - 134: a human-player making an attempt on the target character's life
- line 138 - 175: a computer-controlled player making an attempt on the target character's life
- line 180 - 223: a human-player winning the game by killing the target character
- line 227 - 263: a computer-controlled player winning the game by killing the target character
- line 270 - 319: the target character escaping with his life and the game ending
- line 327 - 377: Player attacked but failed (can be seen)
- line 382 - 511: The pet moves based on a specific route calculated by a depth-first traversal algorithm

### Changes between milestones
#### Changes of milestone 2 compared to milestone 1

- Add computer and human player classes which inherit the Player Class.
- Add player actions (move, pick up weapons and look around).
- Add a Random interface and corresponding class.
- Add methods to display information where needed.

#### Changes of milestone 3 compared to milestone 2

- Add target description in the world.
- Add player actions (move the pet and attack the target).
- Add a Pet interface and corresponding Cat class, and implement a depth-first traversal algorithm which determined the route of cat wandering.

### UML, Testing Plan, and updated UML

In `./res`

### Assumptions

- Target and the pet start from Room 0, which is the Billiards Room.
- Players are assigned different starting room and maximum number of weapons carried.
- Any space that is occupied by the pet cannot be seen by its neighbors.

### Citations

Using of multiple
inputs - <ins>https://stefanbirkner.github.io/system-rules/apidocs/org/junit/contrib/java/lang/system/TextFromStandardInputStream.html </ins>