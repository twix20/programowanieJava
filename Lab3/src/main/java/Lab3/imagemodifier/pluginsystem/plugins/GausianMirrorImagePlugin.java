package Lab3.imagemodifier.pluginsystem.plugins;

import Lab3.imagemodifier.pluginsystem.CCLoader;
import Lab3.imagemodifier.pluginsystem.IImagePlugin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.imageio.ImageIO;

import com.jhlabs.image.MirrorFilter;

public class GausianMirrorImagePlugin implements IImagePlugin{

	@Override
	public byte[] transformImage(byte[] imageBytes) {
		
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
			
			MirrorFilter filter = new MirrorFilter();
			
			BufferedImage transformed =  filter.filter(image, null);
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(transformed, "jpg", baos);
			byte[] imageInByte = baos.toByteArray();
			
			Class<?> externalPluginClass = Class.forName("Lab3.imagemodifier.pluginsystem.plugins.GausianBlurImagePlugin");
			Method transformMethod = externalPluginClass.getMethod("transformImage", (Class<?>[]) new Class[] { byte[].class });
			
			Constructor<?> ctor = externalPluginClass.getConstructor();
			Object plugin = ctor.newInstance();
			
			byte[] finalImage = (byte[]) transformMethod.invoke(plugin, imageInByte);
			
			return finalImage;
			
		} catch (IOException | ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "GausianMirrorImagePlugin";
	}
	

}
