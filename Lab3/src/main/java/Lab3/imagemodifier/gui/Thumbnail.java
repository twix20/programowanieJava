package Lab3.imagemodifier.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Thumbnail extends JLabel {
	
	File imageFile;
	byte[] image;

	public Thumbnail(File imageFile) {
		this.imageFile = imageFile;
		
		try {
			initialize();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initialize() throws IOException {
		image = extractImage(getImageFile());
		
		this.setText(getImageFile().getName());
		this.setIcon(getImageIconThumbnail());
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
	
	public Icon getImageIconThumbnail() throws IOException {
		BufferedImage imgOrg = ImageIO.read(new ByteArrayInputStream(getOrginalImageBytes()));
		
		Image newimg = imgOrg.getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		return new ImageIcon(newimg);  // transform it back
	}

}
