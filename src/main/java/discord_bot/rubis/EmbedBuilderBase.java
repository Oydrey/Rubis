package discord_bot.rubis;

import java.awt.Color;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public abstract class EmbedBuilderBase {
	
	public static EmbedBuilder createBaseEmbed() {
		EmbedBuilder embed = new EmbedBuilder()
				.setColor(Color.GREEN)
				.setFooter("Rubis a supprimé automatiquement votre message");
		return embed;
	}

}
