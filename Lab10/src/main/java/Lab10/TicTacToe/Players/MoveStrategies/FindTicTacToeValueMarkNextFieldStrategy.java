package Lab10.TicTacToe.Players.MoveStrategies;

public class FindTicTacToeValueMarkNextFieldStrategy extends NashornMoveStrategy {

	public FindTicTacToeValueMarkNextFieldStrategy() {
		super(FindTicTacToeValueMarkNextFieldStrategy.class.getClassLoader().getResource("FindTicTacToeValueMarkNextFieldStrategy.js").getPath(), "findTicTacToeValueMarkNextFieldStrategy");
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Find Next TicTacToe";
	}

}
