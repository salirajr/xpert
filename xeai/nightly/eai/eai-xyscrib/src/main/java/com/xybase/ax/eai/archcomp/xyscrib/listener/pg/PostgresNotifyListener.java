package com.xybase.ax.eai.archcomp.xyscrib.listener.pg;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.PGConnection;
import org.postgresql.PGNotification;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.xyscrib.listener.pg.model.PostgresNotification;

public class PostgresNotifyListener implements LevergearBus, DisposableBean {

	private final static Logger log = LogManager
			.getLogger(PostgresNotifyListener.class);

	private Thread thread;
	private PGConnection pgConnection;
	private Connection connection;
	private MessageChannel channelOut;
	@SuppressWarnings("unused")
	private MessageChannel errorChannel;

	private String channelName;
	private String identifier;

	private boolean isAlive = false;
	private int watchleap = 1000;

	public PostgresNotifyListener() {
		// TODO Auto-generated constructor stub
		channelName = null;
	}

	public PostgresNotifyListener(DataSource dtsource,
			final String channelName, MessageChannel channelOut,
			boolean isAutoStartup) {
		setChannelOut(channelOut);
		setChannelName(channelName);
		try {
			setDatasource(dtsource);
			if (isAutoStartup)
				start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setDatasource(DataSource dtsource) throws SQLException {
		this.connection = dtsource.getConnection();

	}

	private void dispatch(String batch, int processId, String channelName,
			String payload) {
		log.info("Notification in on process id: " + processId);
		try {
			channelOut.send(new GenericMessage<PostgresNotification>(
					new PostgresNotification(processId, channelName, payload,
							identifier)));
			log.info("Notification with process id: " + processId + ", batch: "
					+ batch + " sent!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setChannelOut(MessageChannel channel) {
		this.channelOut = channel;
	}

	public void setErrorChannel(MessageChannel channel) {
		this.errorChannel = channel;
	}

	@Override
	public void destroy() throws Exception {
		log.info(this.channelName + " IS STOPPING!!");
		stop();
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	private String getStartStatement() {
		return "LISTEN " + channelName.trim();
	}

	private String getStopStatement() {
		return "UNLISTEN " + channelName.trim();
	}

	@Override
	public String stop() {
		// TODO Auto-generated method stub
		if (channelName == null)
			throw new InternalErrorRuntimeException(
					"Channel Name is Null!, Listener is not alive!");

		try {
			isAlive = false;
			log.info(getStopStatement() + " is called!");

			try {
				Thread.sleep(2 * watchleap);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Statement statement = this.connection.createStatement();
			statement.execute(getStopStatement());
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return BusRspUtil.asResponse(LevergearConstant.state.STOP, "execute("
				+ getStopStatement() + ")");
	}

	@Override
	public String start() {
		// TODO Auto-generated method stub
		if (channelName == null)
			throw new InternalErrorRuntimeException(
					"Channel Name is Null!, failed to initiate Listener.");

		if (isAlive)
			return BusRspUtil.asResponse(LevergearConstant.state.START,
					"Listener('" + channelName + "') is already runnings!");
		try {
			log.info("Getting connection for " + this.channelName);
			this.pgConnection = (PGConnection) connection;

			Statement statement = this.connection.createStatement();
			statement.execute(getStartStatement());
			statement.close();
			isAlive = true;
			thread = new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					log.info("Listener('" + channelName + "') start! ");
					while (isAlive) {
						PGNotification notifications[];
						try {
							notifications = pgConnection.getNotifications();
							if (notifications != null) {
								process(notifications);
							}

							Thread.sleep(watchleap);
						} catch (SQLException | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
					log.info("Listener('" + channelName + "') stoped! ");
				}
			};

			thread.start();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BusRspUtil.asResponse(LevergearConstant.state.START, "execute("
				+ getStartStatement() + ")");
	}

	private void process(PGNotification[] notifications) {
		for (int i = 0; i < notifications.length; i++) {
			dispatch("", notifications[i].getPID(), channelName,
					notifications[i].getParameter());
		}
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return isAlive;
	}

	public void setWatchleap(int watchleap) {
		this.watchleap = watchleap;
	}

	public void setIdentifier(String dbName) {
		this.identifier = dbName;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

}
