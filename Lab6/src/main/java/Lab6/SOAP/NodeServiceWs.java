package Lab6.SOAP;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://superbiz.org/wsdl")
@SOAPBinding(style=SOAPBinding.Style.RPC)
public interface NodeServiceWs {
	String getUrl();
	String handleMessage (@WebParam(name = "msg") String msg);
}
