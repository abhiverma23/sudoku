package com.vabhishek.generator.impl;

import com.vabhishek.data.SudokuBoard;
import com.vabhishek.generator.SudokuGenerator;

public class SimpleSudokuGenerator extends SudokuGenerator {

    private int boardSize = blockSize * blockSize;

    @Override
    protected SudokuBoard createBoard() {
        init();
        final var board = SudokuBoard.createEmptyBoard(blockSize);
        return null;
    }

    private void init() {
        boardSize = blockSize * blockSize;
    }
}
