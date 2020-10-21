package discord_bot.rubis;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.*;

import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

public abstract class BotEvent {

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
	
	public static void checkVIP(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!checkVIP")) {
			event.getMessage().delete();
			User user = event.getMessageAuthor().asUser().get();
			List<Role> roles = user.getRoles(event.getServer().get());
			if (roles.size() == 1) {
				Instant instant = user.getJoinedAtTimestamp(event.getServer().get()).get();
				Date date = Date.from(instant);
				Date today = new Date();
				
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				
				long diffInMillies = Math.abs(today.getTime() - date.getTime());
				long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				
				if (diff < 7) {
					event.getChannel().sendMessage("Tu ne peux pas être VIP pour le moment, attend encore " + (10-diff) + " jours.");
				} else {
					event.getChannel().sendMessage("Félicitation " + user.getMentionTag() + " tu es désormais VIP !");
					List<Role> serverRoles = event.getServer().get().getRoles();
					for (Role role : serverRoles) {
						if (role.getName().equals("VIP")) {
							user.addRole(role);
						}
					}
				}
			} else {
				event.getChannel().sendMessage("Tu es déjà VIP.");
			}
		}
	}
	
	public static void mdrr(MessageCreateEvent event) {
		String content = event.getMessageContent();
		char firstChar = content.charAt(0);
		if (firstChar == '!') {
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
	
	private static boolean userIsVIP(MessageCreateEvent event) {
		User user =  event.getMessageAuthor().asUser().get();
		List<Role> roles = user.getRoles(event.getServer().get());
		for (Role role : roles) {
			if (role.getName().equals("VIP")) {
				return true;
			}
		}
		return false;
	}
	
	public static void affichePlaylist(MessageCreateEvent event) {
		if (event.getMessageContent().equalsIgnoreCase("!affPlaylist")) {
			event.getMessage().delete();
			EmbedBuilder embed = PlaylistRubis.getInstance().createEmbed();
			event.getChannel().sendMessage(embed);
		}
	}
	
	public static void addTrackInPlaylist(MessageCreateEvent event) {
		String[] splitContent = event.getMessageContent().split(" ");
		if ((splitContent[0].equalsIgnoreCase("!addTrack")) && (splitContent.length == 4)) {
			event.getMessage().delete();
			PlaylistRubis.getInstance().addTrack(splitContent[1], splitContent[2], splitContent[3]);
		}
	}
	
	public static void playMusic(MessageCreateEvent event) throws Exception {
		if (userIsVIP(event)) {
			String[] splitMessage = event.getMessageContent().split(" ");	
			
			if ((splitMessage[0].equals("!musique")) || (splitMessage[0].equals("!playlist"))) {
				String audio;
				if ((splitMessage.length == 2) && (splitMessage[0].equals("!musique"))) {
					Pattern pattern = Pattern.compile("https?:?/?/www?.youtube?.com?/watch?\\?v?=");
					Matcher matcher = pattern.matcher(splitMessage[1]);
					boolean find = matcher.find();
					if (find) {
						audio = splitMessage[1];
					} else {
						throw new Exception("The URL is not valable");
					}
				} else {
					audio = PlaylistRubis.getInstance().getPlaylist().getName();
				}
				Collection<ServerVoiceChannel> channels = event.getApi().getServerVoiceChannelsByName("Musique");	
				
				for (ServerVoiceChannel channel : channels) {
					channel.connect().thenAccept(audioConnection -> {
						// Create a player manager
						AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
						playerManager.registerSourceManager(new YoutubeAudioSourceManager());
						AudioPlayer player = playerManager.createPlayer();
	
						// Create an audio source and add it to the audio connection's queue
						AudioSource source = new LavaplayerAudioSource(event.getApi(), player);
						audioConnection.setAudioSource(source);
	
						AudioLoadResultHandler audioLoadResultHandler = new AudioLoadResultHandler() {
						    @Override
						    public void trackLoaded(AudioTrack track) {
						        player.playTrack(track);
						    }
	
						    @Override
						    public void playlistLoaded(AudioPlaylist playlist) {
						        for (AudioTrack track : playlist.getTracks()) {
						            player.playTrack(track);
						        }
						    }
						    
							@Override
							public void noMatches() {
								event.getChannel().sendMessage("La musique n'a pas été trouvée.");
								
							}
							@Override
							public void loadFailed(FriendlyException exception) {
								event.getChannel().sendMessage("La musique n'a pas pu être chargée : " + exception.toString());
							}
						};
						
						// You can now use the AudioPlayer like you would normally do with Lavaplayer, e.g.,
						playerManager.loadItem(audio, audioLoadResultHandler);
					});
				}
			}
		}
	}
	
}
