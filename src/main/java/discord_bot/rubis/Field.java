package discord_bot.rubis;

public class Field {
	
	private String title;
	private String description;
	
	public Field(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public void setDescription(String newDescription) {
		description = newDescription;
	}

}
