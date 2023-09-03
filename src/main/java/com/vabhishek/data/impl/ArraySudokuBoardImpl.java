package com.vabhishek.data.impl;

import com.vabhishek.data.SudokuBoard;

public class ArraySudokuBoardImpl extends SudokuBoard {
    private final int[][] board = new int[boardSize][boardSize];
    private final boolean validationEnabled;

    public ArraySudokuBoardImpl(int blockSize, boolean enableValidations) {
        super(blockSize);
        validationEnabled = enableValidations;
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = EMPTY_VAL;
            }
        }
    }

    protected int getVal(int x, int y) {
        return board[x][y];
    }

    protected void setVal(int x, int y, int val) {
        board[x][y] = val;
    }

    protected boolean isValidationEnabled() {
        return validationEnabled;
    }
}
