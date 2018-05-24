package Lab10.TicTacToe.Players.MoveStrategies;

public class RandomMoveStrategy extends NashornMoveStrategy {

	public RandomMoveStrategy() {
		super(RandomMoveStrategy.class.getClassLoader().getResource("RandomMoveStrategyScript.js").getPath(), "randomMoveStrategy");
	}

	@Override
	public String getName() {
		return "Random";
	}
}
