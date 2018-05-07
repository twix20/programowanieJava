package Lab7.SecuredEncryptor;

import java.awt.EventQueue;
import java.io.File;
import java.nio.file.Paths;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Lab7.Encryptor.Crypto;

import javax.crypto.Cipher;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame {

	private File fileToEncrypt, fileToDecrypt;
	
	private JFrame frame;
	private JTextField txtFileToEncrypt;
	private JTextField txtFileToDecrypt;
	private JTextField txtSecret;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 584, 256);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblFileToEncrypt = new JLabel("File to encrypt");
		lblFileToEncrypt.setBounds(10, 11, 87, 14);
		frame.getContentPane().add(lblFileToEncrypt);
		
		txtFileToEncrypt = new JTextField();
		txtFileToEncrypt.setEditable(false);
		txtFileToEncrypt.setBounds(10, 36, 281, 20);
		frame.getContentPane().add(txtFileToEncrypt);
		txtFileToEncrypt.setColumns(10);
		
		JButton btnChooseFileToEncrypt = new JButton("Choose file to encrypt");
		btnChooseFileToEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileToEncrypt = chooseFileDialog();
				
				if(fileToEncrypt != null) {
					txtFileToEncrypt.setText(fileToEncrypt.getName());
				}
			}
		});
		btnChooseFileToEncrypt.setBounds(301, 35, 257, 23);
		frame.getContentPane().add(btnChooseFileToEncrypt);
		
		JLabel lblFileToDecrypt = new JLabel("File to decrypt");
		lblFileToDecrypt.setBounds(10, 67, 120, 14);
		frame.getContentPane().add(lblFileToDecrypt);
		
		txtFileToDecrypt = new JTextField();
		txtFileToDecrypt.setEditable(false);
		txtFileToDecrypt.setBounds(11, 92, 280, 20);
		frame.getContentPane().add(txtFileToDecrypt);
		txtFileToDecrypt.setColumns(10);
		
		JButton btnChooseFileTo = new JButton("Choose file to decrypt");
		btnChooseFileTo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileToDecrypt = chooseFileDialog();
				
				if(fileToDecrypt != null) {
					txtFileToDecrypt.setText(fileToDecrypt.getName());
				}
			}
		});
		btnChooseFileTo.setBounds(301, 91, 257, 23);
		frame.getContentPane().add(btnChooseFileTo);
		
		JLabel lblSecret = new JLabel("Secret");
		lblSecret.setBounds(10, 123, 46, 14);
		frame.getContentPane().add(lblSecret);
		
		txtSecret = new JTextField();
		txtSecret.setText("This is a secret");
		txtSecret.setBounds(10, 148, 548, 20);
		frame.getContentPane().add(txtSecret);
		txtSecret.setColumns(10);
		
		JButton btnEncrypt = new JButton("Encrypt");
		btnEncrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fileToEncrypt == null)
					return;
				
				String key = txtSecret.getText();
				File inputFile = fileToEncrypt;
				
				String encryptedFileName = Paths.get(inputFile.getParent(), "encrypted-" + inputFile.getName()).toAbsolutePath().toString();
				File encryptedFile = new File(encryptedFileName);
				
    	        Crypto.fileProcessor(Cipher.ENCRYPT_MODE, key, inputFile, encryptedFile);
			}
		});
		btnEncrypt.setBounds(10, 179, 281, 23);
		frame.getContentPane().add(btnEncrypt);
		
		JButton btnDecrypt = new JButton("Decrypt");
		btnDecrypt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileToDecrypt == null)
					return;
				
				String key = txtSecret.getText();
				File inputFile = fileToDecrypt;
				
				String decryptedFileName = Paths.get(inputFile.getParent(), "decrypted-" + inputFile.getName()).toAbsolutePath().toString();
				File decryptedFile = new File(decryptedFileName);
				
    	        Crypto.fileProcessor(Cipher.DECRYPT_MODE, key, inputFile, decryptedFile);
				
			}
		});
		btnDecrypt.setBounds(301, 179, 257, 23);
		frame.getContentPane().add(btnDecrypt);
	}
	
	private File chooseFileDialog() {
		JFileChooser jfc = new JFileChooser(Paths.get("").toAbsolutePath().normalize().toString());

		int returnValue = jfc.showOpenDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			return jfc.getSelectedFile();
		}
		
		return null;
	}
}
