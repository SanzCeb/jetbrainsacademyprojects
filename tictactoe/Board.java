package tictactoe;

import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.stream.IntStream;

class Board {
    private char[][] board;

    Board(int dimension, char[] gameState) {
        this.board = new char[dimension][dimension];
        var cellsIterator = IntStream.range(0, gameState.length).iterator();
        while (cellsIterator.hasNext()) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board[i][j] = gameState[cellsIterator.next()];
                }
            }
        }
    }

    void printBoard() {
        System.out.println("---------");
        System.out.printf("| %c %c %c |%n", board[0][0], board[0][1], board[0][2]);
        System.out.printf("| %c %c %c |%n", board[1][0], board[1][1], board[1][2]);
        System.out.printf("| %c %c %c |%n", board[2][0], board[2][1], board[2][2]);
        System.out.println("---------");
    }

    GameState getGameState() {
        GameState gameState;
        if (gameImpossible()) {
            gameState = GameState.IMPOSSIBLE;
        } else if (playerWins('X')) {
            gameState = GameState.X_WINS;
        } else if (playerWins('O')) {
            gameState = GameState.O_WINS;
        } else if (emptyCells()) {
            gameState = GameState.GAME_NOT_FINISHED;
        } else {
            gameState = GameState.DRAW;
        }
        return gameState;
    }

    private boolean emptyCells() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++){
                if (board[i][j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean playerWins(char player) {
        var playerWinsLeftDiagonal = true;
        var playerWinsRightDiagonal = true;

        for (int i = 0; i < board.length; i++) {
            var playerWinsRow = true;
            var playerWinsColumn = true;
            for (int j = 0; j < board.length; j++) {
                playerWinsColumn = playerWinsColumn && (board[j][i] == player);
                playerWinsRow = playerWinsRow && (board[i][j] == player);
            }
            if (playerWinsColumn || playerWinsRow) {
                return true;
            }
            playerWinsLeftDiagonal = playerWinsLeftDiagonal && (board[i][i] == player);
            playerWinsRightDiagonal = playerWinsRightDiagonal && (board[i][board.length - i - 1] == player);
        }

        return playerWinsLeftDiagonal || playerWinsRightDiagonal;
    }

    private boolean gameImpossible() {
        var numOs = 0;
        var numXs = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if ((board[i][j] == 'X')) {
                    numXs++;
                } else if ((board)[i][j] == 'O'){
                    numOs++;
                }
            }
        }
        return Math.abs(numOs - numXs) > 1 || (playerWins('X') && playerWins('O'));
    }
}
