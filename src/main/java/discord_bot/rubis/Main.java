package discord_bot.rubis;

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.util.logging.FallbackLoggerConfiguration;

public class Main {
	
	public static void main (String[] args) throws URISyntaxException, MalformedURLException {
		
		FallbackLoggerConfiguration.setDebug(true);
		FallbackLoggerConfiguration.setTrace(true);
		
		DiscordApi api = new DiscordApiBuilder().setToken(args[0]).login().join();
	
		final TextChannel CHANNEL_GENERAL = api.getTextChannelById("391928375570071555").get();

		List<Field> listField = new ArrayList<>();
		listField.add(new Field("Régime alimentaire :", "Essentiellement frugivore"));
		FicheEspece ficheToucan = new FicheEspece("Toucan", "Le toucan est un oiseau vivant en Amazonie", (ArrayList) listField, Color.GREEN, new File("C:/Users/Oydrey/Pictures/toucan.jpg"));
		
		EmbedBuilder commandes = new EmbedBuilder()
				.setTitle("Commandes")
				.setDescription("Les commandes de Rubis.")
				.addField("!toucan : ", "Fiche de l'espèce : toucan")
				.addField("Salut", "Rubis te répond Salut @tonPseudo")
				.addField("!commandes", "Donne la liste de toutes les commandes")
				.setColor(Color.RED)
				.setImage(new File("C:/Users/Oydrey/Desktop/bot_discord/Rubis.jpg"));
		
		api.updateActivity("Rubis est de retour !");
		
		api.addMessageCreateListener(event -> {
			String message = event.getMessageContent();
			CommandFicheEspece.getInstance().getCommands().forEach((k, v) -> {		
				if (message.equals(k)) {
					EmbedBuilder embed = v.createEmbed();
					event.getChannel().sendMessage(embed);
				}
			});
			
		});
		
		api.addMessageCreateListener(event -> {
			if (event.getMessageContent().equalsIgnoreCase("Salut"))  {
				Message  message = event.getMessage();
				User user = message.getUserAuthor().get();
				String mentionTag = user.getMentionTag();
				System.out.println(mentionTag);
				event.getChannel().sendMessage("Salut " + mentionTag);
			}
		});
		
		api.addMessageCreateListener(event -> {
			if (event.getMessageContent().equalsIgnoreCase("!commandes")) {
				event.getChannel().sendMessage(commandes);
			}
		});
		
		api.addMessageCreateListener(event -> {
			String contentMessage = event.getMessageContent();
			String[] splitMessage = contentMessage.split(" ");
			if (splitMessage[0].equals("!musique")) {
				
			}
		});
			
	}

}
