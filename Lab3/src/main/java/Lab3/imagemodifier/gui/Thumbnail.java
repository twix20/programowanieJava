package Lab3.imagemodifier.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Thumbnail extends JLabel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4900196832378830115L;
	File imageFile;
	byte[] image;

	public Thumbnail(File imageFile) throws IOException {
		this.imageFile = imageFile;
		image = extractImage(getImageFile());
		
		this.setText(getImageFile().getName());
		
		try {
			this.setIcon(getImageIconThumbnail(40, 40));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Thumbnail(byte[] image) {
		this.image = image;
		
		try {
			this.setIcon(getImageIconThumbnail(200, 200));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	public byte[] extractImage(File imagePath) throws IOException {
		return Files.readAllBytes(imagePath.toPath());
	}
	
	public File getImageFile() {
		return imageFile;
	}
	
	public byte[] getOrginalImageBytes() {
		return this.image;
	}
	
	public Icon getImageIconThumbnail(int width, int height) throws IOException {
		BufferedImage imgOrg = ImageIO.read(new ByteArrayInputStream(getOrginalImageBytes()));
		
		Image newimg = imgOrg.getScaledInstance(width, height,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);  // transform it back
	}

}
