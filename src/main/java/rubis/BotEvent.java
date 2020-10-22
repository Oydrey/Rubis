package rubis;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import deprecated.CommandFicheEspece;

public interface BotEvent {
	
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
	
	public static void checkVIP(MessageCreateEvent event) throws Exception {
		if (event.getMessageContent().equalsIgnoreCase("!checkVIP")) {
			event.getMessage().delete();
			User user = event.getMessageAuthor().asUser().get();
			if (!userIsVIP(event)) {
				Instant instant = user.getJoinedAtTimestamp(event.getServer().get()).get();
				Date date = Date.from(instant);
				Date today = new Date();
				
				long diffInMillies = Math.abs(today.getTime() - date.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				
				if (diff < 7) {
					event.getChannel().sendMessage("Tu ne peux pas être VIP pour le moment, attend encore " + (10-diff) + " jours.");
				} else {
					event.getChannel().sendMessage("Félicitation " + user.getMentionTag() + " tu es désormais VIP !");
					user.addRole(getSpecificRoleInServer(event.getServer().get(), "VIP"));
				}
			} else {
				event.getChannel().sendMessage("Tu es déjà VIP.");
			}
		}
	}
	
	public static void commandFailed(MessageCreateEvent event) {
		String content = event.getMessageContent();
		if (content.startsWith("!")) {
			String[] contentSplit = content.split(" ");
			if (!(ListeCommandeRubis.getInstance().getListeCommande().containsKey(contentSplit[0]))) {
				String message = contentSplit[0].substring(1);
				User user = event.getMessageAuthor().asUser().get();
				event.getChannel().sendMessage("Nan c'est toi le " + message + " " + user.getMentionTag() + " !");
			}
		}	
	}
	
	public static void listUserStatus(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!status"))  {
			event.getMessage().delete();
			Server server = event.getServer().get();
			Collection<User> users = server.getMembers();
			final String total = "Total : ";
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
			
			statusOnline+=total + statusOnlineTotal;
			statusDnd+=total + statusDndTotal;
			statusIdle+=total + statusIdleTotal;
			statusOffline+=total + statusOfflineTotal;
			
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
	
	public static boolean userIsVIP(MessageCreateEvent event) {
		User user =  event.getMessageAuthor().asUser().get();
		List<Role> roles = user.getRoles(event.getServer().get());
		for (Role role : roles) {
			if (role.getName().equals("VIP")) {
				return true;
			}
		}
		return false;
	}
	
	public static Role getSpecificRoleInServer(Server server, String roleName) throws Exception {
		List<Role> serverRoles = server.getRoles();
		for (Role role : serverRoles) {
			if (role.getName().equals(roleName)) {
				return role;
			}
		}
		throw new Exception("The searching role doesn't exist");
	}
	
}
