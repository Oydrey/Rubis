package discord_bot.rubis;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class FicheEspece {
	
	private String title;
	private String description;
	private List<Field> fields;
	private Color color;
	private File image;
	private String descCommand;
	
	public FicheEspece(String title, String description, ArrayList<Field> fields, Color color, File image, String descCommand) {
		this.title = title;
		this.description = description;
		this.fields = fields;
		this.color = color;
		this.image = image;
		this.descCommand = descCommand;
		createCommand();
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
		fields.get(index).setTitle(title);
		fields.get(index).setDescription(description);
	}
	
	private void createCommand() {
		String[] testTitle = title.toLowerCase().split(" ");
		String command = "!";
		for(String word : testTitle) {
			command+=word;
		}
		CommandFicheEspece.getInstance().addCommand(command, this);
		ListeCommandeRubis.getInstance().addCommande(command, descCommand);
	}

}
