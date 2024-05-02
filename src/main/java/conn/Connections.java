package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.google.appengine.api.utils.SystemProperty;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class Connections {
	private static HikariDataSource pool=null;
	
	public static Connection getConnection(){
		Connection conn=null;
		
		// If the app is deployed into Google Cloud and database settings are set to Production
	    
			try {
				conn=Connections.getDevConnection();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    
	    return conn;
	}

	
	/*
	The setting below are for local database - not to be used in Google Cloud
	*/
	public static Connection getDevConnection() throws Exception{
		if (pool!=null) {
			return pool.getConnection();
		}
		// The configuration object specifies behaviors for the connection pool.
		
		String driverName = "com.mysql.jdbc.Driver";
	    Class.forName(driverName);

	    String serverName = "localhost";
	    String mydatabase = "ev3robot";
	    String url = "jdbc:mysql://" + serverName + "/" + mydatabase; 

	    String username = "root";
	    String password = "1234";
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
}