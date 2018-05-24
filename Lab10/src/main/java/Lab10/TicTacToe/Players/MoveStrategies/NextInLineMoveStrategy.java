package Lab10.TicTacToe.Players.MoveStrategies;

public class NextInLineMoveStrategy extends NashornMoveStrategy {

	public NextInLineMoveStrategy() {
		super(NextInLineMoveStrategy.class.getClassLoader().getResource("NextInLineMoveStrategy.js").getPath(), "nextInLineMoveStrategy");
	}

	@Override
	public String getName() {
		return "Next In Line";
	}

}
