package discord_bot.rubis;

import java.awt.Color;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class BotEvent {

	public static void afficheCommandesBot(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!commandes")) {
			EmbedBuilder embed = ListeCommandeRubis.getInstance().createEmbed();
			event.getChannel().sendMessage(embed);
		}
	}
	
	public static void afficheFicheEspece(MessageCreateEvent event) {
		String message = event.getMessageContent();
		CommandFicheEspece.getInstance().getCommands().forEach((k, v) -> {		
			if (message.equals(k)) {
				EmbedBuilder embed = v.createEmbed();
				event.getChannel().sendMessage(embed);
			}
		});
	}
	
	public static void answerSalut(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("Salut"))  {
			Message  message = event.getMessage();
			User user = message.getUserAuthor().get();
			String mentionTag = user.getMentionTag();
			event.getChannel().sendMessage("Salut " + mentionTag);
		}
	}
	
	public static void listUserStatus(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!status"))  {				
			Server server = event.getServer().get();
			Collection<User> users = server.getMembers();
			Map<String, String> usersStatus = new HashMap<>();
			for (User user : users) {
				usersStatus.put(user.getName(), user.getStatus().getStatusString());
			}
			EmbedBuilder embed = new EmbedBuilder()
					.setTitle("Liste des membres")
					.setDescription("La liste des membres du serveur en fonction de leur statut")
					.setColor(Color.YELLOW);
			String statusOnline = "";
			String statusDnd = "";
			String statusIdle = "";
			String statusOffline = "";
			
			usersStatus.forEach((k, v) -> {		

			});
						
			embed.addInlineField("En ligne", statusOnline);
			embed.addInlineField("Absent", statusIdle);
			embed.addInlineField("Ne pas déranger", statusDnd);
			embed.addInlineField("Hors ligne", statusOffline);
			
			event.getChannel().sendMessage(embed);
		}
	} 
	
}
