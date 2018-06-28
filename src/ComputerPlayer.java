import java.util.ArrayList;
import java.util.Arrays;
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

    int move = getWinningMoveThisTurn(validMoves, board, playerPiece, size);
    if (move != -1) {
      return move;
    }

    move = getWinningMoveThisTurn(validMoves, board, getOpponentPiece(playerPiece), size);
    if (move != -1) {
      return move;
    }

    move = getWinningMoveNextTurn(validMoves, board, playerPiece, size);
    if (move != -1) {
      return move;
    }

    move = getWinningMoveNextTurn(validMoves, board, getOpponentPiece(playerPiece), size);
    if (move != -1) {
      return move;
    }

    return -1;
  }

  private int getWinningMoveThisTurn(List<Integer> validMoves, String[] board, String playerPiece, int size) {

    int tileIdx;
    boolean[] isLineFull = new boolean[4];

    for (Integer move : validMoves) {

      Arrays.fill(isLineFull, true);
      isLineFull[2] = isOnDiagonal1(move, size);
      isLineFull[3] = isOnDiagonal2(move, size);

      int row = move / size;
      int col = move % size;

      for (int i = 0; i < size; i++) {
        for (int j = 0; j < isLineFull.length; j++) {
          tileIdx = getTileIdx(j, i, row, col, size);
          if (isLineFull[j] && tileIdx != move && !board[tileIdx].equals(playerPiece)) {
            isLineFull[j] = false;
          }
        }
      }
      for (boolean lineFull : isLineFull) {
        if (lineFull) {
          return move;
        }
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

  private String getOpponentPiece(String playerPiece) {
    if (playerPiece.equals("X")) {
      return "O";
    } else if (playerPiece.equals("O")) {
      return "X";
    }
    return this.playerPiece;
  }


  private int getWinningMoveNextTurn(List<Integer> validMoves, String[] board, String playerPiece, int size) {

    int row;
    int col;

    int[] nEmptyTilesInLine = new int[4];
    boolean[] isLineBlocked = new boolean[4];
    String opponent = getOpponentPiece(playerPiece);

    for (Integer move : validMoves) {

      Arrays.fill(nEmptyTilesInLine, 0);
      Arrays.fill(isLineBlocked, false);
      isLineBlocked[2] = !isOnDiagonal1(move, size);
      isLineBlocked[3] = !isOnDiagonal2(move, size);

      row = move / size;
      col = move % size;

      int tileIdx;
      for (int i = 0; i < size; i++) {
        for (int j = 0; j < 4; j++) {

          if (!isLineBlocked[j]) {

            tileIdx = getTileIdx(j, i, row, col, size);

            if (board[tileIdx].equals(opponent)) {
              isLineBlocked[j] = true;
              nEmptyTilesInLine[j] = 0;
            } else if (!board[tileIdx].equals(playerPiece)) {
              nEmptyTilesInLine[j]++;
            }
          }
        }
      }

      int numWaysToWin = 0;
      for (int i = 0; i < 4; i++) {
        if (nEmptyTilesInLine[i] == size - 2) {
          numWaysToWin++;
        }
      }

      if (numWaysToWin > 1) {
        return move;
      }
    }
    return -1;
  }

  private int getTileIdx(int j, int i, int row, int col, int size) {
    switch (j) {
      case 0:
        return size * row + i;
      case 1:
        return size * i + col;
      case 2:
        return size * i + i;
      case 3:
        return size * i + (size - 1 - i);
      default:
        return -1;
    }
  }
}
