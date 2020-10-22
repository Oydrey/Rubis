package deprecated;

import java.util.HashMap;
import java.util.Map;

public class CommandFicheEspece {
	
	private static final CommandFicheEspece instance = new CommandFicheEspece();
	
	private Map<String, FicheEspece> commands;
	
	private CommandFicheEspece() {
		commands = new HashMap<>();
	}
	
	public static final CommandFicheEspece getInstance() {
		return instance;
	}
	
	public void addCommand(String command, FicheEspece ficheEspece) {
		commands.put(command, ficheEspece);
	}
	
	public Map<String, FicheEspece> getCommands(){
		return commands;
	}

}
