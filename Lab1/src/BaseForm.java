import java.awt.SystemColor;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class BaseForm extends JFrame {
	public BaseForm(String frameTitle) {
		this();
		
		this.setTitle(frameTitle);
	}
	
	public BaseForm() {
		super();
		
		this.getContentPane().setBackground(SystemColor.inactiveCaption);
		this.setBackground(SystemColor.inactiveCaption);
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(MainForm.class.getResource("/Lab1/icon.jpg")));
	}
}
