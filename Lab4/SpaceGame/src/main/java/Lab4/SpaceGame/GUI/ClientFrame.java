package Lab4.SpaceGame.GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Lab4.SpaceGame.Client.Client;
import Lab4.SpaceGame.Client.ClientRemote;
import Lab4.SpaceGame.Core.CanHandleGameEvent;
import Lab4.SpaceGame.Core.Player;
import Lab4.SpaceGame.Core.Player.Role;
import Lab4.SpaceGame.Core.Utils;
import Lab4.SpaceGame.Server.GameEvent;
import Lab4.SpaceGame.Server.GameServer;
import Lab4.SpaceGame.Server.ServerRemote;

public class ClientFrame extends JFrame implements Serializable {

	private ServerRemote look_up;

	private JPanel contentPane;
	private JTextField txtPlayerName;
	private JComboBox<Role> cbRole;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientFrame frame = new ClientFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	public ClientFrame() throws RemoteException, NotBoundException {
		setTitle("Client App");
		init();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 338, 166);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblPlayername = new JLabel("PlayerName");
		lblPlayername.setBounds(10, 11, 65, 14);
		contentPane.add(lblPlayername);

		txtPlayerName = new JTextField();
		txtPlayerName.setText("PlayerA");
		txtPlayerName.setBounds(10, 31, 165, 20);
		contentPane.add(txtPlayerName);
		txtPlayerName.setColumns(10);

		JButton btnJoinGame = new JButton("Join The Game");
		btnJoinGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Role playerRole = (Role) cbRole.getSelectedItem();
				String name = txtPlayerName.getText().trim();

				Player p = new Player(name, playerRole);
				Client client = null;
				CanHandleGameEvent f = null;
				try {
					client = new Client(p);
					
					switch (p.getRole()) {
					case Captain:
						f = new CaptainFrame(look_up, client);
						break;
					case Mechanic:
						f = new MechanicFrame(look_up, client);
						break;
					case Steersman:
						f = new SteersmanFrame(look_up, client);
						break;
					}

					client.setEventHandler(f);

					GameEvent r = look_up.joinGame(client, p);

					if (r == null) {
						Utils.displayError("Cant join game righ now");
						return;
					}
					// TODO: Generic server response

					dispose();

					if (f != null)
						((JFrame) f).setVisible(true);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}

			}
		});
		btnJoinGame.setBounds(10, 88, 294, 23);
		contentPane.add(btnJoinGame);

		JLabel lblRole = new JLabel("Role");
		lblRole.setBounds(195, 11, 46, 14);
		contentPane.add(lblRole);

		cbRole = new JComboBox<>();
		cbRole.setModel(new DefaultComboBoxModel<Role>(Role.values()));
		cbRole.setBounds(195, 31, 109, 20);
		contentPane.add(cbRole);
	}

	private void init() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(GameServer.SERVER_PORT);
		look_up = (ServerRemote) registry.lookup(GameServer.SERVER_LOOKUP);
	}
}
