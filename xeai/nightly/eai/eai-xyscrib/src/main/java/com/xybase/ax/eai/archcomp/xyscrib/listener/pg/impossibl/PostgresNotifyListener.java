package com.xybase.ax.eai.archcomp.xyscrib.listener.pg.impossibl;

import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;

import com.impossibl.postgres.api.jdbc.PGConnection;
import com.impossibl.postgres.api.jdbc.PGNotificationListener;
import com.xybase.ax.eai.archcomp.control.bus.LevergearBus;
import com.xybase.ax.eai.archcomp.control.bus.constant.LevergearConstant;
import com.xybase.ax.eai.archcomp.control.bus.util.BusRspUtil;
import com.xybase.ax.eai.archcomp.exception.InternalErrorRuntimeException;
import com.xybase.ax.eai.archcomp.xyscrib.listener.pg.model.PostgresNotification;

public class PostgresNotifyListener implements LevergearBus, DisposableBean {

	private final static Logger log = LogManager
			.getLogger(PostgresNotifyListener.class);

	private PGConnection connection;
	private DataSource datasource;
	private boolean isAlive = false;
	private MessageChannel channelOut;
	@SuppressWarnings("unused")
	private MessageChannel errorChannel;

	private String channelName;

	public PostgresNotifyListener() {
		// TODO Auto-generated constructor stub
		channelName = null;
	}

	public PostgresNotifyListener(DataSource dtsource, String channelName,
			MessageChannel channelOut, boolean isAutoStartup) {
		setChannelOut(channelOut);
		setChannelName(channelName);
		setDatasource(dtsource);
		if (isAutoStartup)
			start();
	}

	public void setDatasource(DataSource dtsource) {
		this.datasource = dtsource;
		try {
			log.info("Getting connection for " + this.channelName);
			this.connection = (PGConnection) this.datasource.getConnection();

			connection.addNotificationListener(new PGNotificationListener() {
				@Override
				public void notification(int processId, String channelName,
						String payload) {
					// TODO Auto-generated method stub
					dispatch(processId, channelName, payload);
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void dispatch(int processId, String channelName, String payload) {
		log.info("Notification in on process id: " + processId);
		try {
			channelOut.send(new GenericMessage<PostgresNotification>(
					new PostgresNotification(processId, channelName, payload)));
			log.info("Notification with process id: " + processId + " sent!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalErrorRuntimeException(e);
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
			log.info(getStopStatement() + " is starting!");
			Statement statement = connection.createStatement();
			statement.execute(getStopStatement());
			statement.close();
			log.info("Listener('" + channelName + "') stoped! ");
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
		try {
			log.info(getStartStatement() + " is starting!");
			Statement statement = connection.createStatement();
			statement.execute(getStartStatement());
			statement.close();
			log.info("Listener('" + channelName + "') start! ");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return BusRspUtil.asResponse(LevergearConstant.state.START, "execute("
				+ getStartStatement() + ")");
	}

	@Override
	public boolean isAlive() {
		// TODO Auto-generated method stub
		return isAlive;
	}

	@Override
	public String info() {
		// TODO Auto-generated method stub
		return null;
	}

}
