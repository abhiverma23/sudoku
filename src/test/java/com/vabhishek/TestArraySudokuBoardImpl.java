package com.vabhishek;

import com.vabhishek.data.SudokuBoard;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestArraySudokuBoardImpl {

    private final int BLOCK_SIZE = 3;
    private final int BOARD_SIZE = BLOCK_SIZE * BLOCK_SIZE;
    private final SudokuBoard validArrBoard = SudokuBoard.createEmptyBoard(BLOCK_SIZE,false);
    private final SudokuBoard invalidArrBoard = SudokuBoard.createEmptyBoard(BLOCK_SIZE,false);

    @Before
    public void initBoards() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            var value = ((i * BLOCK_SIZE) % BOARD_SIZE) + (i / BLOCK_SIZE); // Get every row starting value
            for (int j = 0; j < BOARD_SIZE; j++) {
                final var v = value++;
                validArrBoard.set(i, j, (v % BOARD_SIZE) + 1);
                invalidArrBoard.set(i, j, (v % BOARD_SIZE) + 1);
            }
        }
        invalidArrBoard.set(0,0, 2);
    }

    @Test
    public void constructorTest() {
        SudokuBoard arrayBoard = SudokuBoard.createEmptyBoard(BLOCK_SIZE);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                assertTrue(arrayBoard.isEmptyVal(arrayBoard.get(i, j)));
            }
        }
    }

    @Test
    public void generateValidArrBoardTest() {
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(3);
        for (int i = 0; i < BOARD_SIZE; i++) {
            var value = ((i * BLOCK_SIZE) % BOARD_SIZE) + (i / BLOCK_SIZE); // Get every row starting value
            for (int j = 0; j < BOARD_SIZE; j++) {
                assertTrue(testBoard.set(i, j, (value++ % BOARD_SIZE) + 1));
            }
        }
    }

    @Test
    public void performInvalidOperationTest() {
        final int blockSize = 3, boardSize = blockSize * blockSize;
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(blockSize);
        for (int i = 0; i < boardSize; i++) {
            var value = ((i * blockSize) % boardSize) + (i / blockSize); // Get every row starting value
            for (int j = 0; j < boardSize; j++) {
                assertTrue(testBoard.set(i, j, (value++ % boardSize) + 1));
            }
        }
        assertFalse(testBoard.set(0,0, 2));
        assertFalse(testBoard.set(0,0, 9));
    }

    @Test
    public void blockValidationTest() {
        final int blockSize = 3;
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(blockSize);
        assertTrue(testBoard.set(3,3,1));
        assertTrue(testBoard.set(3,4,5));
        assertTrue(testBoard.set(3,5,9));
        assertFalse(testBoard.set(4,5,9));
        assertTrue(testBoard.set(4,5,8));
    }

    @Test
    public void horizontalValidationTest() {
        final int blockSize = 3;
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(blockSize);
        assertTrue(testBoard.set(3,3,1));
        assertTrue(testBoard.set(3,4,5));
        assertTrue(testBoard.set(3,5,9));
        assertFalse(testBoard.set(3,0,9));
        assertTrue(testBoard.set(3,0,8));
    }

    @Test
    public void verticalValidationTest() {
        final int blockSize = 3;
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(blockSize);
        assertTrue(testBoard.set(3,3,1));
        assertTrue(testBoard.set(4,3,5));
        assertTrue(testBoard.set(5,3,9));
        assertFalse(testBoard.set(6,3,9));
        assertTrue(testBoard.set(6,3,8));
    }

    @Test
    public void reUpdateValidationTest() {
        final int blockSize = 3;
        SudokuBoard testBoard = SudokuBoard.createEmptyBoard(blockSize);
        assertTrue(testBoard.set(3,3,1));
        assertTrue(testBoard.set(4,3,5));
        assertTrue(testBoard.set(5,3,9));
        assertTrue(testBoard.set(3,3,7));
        assertTrue(testBoard.set(3,4,8));
        assertTrue(testBoard.set(3,5,2));
        assertFalse(testBoard.set(6,3,9));
        assertTrue(testBoard.set(4,4,4));
        assertTrue(testBoard.set(4,4,3));
    }
}
