package Lab6.SOAP;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.xml.ws.Endpoint;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;

public class NodeGUI extends JFrame {
	
	static final String LAYER_URL = "http://localhost:%d/imqSOAPexamples/SOAPEchoServlet";

	private JPanel contentPane;
	private JTextField txtEndpointUrl;
	private JTextField txtPort;
	private JTextField txtMsg;
	private JTextArea txtAreaLog;
	
	private NodeScanner scanner;
	private NodeService nodeService;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NodeGUI frame = new NodeGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws MalformedURLException 
	 */
	public NodeGUI() throws MalformedURLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 607, 387);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtEndpointUrl = new JTextField();
		txtEndpointUrl.setEditable(false);
		txtEndpointUrl.setBounds(10, 31, 373, 20);
		contentPane.add(txtEndpointUrl);
		txtEndpointUrl.setColumns(10);
		
		JLabel lblNodeEndpointUrl = new JLabel("Url");
		lblNodeEndpointUrl.setBounds(10, 11, 46, 14);
		contentPane.add(lblNodeEndpointUrl);
		
		JLabel lblPort = new JLabel("Port");
		lblPort.setBounds(438, 12, 46, 14);
		contentPane.add(lblPort);
		
		txtPort = new JTextField();
		txtPort.setEditable(false);
		txtPort.setBounds(438, 32, 86, 20);
		contentPane.add(txtPort);
		txtPort.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 67, 571, 234);
		contentPane.add(scrollPane);
		
		txtAreaLog = new JTextArea();
		scrollPane.setViewportView(txtAreaLog);
		txtAreaLog.setEditable(false);
		
		txtMsg = new JTextField();
		txtMsg.setBounds(10, 317, 260, 20);
		contentPane.add(txtMsg);
		txtMsg.setColumns(10);
		
		JButton btnSendToNextNode = new JButton("Send To Next Node");
		btnSendToNextNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String msg = txtMsg.getText().trim();
				
				try {
					NodeServiceWs running = scanner.findNextNodeService(LAYER_URL, nodeService.getPort());
					
					AddLogMsg("Sending to " + running.getUrl() + " Message " + msg);

					running.handleMessage(msg);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
			}
		});
		btnSendToNextNode.setBounds(309, 316, 260, 23);
		contentPane.add(btnSendToNextNode);
		
		init();
	}
	
	private void init() throws MalformedURLException {
		scanner = new NodeScanner(100, 105);
		
		URL u = new URL(String.format(LAYER_URL, 80));
		

		int freePort = scanner.findNextFreePort(u.getHost());
		
		nodeService = new NodeService(this, String.format(LAYER_URL, freePort), freePort);
		Endpoint.publish(nodeService.getUrl(), nodeService);
		
		this.txtEndpointUrl.setText(nodeService.getUrl());
		this.txtPort.setText(Integer.toString(nodeService.getPort()));
		
		this.txtMsg.setText("Message from " + nodeService.getPort());
	}
	
	public void AddLogMsg(String msg) {
		this.txtAreaLog.append(msg + '\n');
	}
}
