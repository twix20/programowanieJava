package lab2.grocerystore.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = -5555813368869363685L;
	
	private Image image;

	public void setImage(Image image) {
		this.image = image;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(image != null)
			g.drawImage(image, 0, 0, this);
	}
}
