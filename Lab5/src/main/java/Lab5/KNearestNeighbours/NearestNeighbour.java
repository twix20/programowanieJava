package Lab5.KNearestNeighbours;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import Lab5.GenClassificator.Entities.Flagella;
import Lab5.GenClassificator.Entities.Genotype;
import Lab5.GenClassificator.Entities.Toughness;

public class NearestNeighbour {

	List<Flagella> flagellas;
	List<Toughness> toughnesses;

	public NearestNeighbour(List<Flagella> flagellas, List<Toughness>  toughnesses) {
		this.flagellas = flagellas;
		this.toughnesses = toughnesses;
	}

	public String classifyGenotype(Genotype genotype) {
		int flagella = classifyFlagella(genotype);
		char toughness = classifyToughness(genotype);

		return String.format("%d%s", flagella, toughness);
	}

	private int classifyFlagella(Genotype genotype) {

		DistanceResult<Flagella> r = euclideanDistanceResult(
										genotype.getAlpha(), 
										genotype.getBeta(), 
										flagellas,
										(Flagella f) -> f.getAlpha(), 
										(Flagella f) -> f.getBeta()
									);

		return r.getData().getNumber();
	}

	private char classifyToughness(Genotype genotype) {

		DistanceResult<Toughness> r = euclideanDistanceResult(
											genotype.getBeta(), 
											genotype.getGamma(), 
											toughnesses,
											(Toughness g) -> g.getBeta(), 
											(Toughness g) -> g.getGamma()
										);

		return r.getData().getRank();
	}

	private <T, TR extends Integer> DistanceResult<T> euclideanDistanceResult(TR p, TR q, List<T> flagellas,
			Function<T, TR> getP, Function<T, TR> getQ) {

		List<DistanceResult<T>> results = new ArrayList<>();
		for (T f : flagellas) {

			double dist = Math.sqrt(Math.pow(getP.apply(f) - p, 2) + Math.pow(getQ.apply(f) - q, 2));
			results.add(new DistanceResult<T>(f, dist));
		}

		results.sort((r1, r2) -> {
			return r1.getDistance() > r2.getDistance() ? 1 : -1;
		});

		return results.get(0);
	}

}

class DistanceResult<T> {

	private T data;
	private double distance;

	public DistanceResult(T data, double distance) {
		this.data = data;
		this.distance = distance;
	}

	public boolean compareTo(DistanceResult o) {
		return distance > o.distance;
	}

	public T getData() {
		return data;
	}

	public double getDistance() {
		return this.distance;
	}

}
