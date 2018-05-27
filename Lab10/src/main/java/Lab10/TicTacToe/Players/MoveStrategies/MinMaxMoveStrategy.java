package Lab10.TicTacToe.Players.MoveStrategies;

public class MinMaxMoveStrategy extends NashornMoveStrategy {

	public MinMaxMoveStrategy() {
		super(MinMaxMoveStrategy.class.getClassLoader().getResource("MiniMaxMoveStratgy.js").getPath(), "minimaxMoveStrategy");
	}

	@Override
	public String getName() {
		return "MinMax";
	}

}
