package knightrun;

import java.util.ArrayList;
import java.util.List;

import org.javacord.api.entity.user.User;

public class AllUserKnight {

	private static final AllUserKnight instance = new AllUserKnight();
	
	private List<UserKnight> listUserKnight;
	
	private AllUserKnight() {
		listUserKnight = new ArrayList<>();
	}
	
	public static AllUserKnight getInstance() {
		return instance;
	}
	
	public List<UserKnight> getListUserKnight(){
		return listUserKnight;
	}
	
	public void addUserKnight(UserKnight userKnight) {
		listUserKnight.add(userKnight);
	}
	
	public UserKnight getSpecificUserKnight(User user) {
		for (UserKnight userKnight : listUserKnight) {
			if (userKnight.getUser().getName().equals(user.getName())) {
				return userKnight;
			}
		}
		return null;
	}
	
	public boolean hasUserKnightAccount(User user) {
		boolean hasUserKnightAccount = false;
		for (UserKnight userKnight : AllUserKnight.getInstance().getListUserKnight()) {
			if (userKnight.getUser().getName().equals(user.getName())) {
				hasUserKnightAccount = true;
			}
		}
		return hasUserKnightAccount;
	}
	
}
