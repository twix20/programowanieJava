package Lab6.SOAP;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.xml.messaging.URLEndpoint;
import javax.xml.soap.*;
import javax.xml.ws.Endpoint;

public class NodeGUI extends JFrame {
	
	static final String LAYER_URL_FORMAT = "http://localhost:%d/SOAPRing";

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
		setBounds(100, 100, 607, 421);
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
				
				NodeServiceWs running = scanner.findNextNodeService(LAYER_URL_FORMAT, nodeService.getPort() + 1);
				//AddLogMsg("Sending to " + running.getUrl() + " Message " + msg)
				//running.handleMessage(msg);

				try {
					SOAPMessage soapMsg = createSoapMessage(running.getUrl(), msg);
					callEndpoint(running.getUrl(), soapMsg);
					
				} catch (SOAPException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
		});
		btnSendToNextNode.setBounds(309, 316, 260, 23);
		contentPane.add(btnSendToNextNode);
		
		JButton btnSendToAllNodes = new JButton("Send To All Nodes");
		btnSendToAllNodes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				String msg = txtMsg.getText().trim();
				
				List<NodeServiceWs> allRunning = scanner.findAllNodeServices(LAYER_URL_FORMAT);
				
				//Remove self
				allRunning.removeIf(n -> n.getUrl().equals(nodeService.getUrl()));
				
				for(NodeServiceWs n : allRunning)
				{
					
					AddLogMsg("Sending to " + n.getUrl() + " Message " + msg);

					n.handleMessage(msg);
				}
				
			}
		});
		btnSendToAllNodes.setBounds(309, 348, 260, 23);
		contentPane.add(btnSendToAllNodes);
		
		init();
	}
	
	private void init() throws MalformedURLException {
		scanner = new NodeScanner(100, 105);
		
		URL u = new URL(String.format(LAYER_URL_FORMAT, 80));
		

		int freePort = scanner.findNextFreePort(u.getHost());
		
		nodeService = new NodeService(this, String.format(LAYER_URL_FORMAT, freePort), freePort);
		Endpoint.publish(nodeService.getUrl(), nodeService);
		
		this.txtEndpointUrl.setText(nodeService.getUrl());
		this.txtPort.setText(Integer.toString(nodeService.getPort()));
		
		this.txtMsg.setText("Message from " + nodeService.getPort());
	}
	
	public void AddLogMsg(String msg) {
		this.txtAreaLog.append(msg + '\n');
	}
	
	private SOAPMessage createSoapMessage(String targetUrl, String msg) throws SOAPException, IOException {
		
        String myNamespace = "myNamespace";
        String myNamespaceURI = "http://superbiz.org/wsdl";
		
		 //Construct a default SOAP message factory.
        MessageFactory mf = MessageFactory.newInstance();

         //Create a SOAP message object.
        SOAPMessage soapMessage = mf.createMessage();

         //Get SOAP part.
        SOAPPart soapPart = soapMessage.getSOAPPart();
        
         //Get SOAP envelope.
        SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
        soapEnvelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
        
        // Get SOAP header.
        SOAPHeader header = soapEnvelope.getHeader();
        
        SOAPElement headerTargetElement = header.addChildElement("TargetUrl", myNamespace, myNamespaceURI);
        SOAPElement soapHeaderElem1 = headerTargetElement.addChildElement("Url");
        soapHeaderElem1.addTextNode(targetUrl);

        // Get SOAP body.
        SOAPBody soapBody = soapEnvelope.getBody();

        SOAPElement element = soapBody.addChildElement("handleMessage", myNamespace, myNamespaceURI);
        SOAPElement soapBodyElem1 = element.addChildElement("msg");
        soapBodyElem1.addTextNode(msg);

        soapMessage.saveChanges();
       
        return soapMessage;
	}
	
	private SOAPMessage callEndpoint(String targetUrl, SOAPMessage soapMessage) throws SOAPException, IOException {
        SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = connectionFactory.createConnection();

        URLEndpoint endpoint = new URLEndpoint (targetUrl);
        
        System.out.println("Sending SOAP message");
        soapMessage.writeTo(System.out);
        
        SOAPMessage resp = soapConnection.call(soapMessage, endpoint);
 
        System.out.println("\nSOAP message response");
        resp.writeTo(System.out);

        soapConnection.close();
        
        return resp;
	}
}
