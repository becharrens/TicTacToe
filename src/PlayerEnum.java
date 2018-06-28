public enum PlayerEnum {
  NONE {
    @Override
    PlayerEnum opponent() {
      return NONE;
    }
  },

  PLAYER1 {
    @Override
    PlayerEnum opponent() {
      return PLAYER2;
    }
  },

  PLAYER2 {
    @Override
    PlayerEnum opponent() {
      return PLAYER1;
    }
  };

  abstract PlayerEnum opponent();
}
