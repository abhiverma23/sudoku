package com.vabhishek.data.impl;

import com.vabhishek.data.SudokuBoard;

import java.util.HashMap;
import java.util.Map;

public class MapSudokuBoardImpl extends SudokuBoard {

    private final int[][] board = new int[boardSize][boardSize];
    private final Map<Integer, Coordinates> values = new HashMap<>(18);

    private final boolean validationEnabled;

    public MapSudokuBoardImpl(int blockSize, boolean enableValidations) {
        super(blockSize);
        validationEnabled = enableValidations;
        for (int i = 0; i < boardSize; i++) {
            values.put(i + 1, new Coordinates());
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = EMPTY_VAL;
            }
        }
    }

    @Override
    protected void setVal(int x, int y, int val) {
        var oldVal = getVal(x,y);
        if (!isEmptyVal(oldVal)) {
            values.get(oldVal).unset(x,y);
        }
        values.get(val).set(x,y);
        board[x][y] = val;
    }

    @Override
    protected int getVal(int x, int y) {
        return board[x][y];
    }

    @Override
    protected boolean isValidationEnabled() {
        return validationEnabled;
    }

    @Override
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
        var coordinates = values.get(val);
        //Same horizontal line search
        allowed &= coordinates.existsInX(x);
        //Same vertical line search
        allowed &= coordinates.existsInY(y);

        return allowed;
    }

    private static class Coordinates {
        private final Map<Integer, Integer> xy = new HashMap<>(18);
        private final Map<Integer, Integer> yx = new HashMap<>(18);

        // TODO: how to represent block

        public void set(int x, int y) {
            xy.put(x, y);
            yx.put(y, x);
        }

        public void unset(int x, int y) {
            xy.remove(x);
            yx.remove(y);
        }

        public boolean existsInX(int x) {
            return xy.containsKey(x);
        }
        public boolean existsInY(int y) {
            return yx.containsKey(y);
        }
    }
}
