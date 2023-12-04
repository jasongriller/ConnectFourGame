package com.example.connectfour;

import java.util.Random;

public class ConnectFourGame {
    public static final int ROW = 7;
    public static final int COL = 6;
    public static final int EMPTY = 0;
    public static final int BLUE = 1;
    public static final int RED = 2;
    public static final int DISCS = 42;

    private int[][] boardGrid;
    private int player;

    public ConnectFourGame() {
        boardGrid = new int[ROW][COL];
        player = BLUE;
    }

    public void newGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = EMPTY;
            }
        }
        player = BLUE;
    }
    public String getState() {
        StringBuilder boardString = new StringBuilder();
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardString.append(boardGrid[row][col]);
            }
        }

        return boardString.toString();
    }

    // Added method getDisc
    public int getDisc(int row, int col) {
        return boardGrid[row][col];
    }

    // Add method isGameOver
    public boolean isGameOver() {
        return isBoardFull() || isWin();
    }

    // Add method isWin
    public boolean isWin() {
        return checkHorizontal() || checkVertical() || checkDiagonal();
    }

    // Helper method to check if the board is full
    private boolean isBoardFull() {
        for (int col = 0; col < COL; col++) {
            if (boardGrid[0][col] == EMPTY) {
                return false;
            }
        }
        return true;
    }

    // Helper method for checking win conditions
    private boolean checkHorizontal() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL - 3; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row][col + 1] &&
                        disc == boardGrid[row][col + 2] &&
                        disc == boardGrid[row][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method for checking vertical win conditions
    private boolean checkVertical() {
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col] &&
                        disc == boardGrid[row + 2][col] &&
                        disc == boardGrid[row + 3][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method for checking diagonal win conditions
    private boolean checkDiagonal() {
        return checkDiagonalTopLeftToBottomRight() || checkDiagonalTopRightToBottomLeft();
    }

    // Helper method for checking diagonal from top-left to bottom-right
    private boolean checkDiagonalTopLeftToBottomRight() {
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 0; col < COL - 3; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col + 1] &&
                        disc == boardGrid[row + 2][col + 2] &&
                        disc == boardGrid[row + 3][col + 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    // Helper method for checking diagonal from top-right to bottom-left
    private boolean checkDiagonalTopRightToBottomLeft() {
        for (int row = 0; row < ROW - 3; row++) {
            for (int col = 3; col < COL; col++) {
                int disc = boardGrid[row][col];
                if (disc != EMPTY &&
                        disc == boardGrid[row + 1][col - 1] &&
                        disc == boardGrid[row + 2][col - 2] &&
                        disc == boardGrid[row + 3][col - 3]) {
                    return true;
                }
            }
        }
        return false;
    }

    public void setState(String gameState) {
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                boardGrid[row][col] = Character.getNumericValue(gameState.charAt(index));
                index++;
            }
        }
    }

    public void selectDisc(int row, int col) {
        for (int i = ROW - 1; i >= 0; i--) {
            if (boardGrid[i][col] == EMPTY) {
                boardGrid[i][col] = player;
                switchPlayer();
                break;
            }
        }
    }

    private void switchPlayer() {
        player = (player == BLUE) ? RED : BLUE;
    }


}