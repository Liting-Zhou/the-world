# Project of The World

### Name: Liting Zhou

### How to Run

    java -jar <path of jar file> <path of configuration file> <max number of turns> <play mode>
    java -jar ./res/the-world.jar ./res/mansion.txt 50 1               // for GUI mode
    java -jar ./res/the-world.jar ./res/mansion.txt 50 2               // for console mode

### How to Use the Program

1. Run the jar file as mentioned in **How to Run**.
2. There are two ways to play the game: GUI mode and console mode.
3. Game is over if the max turns are exhausted, the health of target is less or equal to zero, or you choose to exit the game.

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

#### Main changes of milestone 2 compared to milestone 1

- Add computer and human player classes which inherit the Player Class.
- Add player actions (move, pick up weapons and look around).
- Add a Random interface and corresponding class.
- Add methods to display information where needed.

#### Main changes of milestone 3 compared to milestone 2

- Add target description in the world.
- Add player actions (move the pet and attack the target).
- Add a Pet class, and implement a depth-first traversal algorithm which determined the
  route of pet wandering.

#### Main changes of milestone 4 compared to milestone 3

- Add a GUI interface and corresponding class.

### UML, Testing Plan, and updated UML

In `./res`.

### Assumptions

- Target and the pet start from Room 0, which is the Billiards Room.
- Target moves in the order of room numbers.
- Pet moves in an order determined by a depth-first traversal algorithm.
- Any space that is occupied by the pet cannot be seen by its neighbors.
- At most 10 players can be added to the game.
- Players are assigned different starting room and carry limit.
- Players can only move to the neighboring rooms.
- Rooms which share a wall are neighbors.

### Citations

Using of multiple
inputs - <ins>https://stefanbirkner.github.io/system-rules/apidocs/org/junit/contrib/java/lang/system/TextFromStandardInputStream.html </ins>