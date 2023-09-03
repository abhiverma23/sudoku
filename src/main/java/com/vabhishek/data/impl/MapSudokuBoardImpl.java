package com.vabhishek.data.impl;

import com.vabhishek.data.SudokuBoard;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MapSudokuBoardImpl extends SudokuBoard {

    private final int[][] board = new int[boardSize][boardSize];
    private final Map<Integer, Coordinates> values = new HashMap<>(18);

    private final boolean validationEnabled;

    public MapSudokuBoardImpl(int blockSize, boolean enableValidations) {
        super(blockSize);
        validationEnabled = enableValidations;
        for (int i = 0; i < boardSize; i++) {
            values.put(i + 1, new Coordinates(blockSize));
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = EMPTY_VAL;
            }
        }
    }

    @Override
    protected void setVal(int x, int y, int val) {
        var oldVal = getVal(x, y);
        if (!isEmptyVal(oldVal)) {
            values.get(oldVal).unset(x, y);
        }
        values.get(val).set(x, y);
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
        var coordinates = values.get(val);
        //Same box search
        allowed &= !coordinates.existsInBlock(x, y);
        //Same horizontal line search
        allowed &= !coordinates.existsInX(x);
        //Same vertical line search
        allowed &= !coordinates.existsInY(y);

        return allowed;
    }

    private static class Coordinates {
        private final Map<Integer, Integer> xy = new HashMap<>(18);
        private final Map<Integer, Integer> yx = new HashMap<>(18);
        private final Set<Integer> block = new HashSet<>(18);

        private final int blockSize;

        public Coordinates(int blockSize) {
            this.blockSize = blockSize;
        }

        public void set(int x, int y) {
            final var blockNumber = getBlockNumber(x, y);
            if (xy.containsKey(x) || yx.containsKey(y) || block.contains(blockNumber)) {
                System.out.println("Invalid request is being processed anyways.");
            }
            xy.put(x, y);
            yx.put(y, x);
            block.add(blockNumber);
        }

        public void unset(int x, int y) {
            xy.remove(x);
            yx.remove(y);
            block.remove(getBlockNumber(x, y));
        }

        public boolean existsInX(int x) {
            return xy.containsKey(x);
        }

        public boolean existsInY(int y) {
            return yx.containsKey(y);
        }

        public boolean existsInBlock(int x, int y) {
            return block.contains(getBlockNumber(x, y));
        }

        private int getBlockNumber(int x, int y) {
            return ((x/blockSize) * blockSize) + (y/blockSize + 1);
        }
    }
}
