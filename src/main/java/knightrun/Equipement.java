package knightrun;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public abstract class Equipement {
	
	public static final int BASE_DAMAGE = 100;
	public static final int BASE_HP = 800;

	private Rarete rarete;
	private int puissance;
	private List<Avantage> avantages;
	private String name;
	
	public abstract void updateMainAttribut();
	
	public abstract EmbedBuilder createField(EmbedBuilder embed);
	
	public Equipement(Rarete rarete, int puissance, String name) {
		this.rarete = rarete;
		this.puissance = puissance;
		this.name = name;
		initAvantages();
	}
	
	public void initAvantages() {
		avantages = new ArrayList<>();
		switch(rarete) {
		case RARE:
			createAvantages(1);
			break;
		case EPIQUE:
			createAvantages(2);
			break;
		case LEGENDAIRE:
			createAvantages(3);
			break;
		default:
			break;
		}
	}
	
	private void createAvantages(int number) {
		for (int i = 0; i < number; i++) {
			avantages.add(Avantage.createRandomAvantage());
		}
	}
	
	public int setMainAttribut(int base) {
		for (int i = 0; i < puissance; i++) {
			base+=base/10;
		}
		return base;
	}
	
	public Rarete getRarete() {
		return rarete;
	}
	
	public int getPuissance() {
		return puissance;
	}
	
	public void setPuissance(int newPuissance) {
		puissance = newPuissance;
		updateMainAttribut();
	}
	
	public List<Avantage> getAvantages(){
		return avantages;
	}
	
	public String getName() {
		return name;
	}
	
}
