import java.util.Arrays;

public class TicTacToe {
  private final PlayerEnum[][] board;
  private final int size;
  private final Player player1;
  private final Player player2;

  public TicTacToe(int size, Player player1, Player player2) {
    this.size = size;
    this.board = new PlayerEnum[size][size];
    this.player1 = player1;
    this.player2 = player2;
    for (int i = 0; i < size; i++) {
      Arrays.fill(board[i], PlayerEnum.NONE);
    }
  }

  private void makeMove(PlayerEnum currentPlayer) {
    switch (currentPlayer) {
      case PLAYER1:
        int move = player1.getMove();

    }
  }
}
