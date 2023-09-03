package com.vabhishek.generator;

import com.vabhishek.data.SudokuBoard;

public abstract class SudokuGenerator {

    protected int blockSize = 3;
    protected int maxFill = 20;

    protected abstract SudokuBoard createBoard();
    public SudokuGenerator setBlockSize(final int blockSize) {
        this.blockSize = blockSize;
        return this;
    }

    public SudokuGenerator setMaxFill(final int maxFill) {
        this.maxFill = maxFill;
        return this;
    }

    public SudokuBoard generate() {
        return createBoard();
    }
}
