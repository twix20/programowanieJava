package Lab3.imagemodifier.gui;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class ThumbnailsLoaderWorker extends SwingWorker<Integer, String> {
	final List<String> SUPPORTED_IMAGE_EXTENSIONS = Arrays.asList(".jpg");
	
	File directory;
	JPanel panelThumbnails;
	MouseAdapter thumbnailClicked;
	
	  private static void failIfInterrupted() throws InterruptedException {
		    if (Thread.currentThread().isInterrupted()) {
		      throw new InterruptedException("Interrupted while searching files");
		    }
		  }
	
	
	public ThumbnailsLoaderWorker(final File directory, final JPanel panelThumbnails, MouseAdapter thumbnailClicked) {
		this.directory = directory;
		this.panelThumbnails = panelThumbnails;
		this.thumbnailClicked = thumbnailClicked;
	}
	
	@Override
	protected Integer doInBackground() throws Exception {
		
		String dirPath = directory.getAbsolutePath();
		
		publish("Listing all text files under the directory: " + dirPath);
		
		ThumbnailsLoaderWorker.failIfInterrupted();
		File[] allImageFiles = fileFinder(dirPath, SUPPORTED_IMAGE_EXTENSIONS);
		ThumbnailsLoaderWorker.failIfInterrupted();
		
		if(allImageFiles == null || allImageFiles.length == 0) return null;
		
		System.out.println("You selected " + dirPath + " It contains " + allImageFiles.length);
		
		
	    publish("Start");
	    setProgress(0);
		
		panelThumbnails.removeAll();
		int i = 0;
		for(File x : allImageFiles) {
			i++;
			ThumbnailsLoaderWorker.failIfInterrupted();
			
			Thumbnail t = new Thumbnail(x);
			t.addMouseListener(thumbnailClicked);
			
			ThumbnailsLoaderWorker.failIfInterrupted();
			setProgress(100 * i / allImageFiles.length);
			
			panelThumbnails.add(t);
			
			publish("ADDED_NEW_THUMBNAIL");
		}
		
	    publish("Complete");
	    setProgress(100);
		
		return null;
	}
	
	
	public File[] fileFinder(String dirName, final List<String> supportedExtensions) {
		File dir = new File(dirName);

		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return supportedExtensions.stream().anyMatch(x -> filename.endsWith(x));
			}
		});
	}

}
