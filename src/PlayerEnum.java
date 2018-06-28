public enum PlayerEnum {
  NONE {
    @Override
    PlayerEnum opponent() {
      return NONE;
    }

    @Override
    public String toString() {
      return " ";
    }
  },

  PLAYER1 {
    @Override
    PlayerEnum opponent() {
      return PLAYER2;
    }

    @Override
    public String toString() {
      return "X";
    }
  },

  PLAYER2 {
    @Override
    PlayerEnum opponent() {
      return PLAYER1;
    }

    @Override
    public String toString() {
      return "O";
    }
  };

  abstract PlayerEnum opponent();
}
