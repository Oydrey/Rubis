package discord_bot.rubis;

import java.util.Collection;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public class BotEvent {

	public static void afficheCommandesBot(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!commandes")) {
			event.getMessage().delete();
			EmbedBuilder embed = ListeCommandeRubis.getInstance().createEmbed();
			event.getChannel().sendMessage(embed);
		}
	}
	
	public static void afficheFicheEspece(MessageCreateEvent event) {
		String message = event.getMessageContent();
		CommandFicheEspece.getInstance().getCommands().forEach((k, v) -> {		
			if (message.equals(k)) {
				event.getMessage().delete();
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
			event.getMessage().delete();
			Server server = event.getServer().get();
			Collection<User> users = server.getMembers();
			String statusOnline = "";
			String statusDnd = "";
			String statusIdle = "";
			String statusOffline = "";
			int statusOnlineTotal = 0;
			int statusDndTotal = 0;
			int statusIdleTotal = 0;
			int statusOfflineTotal = 0;
			for (User user : users) {
				switch (user.getStatus().getStatusString()) {
				case "online":
					statusOnline+="- " + user.getName() + "\n";
					statusOnlineTotal+=1;
					break;
				case "dnd":
					statusDnd+="- " + user.getName() + "\n";
					statusDndTotal+=1;
					break;
				case "idle":
					statusIdle+="- " + user.getName() + "\n";
					statusIdleTotal+=1;
					break;
				case "offline":
					statusOffline+="- " + user.getName() + "\n";
					statusOfflineTotal+=1;
					break;
				default:
					break;		
				}
			}			
			
			statusOnline+="Total : " + statusOnlineTotal;
			statusDnd+="Total : " + statusDndTotal;
			statusIdle+="Total : " + statusIdleTotal;
			statusOffline+="Total : " + statusOfflineTotal;
			
			EmbedBuilder embed = EmbedBuilderBase.createBaseEmbed();
					
			embed.setTitle("Liste des membres")
				 .setDescription("La liste des membres du serveur en fonction de leur statut");
						
			embed.addInlineField("En ligne", statusOnline);
			embed.addInlineField("Absent", statusIdle);
			embed.addInlineField("Ne pas déranger", statusDnd);
			embed.addInlineField("Hors ligne", statusOffline);
			
			event.getChannel().sendMessage(embed);
		}
	} 
	
}
