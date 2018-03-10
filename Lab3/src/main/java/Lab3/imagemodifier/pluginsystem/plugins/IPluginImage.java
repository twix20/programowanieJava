package Lab3.imagemodifier.pluginsystem.plugins;

public interface IPluginImage {
	String getName();
	byte[] transformImage(byte[] imageBytes);
}
