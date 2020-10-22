package knightrun.arme;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import knightrun.Avantage;
import knightrun.Equipement;
import knightrun.Rarete;

public abstract class Arme extends Equipement {
	
	private int damage;
	
	public Arme(Rarete rarete, int puissance, String name) {
		super(rarete, puissance, name);
		updateMainAttribut();
	}
	
	public int getDamage() {
		return damage;
	}
	
	@Override
	public void updateMainAttribut() {
		damage = setMainAttribut(BASE_DAMAGE);
	}
	
	@Override
	public EmbedBuilder createField(EmbedBuilder embed) {
		String title = this.getName() + "  |  Puissance : " + this.getPuissance();
		String description = "Dégâts : " + damage + "\n";
		if (!(this.getAvantages().isEmpty())) {		
			for (Avantage avantage : this.getAvantages()) {
				description+=avantage.getAvantage().name() + " : " + avantage.getPourcentage() + "% \n";
			}
		}
		
		embed.addField(title, description);
		
		return embed;
	}

}
