package knightrun;

import knightrun.arme.Arme;
import knightrun.armure.Armure;
import knightrun.bottes.Bottes;
import knightrun.cape.Cape;
import knightrun.casque.Casque;
import knightrun.gants.Gants;

public class Knight {
	
	private static final double BASE_KNIGHT_VITESSE_ATTAQUE = 1.50;
	
	private Arme arme;
	private Armure armure;
	private Bottes bottes;
	private Cape cape;
	private Casque casque;
	private Gants gants;
	private int puissance;
	private int dps;
	private int hp;
	private double vitesseAttaque;
	
	public Knight(Arme arme, Armure armure, Bottes bottes, Cape cape, Casque casque, Gants gants) {
		super();
		this.arme = arme;
		this.armure = armure;
		this.bottes = bottes;
		this.cape = cape;
		this.casque = casque;
		this.gants = gants;
		vitesseAttaque = BASE_KNIGHT_VITESSE_ATTAQUE;
		updateDps();
		updateHp();
		updatePuissance();
	}
	
	public void updatePuissance() {
		puissance = (arme.getPuissance()
				+ armure.getPuissance()
				+ bottes.getPuissance()
				+ cape.getPuissance()
				+ casque.getPuissance()
				+ gants.getPuissance()) / 6;
	}
	
	public void updateDps() {
		int damageTot = arme.getDamage() + cape.getDamage();
		dps = (int) (damageTot / vitesseAttaque);
	}
	
	public void updateHp() {
		hp = armure.getHp() + casque.getHp() + bottes.getHp() + gants.getHp();
	}
	
	public void updateVitesseAttaque() {
		
	}
	public int getDps() {
		return dps;
	}
	
	public int getHp() {
		return hp;
	}
	
	public int getPuissance() {
		return puissance;
	}
	
	public Arme getArme() {
		return arme;
	}
	public void setArme(Arme arme) {
		this.arme = arme;
		updatePuissance();
		updateDps();
	}
	public Armure getArmure() {
		return armure;
	}
	public void setArmure(Armure armure) {
		this.armure = armure;
		updatePuissance();
		updateHp();
	}
	public Bottes getBottes() {
		return bottes;
	}
	public void setBottes(Bottes bottes) {
		this.bottes = bottes;
		updatePuissance();
		updateHp();
	}
	public Cape getCape() {
		return cape;
	}
	public void setCape(Cape cape) {
		this.cape = cape;
		updatePuissance();
		updateDps();
	}
	public Casque getCasque() {
		return casque;
	}
	public void setCasque(Casque casque) {
		this.casque = casque;
		updatePuissance();
		updateHp();
	}
	public Gants getGants() {
		return gants;
	}
	public void setGants(Gants gants) {
		this.gants = gants;
		updatePuissance();
		updateHp();
	}

}
