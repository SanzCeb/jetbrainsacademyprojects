package tictactoe;

public enum GameState {
    GAME_NOT_FINISHED,
    DRAW,
    X_WINS,
    O_WINS,
    IMPOSSIBLE;

    @Override
    public String toString() {
        switch (this) {
            case GAME_NOT_FINISHED:
                return "Game not finished";
            case X_WINS:
                return "X wins";
            case O_WINS:
                return "O wins";
            case IMPOSSIBLE:
                return "Impossible";
            default:
                return "Draw";
        }
    }
}
