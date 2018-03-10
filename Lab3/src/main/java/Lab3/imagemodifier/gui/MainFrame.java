package Lab3.imagemodifier.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import Lab3.imagemodifier.pluginsystem.CCLoader;
import Lab3.imagemodifier.pluginsystem.plugins.IPluginImage;

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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JProgressBar;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends JFrame {
	final File PLUGIN_FOLDER = new File("target\\classes\\Lab3\\imagemodifier\\pluginsystem\\plugins");

	ThumbnailsLoaderWorker thumbnailWorker;

	Thumbnail choosenThumbnailToModify;

	FileTree panelFileTree;
	JPanel panelThumbnails;
	JPanel panelDirictories;
	JProgressBar progressBarThumbnailsLoading;

	MouseAdapter thumbnailClickedAdapter;

	JScrollPane scrollPane;

	List<JButton> pluginButtons;

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

		JButton btnLoadPlugins = new JButton("Load Plugins");
		btnLoadPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				pluginButtons = new ArrayList<>();

				List<File> pluginFiles = Arrays.asList(PLUGIN_FOLDER.listFiles()).stream()
						.filter(x -> x.getName().endsWith("Plugin.class") && !x.getName().contains("IPluginImage"))
						.collect(Collectors.toList());
				;

				CCLoader ccl = new CCLoader(MainFrame.class.getClassLoader());
				for (File f : pluginFiles) {
					try {
						String fileName = f.getName().substring(0, f.getName().length() - 6);

						Class<?> clas = ccl.loadClass("Lab3.imagemodifier.pluginsystem.plugins." + fileName);

						Constructor<?> ctor = clas.getConstructor();
						Object plugin = ctor.newInstance();

						Method getNameMethod = clas.getMethod("getName", null);

						Object x = getNameMethod.invoke(plugin, null);

						System.out.println(x);
					} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
							| InstantiationException | InvocationTargetException | IllegalArgumentException
							| SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// Object argsArray[] = { progArgs };
					// main.invoke(null, argsArray);

				}

			}
		});
		btnLoadPlugins.setBounds(10, 11, 121, 23);
		panelImageModifier.add(btnLoadPlugins);

		JButton btnUnloadPlugins = new JButton("Unload Plugins");
		btnUnloadPlugins.setBounds(141, 11, 111, 23);
		panelImageModifier.add(btnUnloadPlugins);
	}

	public void addEvents() {
		this.thumbnailClickedAdapter = new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Thumbnail clickedThumbnail = (Thumbnail) e.getSource();

				if (choosenThumbnailToModify != null)
					choosenThumbnailToModify.setForeground(Color.BLACK);

				clickedThumbnail.setForeground(Color.GREEN);

				choosenThumbnailToModify = clickedThumbnail;
			}
		};

		panelFileTree.addTreeItemSelected(new TreeSelectionListener() {

			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

				// Stop worker if is already running
				if (thumbnailWorker != null)
					thumbnailWorker.cancel(true);

				thumbnailWorker = new ThumbnailsLoaderWorker(new File(node.toString()), panelThumbnails,
						thumbnailClickedAdapter);
				thumbnailWorker.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						String propertyName = event.getPropertyName();

						System.out.println(propertyName);
						switch (propertyName) {
						case "progress":
							progressBarThumbnailsLoading.setValue((Integer) event.getNewValue());
							validate();
							repaint();
							break;
						case "state":
							switch ((StateValue) event.getNewValue()) {
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