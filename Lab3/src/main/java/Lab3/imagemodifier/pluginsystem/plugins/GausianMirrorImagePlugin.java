package Lab3.imagemodifier.pluginsystem.plugins;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.jhlabs.image.MirrorFilter;

import Lab3.imagemodifier.pluginsystem.BaseImagePlugin;

public class GausianMirrorImagePlugin extends BaseImagePlugin {

	@Override
	public byte[] transformImage(byte[] imageBytes) {

		try {
			BufferedImage image = toBufferedImage(imageBytes);

			BufferedImage transformed = new MirrorFilter().filter(image, null);
			byte[] imageInByte = toBytesImage(transformed, "jpg");

			Class<?> externalPluginClass = Class
					.forName("Lab3.imagemodifier.pluginsystem.plugins.GausianBlurImagePlugin");
			Method transformMethod = externalPluginClass.getMethod("transformImage",
					(Class<?>[]) new Class[] { byte[].class });

			Constructor<?> ctor = externalPluginClass.getConstructor();
			Object plugin = ctor.newInstance();

			return (byte[]) transformMethod.invoke(plugin, imageInByte);

		} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException
				| InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Gausian Mirror";
	}

}
