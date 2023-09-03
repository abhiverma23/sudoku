package com.vabhishek.data;

import com.vabhishek.data.impl.MapSudokuBoardImpl;

public abstract class SudokuBoard {

    protected final int blockSize;
    protected final int boardSize;
    protected final int minVal = 1;
    protected final int maxVal;
    protected static final int EMPTY_VAL = -1;

    protected abstract void setVal(int x, int y, int val);

    protected abstract int getVal(int x, int y);

    protected abstract boolean isValidationEnabled();

    public static SudokuBoard createEmptyBoard(int blockSize) {
        return new MapSudokuBoardImpl(blockSize, true);
    }

    public static SudokuBoard createEmptyBoard(int blockSize, boolean enableValidations) {
        return new MapSudokuBoardImpl(blockSize, enableValidations);
    }

    protected SudokuBoard(final int blockSize) {
        if (blockSize <= 1 || blockSize > 10) throw new IllegalArgumentException("Block size can not be less than 1 and greater than 10");
        this.blockSize = blockSize;
        this.boardSize = blockSize * blockSize;
        this.maxVal = boardSize;
    }

    public final boolean setEmpty(int x, int y) {
        setVal(x, y, getEmptyVal());
        return true;
    }

    //region Public methods NO override allowed.
    public final boolean set(int x, int y, int val) {
        if (!isValidationEnabled() || isEmptyVal(val) || (isCoordinateValid(x, y) && isSetAllowed(x, y, val) && isValValid(val))) {
            setVal(x, y, val);
            return true;
        }
        return false;
    }

    public final int get(int x, int y) {
        return getVal(x, y);
    }

    public final int getEmptyVal() {
        return EMPTY_VAL;
    }

    public final boolean isEmptyVal(int val) {
        return val == getEmptyVal();
    }
    //endregion

    //region Protected methods Allowed override
    protected boolean isSetAllowed(int x, int y, int val) {
        boolean allowed = true;
        //Same box search
        final int boxI = (x / blockSize) * blockSize, boxJ = (y / blockSize) * blockSize;
        for (int i = 0; i < blockSize; i++) {
            for (int j = 0; j < blockSize; j++) {
                final var k = boxI + i; final var l = boxJ + j;
                final var v = get(k,l);
                if (isEmptyVal(v) || (k == x && l == y )) continue;
                if (val == v) {
                    allowed = false;
                    break;
                }
            }
        }
        //Same horizontal line search
        for (int i = 0; i < blockSize; i++) {
            if (boxI/ blockSize != i) {
                for (int k = i * blockSize; k < i* blockSize + blockSize; k++) {
                    final var v = get(k,y);
                    if (isEmptyVal(v)) continue;
                    if (v == val) {
                        allowed = false;
                        break;
                    }
                }
            }
        }
        //Same vertical line search
        for (int j = 0; j < blockSize; j++) {
            if (boxJ/ blockSize != j) {
                for (int l = j * blockSize; l < j* blockSize + blockSize; l++) {
                    final var v = get(x,l);
                    if (isEmptyVal(v)) continue;
                    if (v == val) {
                        allowed = false;
                        break;
                    }
                }
            }
        }
        return allowed;
    }

    private boolean isCoordinateValid(int x, int y) {
        return x < boardSize && y < boardSize && x >= 0 && y >= 0;
    }

    protected boolean isValValid(int val) {
        return val >= minVal && val <= maxVal;
    }
    //endregion
}
