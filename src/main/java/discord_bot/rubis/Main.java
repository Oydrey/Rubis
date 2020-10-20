package discord_bot.rubis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Main {
	
	public static void main (String[] args) {
		
		FallbackLoggerConfiguration.setDebug(true);
		FallbackLoggerConfiguration.setTrace(true);
		
		DiscordApi api = new DiscordApiBuilder().setToken(args[0]).setAllIntents().login().join();
		
		final TextChannel CHANNEL_GENERAL = api.getTextChannelById("391928375570071555").get();

		List<Field> listField = new ArrayList<>();
		listField.add(new Field("Régime alimentaire :", "Essentiellement frugivore"));
		FicheEspece ficheToucan = new FicheEspece("Toucan", "Le toucan est un oiseau vivant en Amazonie", (ArrayList) listField, new File("C:/Users/Oydrey/Pictures/toucan.jpg"), "Affiche la fiche espèce du toucan");
		
		List<Field> listFieldTapir = new ArrayList<>();
		listFieldTapir.add(new Field("Régime alimentaire :", "Herbivore & frugivore"));
		listFieldTapir.add( new Field("Relation avec l'Homme :", "Neutre"));
		FicheEspece ficheTapir = new FicheEspece("Tapir malais", "Le tapir malais est un mammifère vivant en Asie, plus précisement en Malaisie", (ArrayList) listFieldTapir, new File("C:/Users/Oydrey/Pictures/tapir.jpg"), "Affiche la fiche espèce du tapir malais");
		
		ListeCommandeRubis.getInstance().initCommande("!status", "Retourne la liste des membres du serveur en fonction de leur statut.");
		ListeCommandeRubis.getInstance().initCommande("!musique url_youtube", "Rubis joue la musique Youtube passée en URL dans le salon vocal Musique? IL faut être VIP pour utiliser cette commande.");
		ListeCommandeRubis.getInstance().initCommande("!checkVIP", "T'accorde le rôle VIP si tu es dans le serveur depuis au moins 7 jours.");
		
		api.updateActivity("Rubis est de retour !");
		
		api.addMessageCreateListener(event -> {
			BotEvent.afficheFicheEspece(event);
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.afficheCommandesBot(event);
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.listUserStatus(event);			
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.mdrr(event);
		});
		
		api.addMessageCreateListener(event -> {
			try {
				BotEvent.playMusic(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.checkVIP(event);
		});
			
	}

}
