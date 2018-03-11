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
import java.util.List;

import javax.swing.Box;
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

	JButton btnLoadPlugins, btnUnloadPlugins;

	Thread pluginWorkingThread;

	MouseAdapter thumbnailClickedAdapter;

	JScrollPane scrollPane;

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
					progressBarThumbnailsLoading.setValue(0);

				}
			}
		});
		btnCancelThumbnailWorker.setBounds(624, 297, 60, 20);
		panelDirictories.add(btnCancelThumbnailWorker);

		JPanel panelImageModifier = new JPanel();
		tabbedPane.addTab("Modify Image", null, panelImageModifier, null);
		panelImageModifier.setLayout(null);

		btnLoadPlugins = new JButton("Load Plugins");
		btnLoadPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnLoadPlugins.setVisible(false);
				btnUnloadPlugins.setVisible(true);

				panelLoadedPlugins.removeAll();

				List<Class<?>> pluginClasses = CCLoader.get().loadImagePluginClasses(PLUGIN_FOLDER);

				for (Class<?> clazz : pluginClasses) {
					try {

						Constructor<?> ctor = clazz.getConstructor();
						Object plugin = ctor.newInstance();

						String pluginName = (String) clazz.getMethod("getName", null).invoke(plugin, null);

						JButton pluginButton = new JButton(pluginName);
						pluginButton.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								try {
									if (choosenThumbnailToModify == null
											|| choosenThumbnailToModify.getOrginalImageBytes() == null)
										return;

									Runnable renderImageTask = new Runnable() {
										public void run() {
											Method transformMethod;
											try {
												transformMethod = clazz.getMethod("transformImage",
														(Class<?>[]) new Class[] { byte[].class });
												byte[] transofmedImage = transofmedImage = (byte[]) transformMethod.invoke(plugin,choosenThumbnailToModify.getOrginalImageBytes());

												Thumbnail t = new Thumbnail(transofmedImage);

												panelTransformedImage.removeAll();
												panelTransformedImage.add(t);

												panelTransformedImage.validate();
												panelTransformedImage.repaint();
											} catch (NoSuchMethodException | SecurityException | IllegalAccessException
													| IllegalArgumentException | InvocationTargetException e) {
												e.printStackTrace();
											}

										}
									};

									if (pluginWorkingThread != null && pluginWorkingThread.isAlive())
										pluginWorkingThread.interrupt();

									pluginWorkingThread = new Thread(renderImageTask);
									pluginWorkingThread.start();

								} catch (SecurityException | IllegalArgumentException e1) {
									e1.printStackTrace();
								}
							}
						});

						panelLoadedPlugins.add(pluginButton);
						panelLoadedPlugins.add(Box.createHorizontalStrut(10));

					} catch (NoSuchMethodException | IllegalAccessException | InstantiationException
							| InvocationTargetException | IllegalArgumentException | SecurityException e) {
						e.printStackTrace();
					}
				}

				validate();
				repaint();
			}
		});
		btnLoadPlugins.setBounds(10, 11, 121, 23);
		panelImageModifier.add(btnLoadPlugins);

		btnUnloadPlugins = new JButton("Unload Plugins");
		btnUnloadPlugins.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				panelLoadedPlugins.removeAll();
				CCLoader.unload();

				btnLoadPlugins.setVisible(true);
				btnUnloadPlugins.setVisible(false);

				repaint();
			}
		});
		btnUnloadPlugins.setBounds(10, 11, 121, 23);
		btnUnloadPlugins.setVisible(false);
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
