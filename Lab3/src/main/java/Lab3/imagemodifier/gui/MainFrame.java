package Lab3.imagemodifier.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class MainFrame extends JFrame {
	
	Thumbnail choosenThumbnailToModify;

	FileTree panelFileTree;
	JPanel panelThumbnails;
	JPanel panelDirictories;
	
	final List<String> SUPPORTED_IMAGE_EXTENSIONS = Arrays.asList(".jpg");
	private JScrollPane scrollPane;

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();

		addEvents();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setBounds(100, 100, 742, 389);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 0, 726, 350);
		getContentPane().add(tabbedPane);

		panelDirictories = new JPanel();
		tabbedPane.addTab("Dirictories", null, panelDirictories, null);
		panelDirictories.setLayout(null);

		panelFileTree = new FileTree(new File("src"));
		panelFileTree.setBounds(10, 27, 302, 263);
		panelDirictories.add(panelFileTree);

		JLabel lblChooseDir = new JLabel("Choose directory");
		lblChooseDir.setBounds(10, 11, 173, 14);
		panelDirictories.add(lblChooseDir);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(337, 25, 358, 265);
		panelDirictories.add(scrollPane);
		
		panelThumbnails = new JPanel();
		scrollPane.setViewportView(panelThumbnails);
		panelThumbnails.setLayout(new BoxLayout(panelThumbnails, BoxLayout.Y_AXIS));

		JPanel panelImageModifier = new JPanel();
		tabbedPane.addTab("Modify Image", null, panelImageModifier, null);
		panelImageModifier.setLayout(null);
	}

	public void addEvents() {
		this.panelFileTree.addTreeItemSelected(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

				
				File[] allImageFiles = fileFinder(node.toString(), SUPPORTED_IMAGE_EXTENSIONS);
				
				if(allImageFiles == null || allImageFiles.length == 0) return;
				
				System.out.println("You selected " + node + " It contains " + allImageFiles.length);
				
				//-----TO SWING WORKER
				//panelThumbnails.removeAll();
				Arrays.asList(allImageFiles).stream().forEach(x -> {
					Thumbnail t = new Thumbnail(x);
					t.addMouseListener(new MouseAdapter() {
						public void mouseClicked(MouseEvent e) {
							Thumbnail clickedThumbnail = (Thumbnail)e.getSource();
							
							if(choosenThumbnailToModify != null)
								choosenThumbnailToModify.setForeground(Color.BLACK);
							
							clickedThumbnail.setForeground(Color.GREEN);
							
							choosenThumbnailToModify = clickedThumbnail;
						}
					});
					
					panelThumbnails.add(t);
					
					validate();
					repaint();
				});
				//------------------------
			}
		});
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
