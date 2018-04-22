package Lab6.SOAP;

import java.io.IOException;
import java.net.*;
import java.util.*;
import java.util.stream.IntStream;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebServiceException;

public class NodeScanner {

	private int _minPort;
	private int _maxPort;

	public NodeScanner(int minPort, int maxPort) {
		_minPort = minPort;
		_maxPort = maxPort;
	}

	public NodeServiceWs findNextNodeService(String remoteFormat, int startPort) {

		for (int portOffset = 0; portOffset < (_maxPort - _minPort); portOffset++) {
			int port = _minPort + ((startPort + portOffset) % ((_maxPort - _minPort))) % _maxPort;
			try {
				String url = String.format(remoteFormat, port);
				
				Socket s = new Socket();
				s.connect(new InetSocketAddress(new URL(url).getHost(), port), 10);
				s.close();

				Service service = Service.create(new URL(url + "?wsdl"), new QName("http://superbiz.org/wsdl", "NodeService"));
				NodeServiceWs r = service.getPort(NodeServiceWs.class);
				return r;

			} catch (WebServiceException | IOException ex) {
				// ex.printStackTrace();
				System.err.println("Port: " + port + "Jest wolny");
			}
		}

		return null;
	}
	
	public List<NodeServiceWs> findAllNodeServices(String remoteFormat){
		List<NodeServiceWs> allNodes = new ArrayList<>();
		
		for (int port = _minPort; port < _maxPort; port++) {
			try {
				
				String url = String.format(remoteFormat, port);
				
				Socket s = new Socket();
				s.connect(new InetSocketAddress(new URL(url).getHost(), port), 10);
				
				s.close();
				
				Service service = Service.create(new URL(url + "?wsdl"), new QName("http://superbiz.org/wsdl", "NodeService"));
				NodeServiceWs r = service.getPort(NodeServiceWs.class);
				allNodes.add(r);

				/*
				String url = String.format(remoteFormat, port);
				 * 
				 * Service calculatorService = Service.create( new URL(url+"?wsdl"), new
				 * QName("http://superbiz.org/wsdl", "NodeService"));
				 */

			} catch (WebServiceException | IOException ex) {
			}
		}
		
		return allNodes;
	}

	public Integer findNextFreePort(String host) {
		
		for (int port = _minPort; port < _maxPort; port++) {
			try {
				
				Socket s = new Socket();
				s.connect(new InetSocketAddress(host, port), 10);
				
				s.close();

				/*
				String url = String.format(remoteFormat, port);
				 * 
				 * Service calculatorService = Service.create( new URL(url+"?wsdl"), new
				 * QName("http://superbiz.org/wsdl", "NodeService"));
				 */

			} catch (WebServiceException | IOException ex) {
				return port;
			}
		}

		return null;
	}
}
