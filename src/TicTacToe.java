import java.util.Arrays;

public class TicTacToe {
  private final String[] board;
  private final int size;
  private final Player player1;
  private final Player player2;

  public TicTacToe(int size, Player player1, Player player2) {
    this.size = size;
    this.board = new String[size * size];
    this.player1 = player1;
    this.player2 = player2;
    Arrays.fill(board, PlayerEnum.NONE.toString());
  }


  private void makeMove(PlayerEnum currentPlayer) {
    Player player;
    switch (currentPlayer) {
      case PLAYER1:
        player = player1;
        break;
      case PLAYER2:
        player = player2;
        break;
      default:
        return;
    }
    boolean isValidMove;
    int move;
    do {
      move = player.getMove(Arrays.copyOf(board, size * size));
      isValidMove = isValidMove(move);
      if (!isValidMove) {
        System.out.println("Invalid move: the move index must be a number in the range " + 0 + "-" +
            (size * size - 1) + " and it mustn't be occupied already");
      }
    } while (!isValidMove);

    board[move] = currentPlayer.toString();
  }


  private boolean isValidMove(int move) {
    return move >= 0 && move < size * size && board[move].equals(PlayerEnum.NONE.toString());
  }


  public void play() {
    int moves = 0;

    PlayerEnum currentPlayer = PlayerEnum.PLAYER1;
    while (moves < size * size && !hasOpponentWon(currentPlayer.opponent())) {
      displayBoard();
      makeMove(currentPlayer);
      moves++;
      currentPlayer = currentPlayer.opponent();
    }

    displayBoard();

    // TODO: Print result
  }


  private void displayBoard() {
    StringBuilder sb = new StringBuilder();

    // TODO: Adapt for boards bigger than 3
    addSeparatorLine(sb);

    String boardPiece;
    for (int i = 0; i < size; i++) {

      sb.append("| ");
      for (int j = 0; j < size; j++) {

        boardPiece = board[i * size + j];
        if (boardPiece.equals(PlayerEnum.NONE.toString())) {
          boardPiece = String.valueOf(i * size + j);
        }

        sb.append(boardPiece);
        sb.append(" | ");
      }
      sb.append("\n");
      addSeparatorLine(sb);
    }

    System.out.println(sb.toString());
  }


  private void addSeparatorLine(StringBuilder sb) {
    for (int i = 0; i < 4 * size + 1; i++) {
      sb.append('-');
    }
    sb.append('\n');
  }


  private boolean hasOpponentWon(PlayerEnum player) {
    boolean isRowFull;
    boolean isColumnFull;

    // Check if rows or columns are full
    for (int i = 0; i < size; i++) {
      isRowFull = true;
      isColumnFull = true;
      for (int j = 0; j < size; j++) {
        if (isRowFull && !board[size * i + j].equals(player.toString())) {
          isRowFull = false;
        }
        if (isColumnFull && !board[size * j + i].equals(player.toString())) {
          isColumnFull = false;
        }
      }
      if (isColumnFull || isRowFull) {
        return true;
      }
    }

    // Check if diagonals are full
    boolean isDiag1Full = true;
    boolean isDiag2Full = true;
    for (int i = 0; i < size; i++) {
      if (isDiag1Full && !board[size * i + i].equals(player.toString())) {
        isDiag1Full = false;
      }
      if (isDiag2Full && !board[size * i + (size - 1 - i)].equals(player.toString())) {
        isDiag2Full = false;
      }
    }

    return isDiag1Full || isDiag2Full;

  }

//  public static void main(String[] args) {
//    Player player = board -> 0;
//    new TicTacToe(3, player, player).displayBoard();
//  }
}
