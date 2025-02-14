package com.minesweeper.ncs;

import java.util.Random;

public class Board {
	private final int size;
	private final int mineCount;
	private final Cell[][] grid;
	private final Random random = new Random();
	
	public int getSize() {
		return size;
	}

	public int getMineCount() {
		return mineCount;
	}

	public Cell[][] getGrid() {
		return grid;
	}

	public Random getRandom() {
		return random;
	}

	public Board(int size, int mineCount) {
		this.size = size;
		this.mineCount = mineCount;
		this.grid = new Cell[size][size];
		initializeBoard();
	}

	private void initializeBoard() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				grid[i][j] = new Cell();
			}
		}
		placeMines();
		calculateAdjacentMines();
	}

	private void placeMines() {
		int placedMines = 0;
		while (placedMines < mineCount) {
			int row = random.nextInt(size);
			int col = random.nextInt(size);
			if (!grid[row][col].isMine) {
				grid[row][col].isMine = true;
				placedMines++;
			}
		}
	}

	private void calculateAdjacentMines() {
		int[] directions = {-1, 0, 1};
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!grid[i][j].isMine) {
					int count = 0;
					for (int dx : directions) {
						for (int dy : directions) {
							int ni = i + dx, nj = j + dy;
							if (ni >= 0 && ni < size && nj >= 0 && nj < size && grid[ni][nj].isMine) {
								count++;
							}
						}
					}
					grid[i][j].adjacentMines = count;
				}
			}
		}
	}

	public boolean revealCell(int row, int col) {
		
		if (row < 0 || row >= size || col < 0 || col >= size || grid[row][col].isRevealed) {
			return false;
		}
		grid[row][col].isRevealed = true;
		if (grid[row][col].isMine) {
			return true; // Game Over
		}
		if (grid[row][col].adjacentMines == 0) {
			for (int dx = -1; dx <= 1; dx++) {
				for (int dy = -1; dy <= 1; dy++) {
					revealCell(row + dx, col + dy);
				}
			}
		}
		
		return false;
	}

	public boolean allCellsRevealed() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!grid[i][j].isMine && !grid[i][j].isRevealed) {
					return false;
				}
			}
		}
		return true;
	}

	public void displayBoard(boolean revealMines) {
		
		System.out.print("  ");
		for (int i = 0; i < size; i++) System.out.print((i + 1) + " ");
		System.out.println();
		for (int i = 0; i < size; i++) {
			System.out.print((char) ('A' + i) + " ");
			for (int j = 0; j < size; j++) {
				if (grid[i][j].isRevealed || revealMines && grid[i][j].isMine) {
					System.out.print(grid[i][j].isMine ? "_ " : grid[i][j].adjacentMines + " ");
				} else {
					System.out.print("_ ");
				}
			}
			System.out.println();
		}
	}
	
	public void displayAdjacentmines(int row, int col) {
		if(!grid[row][col].isMine)System.out.println("This square contains "+grid[row][col].adjacentMines+" adjacent mines.\n");
		System.out.println("Here is your updated minefield:"); 
	}

}
