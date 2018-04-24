package Lab6.SOAP;

import javax.xml.soap.*;

import java.io.IOException;

import javax.xml.messaging.URLEndpoint;

import javax.jws.*;
import javax.jws.soap.*;

@WebService(
		portName = "CalculatorPort",
		serviceName = "NodeService",
		targetNamespace = "http://superbiz.org/wsdl",
		endpointInterface = "Lab6.SOAP.NodeServiceWs"
)
public class NodeService implements NodeServiceWs {
	private String url;
	private int port;
	private NodeGUI nodeGui;

	public NodeService(NodeGUI nodeGui, String urlFormat, int port) {
		this.url = String.format(urlFormat, port);
		this.port = port;
		this.nodeGui = nodeGui;
	}
			
	
	
	public String handleMessage (String msg) {
		
		this.nodeGui.AddLogMsg("Recived: " + msg);
        
        return "Handled: " + msg;
	}

	public String getUrl() {
		return url;
	}
	
	public int getPort() {
		return port;
	}
}
