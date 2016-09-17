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

import com.xybase.ax.eai.archcomp.context.lookup.javax.ContextLookup;
import com.xybase.xmb.XMBMessage;
import com.xybase.xmb.XMBTextMessage;
import com.xybase.xmb.helper.ExecutionFailedException;
import com.xybase.xmb.helper.InvalidRequestException;
import com.xybase.xmb.helper.XMBTimestamp;
import com.xybase.xmb.xms.XMBMessageServer;
import com.xybase.xmb.xms.XMBMessageServerHome;

/**
 * @note
 *
 */
public class XMBMessageServerHomeTest {
	public static void main(String[] args) throws NamingException {
		String ejbName = "ejb:EaiXyStubs/EaiXystubMb/XeaiMBMessageServer!com.xybase.xmb.xms.XMBMessageServerHome";
		ejbName = "ejb:/EaiXmb/XmbEjbIReceiver!com.xybase.xmb.xms.XMBMessageServerHome";
		final Hashtable<Object, Object> jndiProperties = new Hashtable<Object, Object>();
		jndiProperties.put("java.naming.factory.initial",
				"org.jboss.naming.remote.client.InitialContextFactory");
		jndiProperties.put("java.naming.factory.url.pkgs",
				"org.jboss.ejb.client.naming");
		jndiProperties.put("jboss.naming.client.ejb.context", true);
		jndiProperties.put("java.naming.provider.url",
		// "remote://192.168.88.59:4448");
				"remote://localhost:4447");
		// "remote://192.168.88.91:4647");
		jndiProperties.put("java.naming.security.principal", "ejb");
		jndiProperties.put("java.naming.security.credentials", "user123-");
		jndiProperties.put("invocation.timeout", "10000");

		ContextLookup contextLookup = new ContextLookup(ejbName, jndiProperties);
		Object ejbLookup = contextLookup.lookup();

		XMBMessageServerHome xMBMessageServerHome = XMBMessageServerHome.class
				.cast(ejbLookup);
		XMBMessageServer server = null;
		try {
			server = xMBMessageServerHome.create();
		} catch (RemoteException | CreateException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		int opt = 1;

		opt = 1;

		if (opt == 1) {
			try {

				XMBTextMessage outTextMsg = new XMBTextMessage();
				((XMBMessage) outTextMsg).setMsgCode("XEAISTUBXMBSYSSVR");
				((XMBMessage) outTextMsg).setMsgCode("XEAIXMBSYSSVR");
				((XMBMessage) outTextMsg).setMsgCode("AODBAFSUPDATE");
				((XMBMessage) outTextMsg).setMsgVersion(1);
				((XMBMessage) outTextMsg).setOrigSystem("ODB");
				((XMBMessage) outTextMsg).setPriority(4);
				((XMBMessage) outTextMsg).setTimestamp(new XMBTimestamp(System
						.currentTimeMillis()));
				outTextMsg// .setText("1234567890");
						.setText("DB7008660  soni     UGA 0001 DA742           201508071116            201508071116                             A11  1AA6MDI                 DB7008661   J");

				System.out.println(server.dispatch((XMBMessage) outTextMsg));
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvalidRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionFailedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			try {
				server.ping();
				System.out.println("Pinged!");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
