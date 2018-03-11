package Lab3.imagemodifier.pluginsystem.plugins;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jhlabs.image.GaussianFilter;

import Lab3.imagemodifier.pluginsystem.IImagePlugin;

public class GausianBlurImagePlugin implements IImagePlugin{

	@Override
	public byte[] transformImage(byte[] imageBytes) {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			GaussianFilter gaussian = new GaussianFilter(15);		
			BufferedImage bluredImage = gaussian.filter(image, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bluredImage, "jpg", baos);
			byte[] imageInByte = baos.toByteArray();

			baos.close();

			return imageInByte;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public String getName() {
		return "Gausian Blur";
	}
}
	
	
