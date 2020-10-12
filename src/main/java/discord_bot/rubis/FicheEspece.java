package discord_bot.rubis;

import java.awt.Color;
import java.io.File;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class FicheEspece {
	
	private String title;
	private String description;
	private List<Field> fields;
	private Color color;
	private File image;
	
	public FicheEspece(String title, String description, List<Field> fields, Color color, File image) {
		this.title = title;
		this.description = description;
		this.fields = fields;
		this.color = color;
		this.image = image;
	}
	
	public EmbedBuilder createEmbed() {
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setTitle(title);
		embed.setDescription(description);
		
		for(Field field : fields) {
			embed.addField(field.getTitle(), field.getDescription());
		}
		
		embed.setColor(color);
		embed.setImage(image);
		
		return embed;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setImage(File image) {
		this.image = image;
	}
	
	public void setOneField(int index, String title, String description) {
		
	}

}
