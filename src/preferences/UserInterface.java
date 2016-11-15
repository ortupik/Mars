package preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class UserInterface {

	private static Preferences pref;
	
	public UserInterface(){
		pref = Preferences.userNodeForPackage(UserInterface.class);
		pref.put("A", "a jock");
		//pref.put("B", "b lom");
	}
	public static void main(String[] args) {
		new UserInterface();

		System.out.println(pref.get("B",null));
		//pref.remove("B");

	}

}
