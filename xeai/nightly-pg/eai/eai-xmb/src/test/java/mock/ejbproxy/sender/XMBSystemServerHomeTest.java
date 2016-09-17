package mock.ejbproxy.sender;

/** 
 * Modification History
 * Date			Time				Modified By             Comments
 * **************************************************************************************
 * Mar 27, 2015	10:51:04 AM			Jovi Rengga Salira		Initial Creation
 * **************************************************************************************
 */

import java.rmi.RemoteException;
import java.util.Hashtable;

import javax.ejb.CreateException;
import javax.naming.NamingException;
import javax.rmi.PortableRemoteObject;

import com.xybase.ax.eai.archcomp.context.lookup.javax.ContextLookup;
import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.XMBTextMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;
import com.xybase.xmb.helper.XMBTimestamp;
import com.xybase.xmb.xss.XMBSystemServer;
import com.xybase.xmb.xss.XMBSystemServerHome;

/**
 * @note
 *
 */
public class XMBSystemServerHomeTest {

	public static void main(String[] args) throws NamingException {
		String ejbName = "ejb:EaiXyStubs/EaiXystubMb/XeaiMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
//		ejbName = "ejb:xmb_receiver/xmb_receiver_ejb/XMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
//		ejbName = "EaiXyStubs/EaiXystubMb//XeaiMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
//		ejbName = "xmb-rms/xmb_receiver_ejb//XMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
//		ejbName = "xmb-ams/xmb_receiver_ejb//XMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
///		ejbName="ejb:/EaiXyStubs/EaiXystubMb/XeaiMBSystemServer!com.xybase.xmb.xss.XMBSystemServerHome";
		final Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		jndiProperties.put("java.naming.factory.initial",
				"org.jboss.naming.remote.client.InitialContextFactory");
		// jndiProperties.put("java.naming.factory.url.pkgs",
		// "org.jboss.ejb.client.naming");
		jndiProperties.put("org.jboss.ejb.client.scoped.context", true);
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		jndiProperties.put("java.naming.provider.url",
		 "remote://192.168.88.75:4447"); // XEAI
//		 "remote://192.168.88.91:4547"); // RMS
//				"remote://192.168.88.91:4747"); // AMS
//				 "remote://192.168.88.112:4447"); // XEAI
		jndiProperties.put("java.naming.security.principal", "ejb");
		jndiProperties.put("java.naming.security.credentials", "user123-");

		ContextLookup contextLookup = new ContextLookup(ejbName, jndiProperties);
		Object ejbLookup;
		ejbLookup = contextLookup.lookup();

		XMBSystemServerHome home = XMBSystemServerHome.class
				.cast(PortableRemoteObject.narrow(ejbLookup,
						XMBSystemServerHome.class));
		int opt = 1;
		
		opt = 0;
		
		
		XMBSystemServer xMBMessageServer = null;
		try {
			xMBMessageServer = home.create();
		} catch (RemoteException | CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		XMBTextMessage outTextMsg = new XMBTextMessage();

		if (opt == 1) {
			try {

				((XMBMessage) outTextMsg).setMsgCode("XEAISTUBXMBSYSSVR");
				((XMBMessage) outTextMsg).setMsgCode("XEAIXMBSYSSVR");
				((XMBMessage) outTextMsg).setMsgCode("AODBFLTREMSTATUS2");
				((XMBMessage) outTextMsg).setMsgVersion(1);
				((XMBMessage) outTextMsg).setOrigSystem("ODB");
				((XMBMessage) outTextMsg).setPriority(4);
				((XMBMessage) outTextMsg).setTimestamp(new XMBTimestamp(System
						.currentTimeMillis()));
				((XMBMessage) outTextMsg)
						.setRefId("AEF6E81E-8B98-97A5-22AF-239DAA2ACA53");
				;
				outTextMsg
						.setText("DB7005202           JT                          4110 A201505301400201505301505            201505301505BLA                                                               ");
				xMBMessageServer.dispatch(outTextMsg);

				System.out.println(outTextMsg.getTimestamp());
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			try {
				xMBMessageServer.ping();
				System.out.println("Pinged!");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
