package discord_bot.rubis;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Main {
	
	public static void main (String[] args) {
		
		FallbackLoggerConfiguration.setDebug(true);
		FallbackLoggerConfiguration.setTrace(true);
		
		DiscordApi api = new DiscordApiBuilder().setToken(args[0]).login().join();
	
		final TextChannel CHANNEL_GENERAL = api.getTextChannelById("391928375570071555").get();

		List<Field> listField = new ArrayList<>();
		listField.add(new Field("Régime alimentaire :", "Essentiellement frugivore"));
		FicheEspece ficheToucan = new FicheEspece("Toucan", "Le toucan est un oiseau vivant en Amazonie", (ArrayList) listField, Color.GREEN, new File("C:/Users/Oydrey/Pictures/toucan.jpg"), "Affiche la fiche espèce du toucan");
		
		List<Field> listFieldTapir = new ArrayList<>();
		listFieldTapir.add(new Field("Régime alimentaire :", "Herbivore & frugivore"));
		listFieldTapir.add( new Field("Relation avec l'Homme :", "Neutre"));
		FicheEspece ficheTapir = new FicheEspece("Tapir malais", "Le tapir malais est un mammifère vivant en Asie, plus précisement en Malaisie", (ArrayList) listFieldTapir, Color.GREEN, new File("C:/Users/Oydrey/Pictures/tapir.jpg"), "Affiche la fiche espèce du tapir malais");
		
		api.updateActivity("Rubis est de retour !");
		
		api.addMessageCreateListener(event -> {
			BotEvent.afficheFicheEspece(event);
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.answerSalut(event);
		});
		
		api.addMessageCreateListener(event -> {
			BotEvent.afficheCommandesBot(event);
		});
		
		api.addMessageCreateListener(event -> {
			Server server = event.getServer().get();
			Collection<User> users = server.getMembers();
		});
			
	}

}
