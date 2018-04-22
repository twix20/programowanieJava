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
@SOAPBinding(style=SOAPBinding.Style.RPC)
public class NodeService implements NodeServiceWs {
	private String url;
	private int port;
	private NodeGUI nodeGui;

	public NodeService(NodeGUI nodeGui, String url, int port) {
		this.url = url;
		this.port = port;
		this.nodeGui = nodeGui;
	}
			
	
	
	public String handleMessage (String msg) {
		
		this.nodeGui.AddLogMsg("Recived: " + msg);

		/**
		System.out.println("Jestem w NodeService::sendMessage pod portem " + port + " Chce wyslac do " + endpointUrl + " wiadomosc " + msg);
		
        String myNamespace = "myNamespace";
        String myNamespaceURI = "http://superbiz.org/wsdl";
		
        try {
             //Construct a default SOAP message factory.
            MessageFactory mf = MessageFactory.newInstance();

             //Create a SOAP message object.
            SOAPMessage soapMessage = mf.createMessage();

             //Get SOAP part.
            SOAPPart soapPart = soapMessage.getSOAPPart();

             //Get SOAP envelope.

            SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
            soapEnvelope.addNamespaceDeclaration(myNamespace, myNamespaceURI);
            

            // Get SOAP body.
            SOAPBody soapBody = soapEnvelope.getBody();

             //Add child element with the specified name.
    
            SOAPElement element = soapBody.addChildElement("sendMessage", myNamespace, myNamespaceURI);
            element.addNamespaceDeclaration(myNamespace, myNamespaceURI);
            
            SOAPElement soapBodyElem1 = element.addChildElement("endpointUrl", myNamespace);
            soapBodyElem1.addTextNode(endpointUrl);

            SOAPElement soapBodyElem3 = element.addChildElement("msg", myNamespace);
            soapBodyElem3.addTextNode(msg);

            soapMessage.saveChanges();

            //Construct a default SOAP connection factory.
            SOAPConnectionFactory connectionFactory = SOAPConnectionFactory.newInstance();

			//Get SOAP connection.
            SOAPConnection soapConnection = connectionFactory.createConnection();

            //Construct endpoint object.
            URLEndpoint endpoint = new URLEndpoint (endpointUrl);
            
            soapMessage.writeTo(System.out);
            
            

            //Send SOAP message.
            SOAPMessage resp = soapConnection.call(soapMessage, endpoint);

            //Print response to the std output.
            resp.writeTo( System.out );

            //close the connection
            soapConnection.close();

        } catch (SOAPException | IOException ex) {
            ex.printStackTrace();
        }
        
        */
        
        return "Wyslalem " + msg;
	}

	public String getUrl() {
		return url;
	}
	
	public int getPort() {
		return port;
	}
}
