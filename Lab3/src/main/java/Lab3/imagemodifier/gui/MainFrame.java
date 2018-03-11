package Lab3.imagemodifier.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingWorker.StateValue;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import Lab3.imagemodifier.pluginsystem.CCLoader;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 348261545033767115L;

	final File PLUGIN_FOLDER = new File("target\\classes\\Lab3\\imagemodifier\\pluginsystem\\plugins");

	ThumbnailsLoaderWorker thumbnailWorker;

	Thumbnail choosenThumbnailToModify;

	FileTree panelFileTree;
	JPanel panelThumbnails;
	JPanel panelDirictories;
	JPanel panelLoadedPlugins;
	JPanel panelTransformedImage;
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
		setBounds(100, 100, 742, 390);
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

		progressBarThumbnailsLoading = new JProgressBar();
		progressBarThumbnailsLoading.setBounds(10, 297, 604, 20);
		panelDirictories.add(progressBarThumbnailsLoading);
		
		JButton btnCancelThumbnailWorker = new JButton("Stop");
		btnCancelThumbnailWorker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Stop worker if is already running
				if (thumbnailWorker != null) {
					thumbnailWorker.terminate();
				}
			}
		});
		btnCancelThumbnailWorker.setBounds(624, 297, 60, 20);
		panelDirictories.add(btnCancelThumbnailWorker);

		JPanel panelImageModifier = new JPanel();
		tabbedPane.addTab("Modify Image", null, panelImageModifier, null);
		panelImageModifier.setLayout(null);

		JButton btnLoadPlugins = new JButton("Load Plugins");
		btnLoadPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				List<File> pluginFiles = Arrays.asList(PLUGIN_FOLDER.listFiles()).stream()
						.filter(x -> x.getName().endsWith("Plugin.class")).collect(Collectors.toList());

				pluginButtons = new ArrayList<>();
				panelLoadedPlugins.removeAll();

				CCLoader ccl = new CCLoader(MainFrame.class.getClassLoader());
				for (File f : pluginFiles) {
					try {
						String fileName = f.getName().substring(0, f.getName().length() - 6);

						Class<?> clas = ccl.loadClass("Lab3.imagemodifier.pluginsystem.plugins." + fileName);

						Constructor<?> ctor = clas.getConstructor();
						Object plugin = ctor.newInstance();

						String pluginName = (String) clas.getMethod("getName", null).invoke(plugin, null);

						JButton pluginButton = new JButton(pluginName);
						pluginButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									if (choosenThumbnailToModify == null
											|| choosenThumbnailToModify.getOrginalImageBytes() == null)
										return;

									Method transformMethod = clas.getMethod("transformImage",
											(Class<?>[]) new Class[] { byte[].class });

									byte[] transofmedImage = (byte[]) transformMethod.invoke(plugin,
											choosenThumbnailToModify.getOrginalImageBytes());

									Thumbnail t = new Thumbnail(transofmedImage);

									panelTransformedImage.removeAll();
									panelTransformedImage.add(t);

									panelTransformedImage.validate();
									panelTransformedImage.repaint();

								} catch (NoSuchMethodException | SecurityException | IllegalAccessException
										| IllegalArgumentException | InvocationTargetException e1) {
									e1.printStackTrace();
								}
							}
						});

						pluginButtons.add(pluginButton);
						panelLoadedPlugins.add(pluginButton);

					} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException
							| InstantiationException | InvocationTargetException | IllegalArgumentException
							| SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				validate();
				repaint();
			}
		});
		btnLoadPlugins.setBounds(10, 11, 121, 23);
		panelImageModifier.add(btnLoadPlugins);

		JButton btnUnloadPlugins = new JButton("Unload Plugins");
		btnUnloadPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pluginButtons.clear();
				panelLoadedPlugins.removeAll();

				validate();
				repaint();
			}
		});
		btnUnloadPlugins.setBounds(141, 11, 111, 23);
		panelImageModifier.add(btnUnloadPlugins);

		panelLoadedPlugins = new JPanel();
		panelLoadedPlugins.setBounds(10, 42, 701, 44);
		panelLoadedPlugins.setLayout(new BoxLayout(panelLoadedPlugins, BoxLayout.X_AXIS));
		panelImageModifier.add(panelLoadedPlugins);

		panelTransformedImage = new JPanel();
		panelTransformedImage.setBounds(10, 98, 701, 201);
		panelImageModifier.add(panelTransformedImage);
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
				if (thumbnailWorker != null) {
					thumbnailWorker.terminate();
				}

				thumbnailWorker = new ThumbnailsLoaderWorker(new File(node.toString()), panelThumbnails,
						thumbnailClickedAdapter);
				thumbnailWorker.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent event) {
						String propertyName = event.getPropertyName();

						// System.out.println(propertyName);
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
