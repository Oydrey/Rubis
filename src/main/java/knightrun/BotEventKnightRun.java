package knightrun;

import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

public interface BotEventKnightRun {

	public static void startKnightRun(MessageCreateEvent event) {
		String content = event.getMessageContent();
		if ((content.equalsIgnoreCase("!startKnightRun")) || (content.equalsIgnoreCase("!sKR"))) {
			User user = event.getMessage().getAuthor().asUser().get();
			if (!(AllUserKnight.getInstance().hasUserKnightAccount(user))) {
				UserKnight userKnight = new UserKnight(user);
				AllUserKnight.getInstance().addUserKnight(userKnight);
				event.getChannel().sendMessage("Bienvenue dans l'aventure KnightRun !");
			} else {
				event.getChannel().sendMessage("Tu as déjà commencé l'aventure ;)");
			}
		}
	}
	
	public static void afficheKnight(MessageCreateEvent event) {
		String content = event.getMessageContent();
		if ((content.equalsIgnoreCase("!afficheKnight")) || (content.equalsIgnoreCase("!aK"))) {
			User user = event.getMessage().getAuthor().asUser().get();
			if (AllUserKnight.getInstance().hasUserKnightAccount(user)) {
				UserKnight userKnight = AllUserKnight.getInstance().getSpecificUserKnight(user);
				event.getChannel().sendMessage(userKnight.createEmbed());
			} else {
				event.getChannel().sendMessage("Tu n'as pas encore de compte KnightRun, utilise !startKnightRun pour te créer un comtpe.");
			}
		}
	}
	
}
