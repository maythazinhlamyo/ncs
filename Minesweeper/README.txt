#Minesweeper Game

##Overview

This is a simple command-line implementation of Minesweeper in Java. The game allows players to select grid sizes and the number of mines before starting the game. Players reveal cells until they either hit a mine (game over) or reveal all non-mine cells (win).

###Features

-Dynamic board size selection

-Configurable mine count

-Recursive reveal for empty cells

-Simple user-friendly interface

###Requirements

-Java 8 or later

-JUnit 5 for testing

-maven

## How to Run

1. Extract the zip file.
2. Navigate to the project directory.
3. Build the project using Maven:
   ```bash
   mvn clean install
   
## How This Works
Cell Class - Stores information about each square (mine, revealed state, adjacent mines).
Board Class - Initializes the grid, places mines, calculates adjacent numbers, and manages revealing logic.
Minesweeper Class - Entry point to start the game and Handles user interaction, game loop, and victory conditions