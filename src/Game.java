import java.util.Scanner;

public class Game {
  public static void main(String[] args) {
    System.out.println("Welcome to the TicTacToe program");
    int size = getBoardSize();
    char player1Human = getYesNoAnswer("Will player 1 be a human? (y/n)");
    char player2Human = getYesNoAnswer("Will player 2 be a human? (y/n)");

    TicTacToe ticTacToeGame = new TicTacToe(size, getPlayer(player1Human,
        PlayerEnum.PLAYER1.toString()), getPlayer(player2Human,
        PlayerEnum.PLAYER2.toString()));

    ticTacToeGame.play();

  }

  private static Player getPlayer(char playerHuman, String playerPiece) {
    switch (playerHuman) {
      case 'y':
        return new HumanPlayer();
      case 'n':
        return new ComputerPlayer(playerPiece);
      default:
        return null;
    }
  }

  private static char getYesNoAnswer(String question) {
    Scanner scanner = new Scanner(System.in);
    String answer;
    char yesNo;
    do {
      System.out.println(question);
      answer = scanner.nextLine().trim();
      if (answer.length() > 0) {
        yesNo = answer.charAt(0);
        if (yesNo == 'y' || yesNo == 'n') {
          return yesNo;
        }
      }
      System.out.println("Invalid input: answer must be 'y' or 'n'");
    } while (true);
  }

  private static int getBoardSize() {
    Scanner scanner = new Scanner(System.in);
    String moveStr;
    do {
      System.out.println("How big would you like the board to be (minimum 3)");
      moveStr = scanner.next();
      try {
        int size = Integer.parseInt(moveStr.trim());
        if (size < 3) {
          System.out.println("Board size must be at least 3!");
          continue;
        }
        return size;
      } catch (NumberFormatException e) {
        System.out.println("Input was not a number");
      }
    } while (true);
  }
}
