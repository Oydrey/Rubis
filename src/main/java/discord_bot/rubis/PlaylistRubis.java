package discord_bot.rubis;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sedmelluq.discord.lavaplayer.track.BasicAudioPlaylist;

public class PlaylistRubis {

	private static final PlaylistRubis instance = new PlaylistRubis();
	
	private BasicAudioPlaylist playlist;
	private List<AudioTrack> listTrack;
	
	private PlaylistRubis() {
		listTrack = new ArrayList<>();
		playlist = new BasicAudioPlaylist("Playlist de Rubis", listTrack, null, false);
	}
	
	public static final PlaylistRubis getInstance() {
		return instance;
	}
	
	public void addTrack(String title, String author, String url) {
		String identifier = title + author;		
		AudioTrackInfo trackInfo = new AudioTrackInfo(title, author, 0, identifier, false, url);
		AudioTrackRubis audioTrack = new AudioTrackRubis(trackInfo);
		listTrack.add(audioTrack);
		playlist = new BasicAudioPlaylist("Playlist de Rubis", listTrack, null, false);
	}
	
	public void removeTrack(AudioTrack track) {
		listTrack.remove(track);
	}
	
	public BasicAudioPlaylist getPlaylist() {
		return playlist;
	}
	
	public EmbedBuilder createEmbed() {
		EmbedBuilder embed = EmbedBuilderBase.createBaseEmbed();
		
		embed.setTitle("Playlist");
		
		for (AudioTrack track : listTrack) {
			if (track instanceof AudioTrackRubis) {
				embed.addField(track.getInfo().title, track.getInfo().author);
			}
		}
		
		return embed;
	}
	
}
