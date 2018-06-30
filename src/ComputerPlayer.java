import java.util.*;

public class ComputerPlayer implements Player {
  private final String playerPiece;

  public ComputerPlayer(String playerPiece) {
    this.playerPiece = playerPiece;
  }

  @Override
  public int getMove(String[] board) {
    Set<Integer> validMoves = new HashSet<>();
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

//    move = getWinningMoveNextTurn(validMoves, board, getOpponentPiece(playerPiece), size);
//    if (move != -1) {
//      return move;
//    }
    removeLosingMoves(validMoves, board, playerPiece, size);

    return getMostBlockingMove(validMoves, board, playerPiece, size);
  }

  private int getMostBlockingMove(Set<Integer> validMoves, String[] board, String playerPiece, int size) {
    int bestMove = -1;
    int mostLinesBlocked = -1;
    int linesBlocked;
    int row;
    int col;
    int tileIdx;

    boolean[] blockedLines = new boolean[4];

    for (Integer move : validMoves) {

      Arrays.fill(blockedLines, true);
      blockedLines[2] = isOnDiagonal1(move, size);
      blockedLines[3] = isOnDiagonal2(move, size);

      row = move / size;
      col = move % size;

      for (int i = 0; i < size; i++) {
        for (int j = 0; j < blockedLines.length; j++) {
          tileIdx = getTileIdx(j, i, row, col, size);
          if (blockedLines[j] && !board[tileIdx].equals(" ")) {
            blockedLines[j] = false;
          }
        }
      }

      linesBlocked = 0;
      for (boolean  isLineBlocked : blockedLines) {
        if (isLineBlocked) {
          linesBlocked++;
        }
      }
      if (linesBlocked > mostLinesBlocked ||
          (linesBlocked == mostLinesBlocked &&
              distanceToCentre(size, move) < distanceToCentre(size, bestMove))
          ) {
        mostLinesBlocked = linesBlocked;
        bestMove = move;
      }
    }
    return bestMove;
  }

  private int getWinningMoveThisTurn(Set<Integer> validMoves, String[] board, String playerPiece, int size) {

    int tileIdx;
    int row;
    int col;
    boolean[] isLineFull = new boolean[4];

    for (Integer move : validMoves) {

      Arrays.fill(isLineFull, true);
      isLineFull[2] = isOnDiagonal1(move, size);
      isLineFull[3] = isOnDiagonal2(move, size);

      row = move / size;
      col = move % size;

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


  private int getWinningMoveNextTurn(Set<Integer> validMoves, String[] board, String playerPiece, int size) {

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
        if (nEmptyTilesInLine[i] == 2) {
          numWaysToWin++;
        }
      }

      if (numWaysToWin > 1) {
        return move;
      }
    }
    return -1;
  }

  private void removeLosingMoves(Set<Integer> validMoves, String[] board, String playerPiece, int size) {
    Set<Integer> remainingMoves = new HashSet<>(validMoves);
    Set<Integer> nonLosingMoves = new HashSet<>();

    int playerWinningMove;
    int opponentWinningMove;
    for (Integer move : validMoves) {
      remainingMoves.remove(move);
      board[move] = playerPiece;
      playerWinningMove = getWinningMoveThisTurn(remainingMoves, board, playerPiece, size);
      opponentWinningMove = getWinningMoveNextTurn(remainingMoves, board, getOpponentPiece(playerPiece), size);
      if ((playerWinningMove != -1 && opponentWinningMove != playerWinningMove) ||
          opponentWinningMove == -1) {
        nonLosingMoves.add(move);
      }
      board[move] = " ";
      remainingMoves.add(move);
    }
    validMoves.retainAll(nonLosingMoves);
    if (validMoves.isEmpty()) {
      validMoves.addAll(remainingMoves);
    }
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

  private int distanceToCentre(int side, int move) {
    int centre = side / 2;
    int moveCol = move % side;
    int moveRow = move / side;
    return Math.abs(moveRow - centre) + Math.abs(moveCol - centre);
  }
}
