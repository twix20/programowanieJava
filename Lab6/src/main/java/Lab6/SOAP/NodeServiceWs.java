package Lab6.SOAP;

import javax.jws.WebService;

@WebService(targetNamespace = "http://superbiz.org/wsdl")
public interface NodeServiceWs {
	String getUrl();
	String handleMessage (String msg);
}
