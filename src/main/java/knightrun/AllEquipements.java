package knightrun;

import java.util.ArrayList;
import java.util.List;

public class AllEquipements {
	
	private static final AllEquipements instance = new AllEquipements();
	
	private List<Equipement> listEquipements;
	
	private AllEquipements() {
		listEquipements = new ArrayList<>();
	}
	
	public static AllEquipements getInstance() {
		return instance;
	}
	
	public List<Equipement> getListEquipements(){
		return listEquipements;
	}
	
	public void add(Equipement equipement) {
		listEquipements.add(equipement);
	}

}
