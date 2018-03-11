package Lab3.imagemodifier.pluginsystem;

public interface IImagePlugin {
	byte[] transformImage(byte[] imageBytes);
	String getName();
}
