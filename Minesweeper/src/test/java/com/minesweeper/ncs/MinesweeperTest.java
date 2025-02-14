package com.minesweeper.ncs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



class MinesweeperTest {
    private Board board;
    private InputStream originalSystemIn;
    @BeforeEach
    void setUp() {
        board = new Board(5, 3); // 5x5 grid with 3 mines
    }

    @Test
    void testBoardInitialization() {
        assertNotNull(board, "Board should be initialized");
    }

    @Test
    void testRevealMineCell() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board.revealCell(i, j) && board.allCellsRevealed()) {
                    fail("Game should end on revealing a mine");
                }
            }
        }
    }

    @Test
    void testRevealEmptyCell() {
        boolean revealed = board.revealCell(0, 0);
        assertFalse(revealed, "Revealing a non-mine cell should not end the game");
    }
    
    @Test
    void testDisplayBoard() {
        board.displayBoard(false);
    }
    

    @BeforeEach
    void saveSystemIn() {
        originalSystemIn = System.in; // Save the original System.in
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalSystemIn); // Restore the original System.in
    }

    // Helper method to reset the static scanner in Main
    private void resetMainScanner() {
        try {
            Field scannerField = Minesweeper.class.getDeclaredField("scanner");
            scannerField.setAccessible(true);
            scannerField.set(null, new Scanner(System.in)); // Reset the scanner
        } catch (Exception e) {
            fail("Failed to reset Main.scanner: " + e.getMessage());
        }
    }

    @Test
    void testGetGridSize() {
        // Simulate user input "4"
        String input = "4\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Set the simulated input stream
        resetMainScanner(); // Reset the scanner to use the new input

        // Call the method to test
        int size = Minesweeper.getGridSize();

        // Verify the result
        assertEquals(4, size);
    }

    @Test
    void testGetNumberOfMines() {
        // Simulate user input "2"
        String input = "2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Set the simulated input stream
        resetMainScanner(); // Reset the scanner to use the new input

        // Call the method to test
        int mines = Minesweeper.getNumberOfMines(4);

        // Verify the result
        assertEquals(2, mines);
    }

    @Test
    void testGetCellInput() {
        // Simulate user input "A1"
        String input = "A1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Set the simulated input stream
        resetMainScanner(); // Reset the scanner to use the new input

        // Call the method to test
        String cellInput = Minesweeper.getCellInput(4);

        // Verify the result
        assertEquals("A1", cellInput);
    }

    @Test
    void testPlayGameLoop() {
        // Mock the Board
        Board mockBoard = Mockito.mock(Board.class);
        //Board spyBoard = Mockito.spy(board);
        Mockito.when(mockBoard.getSize()).thenReturn(4); // Simulate a 4x4 board
        Mockito.when(mockBoard.revealCell(0, 0)).thenReturn(false); // Safe cell
        Mockito.when(mockBoard.allCellsRevealed()).thenReturn(false) // Game is not won initially
        .thenReturn(true);  // Game is won after revealing the cell
        // Simulate input: "A1" (cell) + "X" (press any key)
        String input = "A1\nX\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Set the simulated input stream
        resetMainScanner(); // Reset the scanner to use the new input

        // Run the game loop
        Minesweeper.playGameLoop(mockBoard.getSize(),mockBoard.getMineCount());
        //Minesweeper.playGameLoop(spyBoard.getSize(),spyBoard.getMineCount());
        //Mockito.verify(spyBoard).revealCell(0, 0);
        //Mockito.verify(spyBoard, Mockito.atLeastOnce()).allCellsRevealed();
    }
    
}
