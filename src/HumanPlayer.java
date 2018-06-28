import java.util.Scanner;

public class HumanPlayer implements Player {
  final Scanner scanner;

  public HumanPlayer() {
    scanner = new Scanner(System.in);
  }

  @Override
  public int getMove(String[] board) {
    String moveStr;
    do {
      System.out.println("Please enter a valid move: ");
      moveStr = scanner.next();
      try {
        return Integer.parseInt(moveStr.trim());
      } catch (NumberFormatException e) {
        System.out.println("Input was not a number");
      }
    } while (true);
  }
}
