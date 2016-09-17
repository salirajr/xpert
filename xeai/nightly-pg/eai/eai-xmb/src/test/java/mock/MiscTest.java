package mock;

import java.io.IOException;

import javax.xml.soap.SOAPException;

import com.xybase.ax.eai.archcomp.xmb.util.XMBMessageUtil;
import com.xybase.xmb.XMBTextMessage;

public class MiscTest {
	public static void mainA(String[] args) {
		String soapResponse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><dispatch xmlns=\"urn:xmb\"><message><refId/></message></dispatch></soapenv:Body></soapenv:Envelope>";

		String addText = "039280390-232";
		soapResponse = soapResponse.replaceAll("<refId/>", "<refId>" + addText
				+ "</refId>");

		System.out.println(soapResponse);
	}

	public static void main(String[] args) {
		String in = "<SOAP-ENV:Envelope xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"><SOAP-ENV:Header/><SOAP-ENV:Body><dispatch><message><msgCode>AODBAFSADHOC</msgCode><msgVersion>1</msgVersion><origSystem>ODB</origSystem><priority>0</priority><text>DB7028320           8Q 9999 IA3882FAAN            IST                        201608091023                                                                 01                    J</text><timestamp>1470733554382</timestamp><reffId/></message></dispatch></SOAP-ENV:Body></SOAP-ENV:Envelope>";

		try {
			XMBTextMessage trans = XMBMessageUtil.fromSoap(in);
			String out = XMBMessageUtil.toSoap(trans);

			if (in.equals(out)) {
				System.out.println("IN/OUT equal!");
			} else {
				System.out.println("IN/OUT different!");
				System.out.println("in: " + in);
				System.out.println("out: " + out);
			}
		} catch (IOException | SOAPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
