package discord_bot.rubis;

import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class ListeCommandeRubis {

	private static final ListeCommandeRubis instance = new ListeCommandeRubis();
	
	private Map<String, String> listeCommande; 
	
	private ListeCommandeRubis() {
		listeCommande = new HashMap<>();
	}
	
	public static final ListeCommandeRubis getInstance() {
		return instance;
	}
	
	public void addCommande(String commande, String desc) {
		listeCommande.put(commande, desc);
	}
	
	public Map<String, String> getListeCommande() {
		return listeCommande;
	}
	
	public EmbedBuilder createEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setTitle("Liste des commandes de Rubis");
		embed.setDescription("Les commandes de Rubis commencent par ! suivi du nom de la commande");
		
		listeCommande.forEach((k, v) -> {		
			embed.addField(k, v);
		});
		
		embed.setColor(Color.RED);
		embed.setImage(new File("C:/Users/Oydrey/Pictures/rubis.jpg"));
		
		return embed;
	}
	
}
