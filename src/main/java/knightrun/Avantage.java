package knightrun;

import java.util.Random;

public class Avantage {

	private AvantageList avantageName;
	private int pourcentage;
	
	public Avantage(AvantageList avantage) {
		this.avantageName = avantage;
		pourcentage = initPourcentage();
	}
	
	private int initPourcentage() {
		switch (avantageName) {
		case VITESSE_ATTAQUE:
			return getRandomNumberInRange(10, 25);
		case ARMURE:
			return getRandomNumberInRange(5, 15);
		case CHANCE_CRITIQUE:
			return getRandomNumberInRange(5, 10);
		case DEGAT_CRITIQUE:
			return getRandomNumberInRange(50, 100);
		case DEGAT_ARME:
			return getRandomNumberInRange(15, 25);
		case CHANCE_ESQUIVE:
			return getRandomNumberInRange(5, 10);
		case PIECE_SUPP:
			return getRandomNumberInRange(10, 20);
		case CHANCE_BLOQUER:
			return getRandomNumberInRange(5, 10);
		default:
			return 0;
		}
	}
	
	public static Avantage createRandomAvantage() {
		int index = getRandomNumberInRange(0, AvantageList.values().length - 1);
		return new Avantage(AvantageList.values()[index]);
	}
	
	private static int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("Max must be greater than min");
		}

		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	public AvantageList getAvantage() {
		return avantageName;
	}
	
	public int getPourcentage() {
		return pourcentage;
	}
	
}
