package com.minesweeper.ncs;

import java.util.Scanner;


public class Minesweeper {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            playGame();
        }
    }

    public static void playGame() {
        System.out.println("Welcome to Minesweeper!\n");
        int size = getGridSize();
        int mines = getNumberOfMines(size);

        if (size < 2 || mines < 1 || mines > (size * size * 35 / 100)) {
            System.out.println("Invalid input. Exiting...\n\n");
            return;
        }
       
        playGameLoop(size, mines);
    }

    public static int getGridSize() {
        System.out.println("Enter the size of the grid (e.g. 4 for a 4x4 grid):");
        if (!scanner.hasNextInt()) { //check for input type 
			System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
		}
        return scanner.nextInt();
    }

    public static int getNumberOfMines(int size) {
        System.out.println("Enter the number of mines to place on the grid (maximum is 35% of the total squares): ");
        if (!scanner.hasNextInt()) { //check for input type 
			System.out.println("Invalid input. Please enter a valid number.");
            scanner.next(); // Clear invalid input
		}
        return scanner.nextInt();
    }

    public static void playGameLoop(int size,int mines) {
    	 Board board = new Board(size, mines);
        System.out.println("\nHere is your minefield:");
        while (true) {
            board.displayBoard(false);
            String input = getCellInput(size);

            int row = input.charAt(0) - 'A';
            int col = Integer.parseInt(input.substring(1)) - 1;

            System.out.println("\nHere is your updated minefield:");
            if (board.revealCell(row, col)) {
                board.displayBoard(true);
                System.out.println("Oh no, you detonated a mine! Game over.");
                break;
            }
            if (board.allCellsRevealed()) {
                System.out.println("Congratulations! You have won the game!");
                break;
            }
            board.displayAdjacentmines(row,col);
        }
        System.out.println("Press any key to play again...");
        scanner.next();
    }

    public static String getCellInput(int size) {
        while (true) {
            System.out.print("\nSelect a square to reveal (e.g. A1):");
            String input = scanner.next().toUpperCase();

            if (input.length() < 2 || input.length() > 3) {
                System.out.println("Invalid input format. Use A1, B2, etc.\n");
                continue;
            }
            char rowChar = Character.toUpperCase(input.charAt(0));
            if (rowChar < 'A' || rowChar >= 'A' + size) {
                System.out.println("Invalid row selection.");
                continue;
            }
            int col;
            try {
                col = Integer.parseInt(input.substring(1)) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid column selection.\n");
                continue;
            }
            if (col < 0 || col >= size) {
                System.out.println("Invalid column selection.\n");
                continue;
            }
            return input;
        }
    }
 }
