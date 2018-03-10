package Lab3.imagemodifier.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;

public class MainFrame extends JFrame {
	ThumbnailsLoaderWorker thumbnailWorker;
	
	Thumbnail choosenThumbnailToModify;

	FileTree panelFileTree;
	JPanel panelThumbnails;
	JPanel panelDirictories;
	JProgressBar progressBarThumbnailsLoading;
	
	MouseAdapter thumbnailClickedAdapter;
	
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
		panelFileTree.setBounds(10, 27, 302, 284);
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
		
		progressBarThumbnailsLoading = new JProgressBar();
		progressBarThumbnailsLoading.setBounds(337, 297, 358, 14);
		panelDirictories.add(progressBarThumbnailsLoading);

		JPanel panelImageModifier = new JPanel();
		tabbedPane.addTab("Modify Image", null, panelImageModifier, null);
		panelImageModifier.setLayout(null);
	}

	public void addEvents() {
		this.thumbnailClickedAdapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Thumbnail clickedThumbnail = (Thumbnail)e.getSource();
				
				if(choosenThumbnailToModify != null)
					choosenThumbnailToModify.setForeground(Color.BLACK);
				
				clickedThumbnail.setForeground(Color.GREEN);
				
				choosenThumbnailToModify = clickedThumbnail;
			}
		};
		
		
		panelFileTree.addTreeItemSelected(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

				//Stop worker if is already running
				if(thumbnailWorker != null)
					thumbnailWorker.cancel(true);
				
				thumbnailWorker = new ThumbnailsLoaderWorker(new File(node.toString()), panelThumbnails, thumbnailClickedAdapter);
				thumbnailWorker.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent event) {
						String propertyName = event.getPropertyName();
						
						System.out.println(propertyName);
						switch(propertyName) {
							case "progress":
								progressBarThumbnailsLoading.setValue((Integer) event.getNewValue());
								validate();
								repaint();
								break;
							case "state":
								switch((StateValue) event.getNewValue()) {
									case STARTED:
									case DONE:
										progressBarThumbnailsLoading.setValue(0);
										break;
								default:
									break;
								}
								break;
						}
					}
					
				});
				thumbnailWorker.execute();
			}
		});
	}
}
