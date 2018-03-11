package Lab3.imagemodifier.pluginsystem.plugins;

import java.awt.image.BufferedImage;
import java.io.IOException;

import Lab3.imagemodifier.pluginsystem.BaseImagePlugin;

public class RotateImagePlugin extends BaseImagePlugin {

	@Override
	public byte[] transformImage(byte[] imageBytes) {

		try {
			BufferedImage image = toBufferedImage(imageBytes);

			for (int i = 0; i < image.getWidth(); i++) {
				for (int j = 0; j < image.getHeight() / 2; j++) {
					int tmp = image.getRGB(i, j);
					image.setRGB(i, j, image.getRGB(i, image.getHeight() - j - 1));
					image.setRGB(i, image.getHeight() - j - 1, tmp);
				}
			}
			
			return toBytesImage(image, "jpg");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		return "Rotate Image";
	}

}
