import java.util.ArrayList;
import java.util.List;

public class ComputerPlayer implements Player {
  private final String playerPiece;

  public ComputerPlayer(String playerPiece) {
    this.playerPiece = playerPiece;
  }

  @Override
  public int getMove(String[] board) {
    List<Integer> validMoves = new ArrayList<>();
    for (int i = 0; i < board.length; i++) {
      if (board[i].equals(" ")) {
        validMoves.add(i);
      }
    }

    int size = (int) Math.sqrt(board.length);

    int move = getWinningMoveThisTurn(validMoves, board, size);
    if (move != -1) {
      return move;
    }

    return -1;
  }

  private int getWinningMoveThisTurn(List<Integer> validMoves, String[] board, int size) {
    boolean isRowFull;
    boolean isColumnFull;
    boolean isDiag1Full;
    boolean isDiag2Full;
    for (Integer move : validMoves) {
      isRowFull = true;
      isColumnFull = true;
      isDiag1Full = isOnDiagonal1(move, size);
      isDiag2Full = isOnDiagonal2(move, size);

      int row = move / size;
      int col = move % size;

      for (int i = 0; i < size; i++) {
        if (isRowFull && size * row + i != move && !board[size * row + i].equals(playerPiece)) {
          isRowFull = false;
        }
        if (isColumnFull && size * i + col != move && !board[size * i + col].equals(playerPiece)) {
          isColumnFull = false;
        }
        if (isDiag1Full && size * i + i != move && !board[size * i + i].equals(playerPiece)) {
          isDiag1Full = false;
        }
        if (isDiag2Full && size * i + (size - 1 - i) != move && !board[size * i + (size - 1 - i)].equals(playerPiece)) {
          isDiag2Full = false;
        }
      }
      if (isRowFull || isColumnFull || isDiag1Full || isDiag2Full) {
        return move;
      }
    }
    return -1;
  }

  private boolean isOnDiagonal1(int tile, int size) {
    int row = tile / size;
    int col = tile % size;
    return row == col;
  }

  private boolean isOnDiagonal2(int tile, int size) {
    int row = tile / size;
    int col = tile % size;
    return row + col == size - 1;
  }

  private String getOpponentPiece() {
    if (playerPiece.equals("X")) {
      return "O";
    } else if (playerPiece.equals("O")) {
      return "X";
    }
    return playerPiece;
  }
}
