package com.minesweeper.ncs;

public class Cell {
	 boolean isMine;
	 boolean isRevealed;
	 int adjacentMines;

	 public Cell() {
	     this.isMine = false;
	     this.isRevealed = false;
	     this.adjacentMines = 0;
	 }
}
