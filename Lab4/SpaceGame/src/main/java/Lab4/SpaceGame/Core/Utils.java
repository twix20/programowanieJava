package Lab4.SpaceGame.Core;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

public class Utils {
	
	public static String Env = "default";

	public static void log(String msg) {
		System.out.println(Env + "> " + msg);
	}
	
	public static void displayError(String msg) {
		
		JOptionPane optionPane = new JOptionPane(msg, JOptionPane.ERROR_MESSAGE);    
		JDialog dialog = optionPane.createDialog("Failure");
		dialog.setAlwaysOnTop(true);
		dialog.setVisible(true);
	}
}
