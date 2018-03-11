package Lab3.imagemodifier.pluginsystem.plugins;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RotateImagePlugin {

	public byte[] transformImage(byte[] imageBytes) {
		System.out.println("before " + imageBytes.length);
		
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			System.out.println(image.getWidth());
			
		    for (int i=0;i<image.getWidth();i++) {
		        for (int j=0;j<image.getHeight()/2;j++)
		        {
		            int tmp = image.getRGB(i, j);
		            image.setRGB(i, j, image.getRGB(i, image.getHeight()-j-1));
		            image.setRGB(i, image.getHeight()-j-1, tmp);
		        }
		    }
		    
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			byte[] imageInByte = baos.toByteArray();
			
			System.out.println("after " + imageInByte.length);
			
			baos.close();
		    
		    return imageInByte;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public String getName() {
		return "RotateImagePlugin";
	}

}
