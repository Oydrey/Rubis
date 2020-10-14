package discord_bot.rubis;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
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
	
}
