package knightrun.armure;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import knightrun.Avantage;
import knightrun.Equipement;
import knightrun.Rarete;

public abstract class Armure extends Equipement {
	
	private int hp;
	
	public Armure(Rarete rarete, int puissance, String name) {
		super(rarete, puissance, name);
		updateMainAttribut();
	}
	
	public int getHp() {
		return hp;
	}

	@Override
	public void updateMainAttribut() {
		hp = setMainAttribut(BASE_HP);
	}
	
	@Override
	public EmbedBuilder createField(EmbedBuilder embed) {
		String title = this.getName() + "  |  Puissance : " + this.getPuissance();
		String description = "HP : " + hp + "\n";
		if (!(this.getAvantages().isEmpty())) {		
			for (Avantage avantage : this.getAvantages()) {
				description+=avantage.getAvantage().name() + " : " + avantage.getPourcentage() + "% \n";
			}
		}
		
		embed.addField(title, description);
		
		return embed;
	}

}
