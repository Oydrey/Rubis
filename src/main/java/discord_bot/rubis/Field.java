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

}
