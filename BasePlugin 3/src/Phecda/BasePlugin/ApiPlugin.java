package Phecda.BasePlugin;

import java.sql.Connection;
import java.sql.SQLException;

import org.bukkit.configuration.file.FileConfiguration;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ApiPlugin extends BasePlugin<ApiPlugin> {
	
	private boolean isMySqlEnabled = false;
	private String MySqlServer;
	private int MySqlPort;
	private String MySqlDefaultDatabase;
	private String MySqlUsername;
	private String MySqlPassword;
	
	
	@Override
	public void enablePlugin() {
		FileConfiguration config = getFileConfiguration();
		
		config.addDefault("mysql.enable", false);
		config.addDefault("mysql.server", "192.168.0.10"); 
		config.addDefault("mysql.port", 3306);
		config.addDefault("mysql.database", "Bukkit");
		config.addDefault("mysql.login.username", "root");
		config.addDefault("mysql.login.password", "root");
		
		saveFileConfigurations();
		
		isMySqlEnabled = config.getBoolean("mysql.enable");
		if (isMySqlEnabled) {
			
			this.MySqlServer = config.getString("mysql.server");
			this.MySqlPort = config.getInt("mysql.port");
			this.MySqlDefaultDatabase = config.getString("mysql.database");
			this.MySqlUsername = config.getString("mysql.login.username");
			this.MySqlPassword = config.getString("mysql.login.password");
			
			
		}
		
	}

	@Override
	public void disablePlugin() {
		saveFileConfigurations();
	}
	
	// Implement the MySQL stuff
	
	private Connection connection;
	
	@Override
	public boolean isMySqlEnabled() {
		return this.isMySqlEnabled;
	}

	@Override
	public Connection createConnection() {
		return this.createConnection(this.MySqlDefaultDatabase);
	}
	
	public Connection createConnection(String Database) {
		if (this.connection != null) closeConnection();
		
		this.connection = openConnection(Database);
		return this.connection;
	}

	public void closeConnection() {
		
		try {
			if (connection == null || connection.isClosed()) return;
			connection.close();
			connection = null;
		} catch (SQLException ex) { 
			ex.printStackTrace();
		}
	}

	private synchronized Connection openConnection(String Database) {
		
		try {

			Class.forName("com.mysql.jdbc.Driver");

			MysqlDataSource ds = new MysqlDataSource();
			ds.setDatabaseName(Database);
			ds.setUser(MySqlUsername);
			ds.setPassword(MySqlPassword);
			ds.setServerName(MySqlServer);
			ds.setPort(MySqlPort);
			return ds.getConnection();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
