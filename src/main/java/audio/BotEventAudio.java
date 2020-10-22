package audio;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javacord.api.audio.AudioSource;
import org.javacord.api.entity.channel.ServerVoiceChannel;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import rubis.BotEvent;

public interface BotEventAudio {

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
		if (BotEvent.userIsVIP(event)) {
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
