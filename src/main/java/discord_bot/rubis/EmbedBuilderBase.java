package discord_bot.rubis;

import java.awt.Color;
import java.io.File;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public abstract class EmbedBuilderBase {
	
	public static EmbedBuilder createBaseEmbed() {
		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.GREEN)
				.setFooter("Rubis a supprimé automatiquement votre message")
				.setThumbnail(new File("C:/Users/Oydrey/Pictures/Rubis.jpg"));
		return embed;
	}

}
