package rubis;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

import audio.BotEventAudio;
import knightrun.BotEventKnightRun;

public class Main {
	
	public static void main (String[] args) {
		
		FallbackLoggerConfiguration.setDebug(true);
		FallbackLoggerConfiguration.setTrace(true);
		
		DiscordApi api = new DiscordApiBuilder().setToken(args[0]).setAllIntents().login().join();
		
		init(api);
		
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
			//BotEvent.commandFailed(event);
		});
		
		api.addMessageCreateListener(event -> {
			try {
				BotEventAudio.playMusic(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		api.addMessageCreateListener(event -> {
			BotEventAudio.affichePlaylist(event);
		});
		
		api.addMessageCreateListener(event -> {
			try {
				BotEvent.checkVIP(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		api.addMessageCreateListener(event -> {
			BotEventAudio.addTrackInPlaylist(event);
		});	
		
		api.addMessageCreateListener(event -> {
			BotEventKnightRun.startKnightRun(event);
		});
		
		api.addMessageCreateListener(event -> {
			BotEventKnightRun.afficheKnight(event);
		});
		
	}
	
	private static void init(DiscordApi api) {
		api.updateActivity("Rubis est de retour !");
		
		ListeCommandeRubis.getInstance().initCommande("!status", "Retourne la liste des membres du serveur en fonction de leur statut.");
		ListeCommandeRubis.getInstance().initCommande("!musique", "Rubis joue la musique Youtube passée en URL dans le salon vocal Musique.  IL faut être VIP pour utiliser cette commande. \n Utilisation : !musique url_youtube");
		ListeCommandeRubis.getInstance().initCommande("!checkVIP", "T'accorde le rôle VIP si tu es dans le serveur depuis au moins 7 jours.");
		ListeCommandeRubis.getInstance().initCommande("!commandes", "Retourne la liste des commandes utilisables avec Rubis.");
		ListeCommandeRubis.getInstance().initCommande("!playlist", "Rubis joue la playlist dans salon vocal Musique. Il faut être VIP pour utiliser cette commande.");
		ListeCommandeRubis.getInstance().initCommande("!affPlaylist", "Affiche la playlist de Rubis.");
		ListeCommandeRubis.getInstance().initCommande("!addTrack", "Ajoute une musique à la playlist de Rubis. Il faut être VIP pour utiliser cette commande. \n Utilisation : !addTrack nom_musique auteur_musique url_youtube");
		ListeCommandeRubis.getInstance().initCommande("!startKnightRun", "Commence l'aventure KnightRun.");
		ListeCommandeRubis.getInstance().initCommande("!afficheKnight", "Affiche ton Knight (possible uniquement quand tu as commencé l'aventure KnightRun).");
	}

}
