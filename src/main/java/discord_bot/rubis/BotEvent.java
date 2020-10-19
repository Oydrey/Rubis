package discord_bot.rubis;

import java.util.Collection;
import java.util.regex.*;

import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
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
	
	public static void playMusic(MessageCreateEvent event) throws Exception {
		String[] splitMessage = event.getMessageContent().split(" ");	
		
		if ((splitMessage[0].equals("!musique")) || (splitMessage[0].equals("!playlist"))) {
			String audio;
			if (splitMessage.length == 2) {
				Pattern pattern = Pattern.compile("https?:?/?/www?.youtube?.com?/watch?\\?v?=");
				Matcher matcher = pattern.matcher(splitMessage[1]);
				boolean find = matcher.find();
				if (find) {
					audio = splitMessage[1];
				} else {
					throw new Exception("The URL is not valable");
				}
			} else {
				//TODO playlist
				audio = "";
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
