package Lab3.imagemodifier.pluginsystem.plugins;

import java.awt.image.BufferedImage;
import java.io.IOException;

import com.jhlabs.image.GaussianFilter;

import Lab3.imagemodifier.pluginsystem.BaseImagePlugin;

public class GausianBlurImagePlugin extends BaseImagePlugin {

	@Override
	public byte[] transformImage(byte[] imageBytes) {
		try {
			BufferedImage image = toBufferedImage(imageBytes);

			GaussianFilter gaussian = new GaussianFilter(15);
			BufferedImage bluredImage = gaussian.filter(image, null);

			return toBytesImage(bluredImage, "jpg");

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		return "Gausian Blur";
	}
}
