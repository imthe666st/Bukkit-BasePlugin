package Phecda.BasePlugin;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import Phecda.BasePlugin.Components.PluginCommand;
import Phecda.BasePlugin.Components.HelpTopics.PluginHelp;

public abstract class BasePlugin<T extends BasePlugin<T>> extends JavaPlugin {
	
	private ApiPlugin BaseApi;
	
	protected PluginHelp pluginHelp;
	
	@Override
	public void onEnable() {
		
		this.Configurations = new HashMap<String, FileConfiguration>();
		this.pluginHelp = new PluginHelp(this);
		
		// Time to get the API plugin
		this.BaseApi = (ApiPlugin) Bukkit.getPluginManager().getPlugin("BasePluginApi");
		
		// Do user stuff
		enablePlugin();
		
		Bukkit.getHelpMap().addTopic(pluginHelp);
		
	}
	
	
	@Override
	public void onDisable() {
		disablePlugin();
	}

	/*****************************************************/
	
	// Commands
	
	private static CommandMap commandMap;
	private static final CommandMap getCommandMap() {
        if (commandMap == null) {
            try {
                final Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                f.setAccessible(true);
                commandMap = (CommandMap) f.get(Bukkit.getServer());
                return getCommandMap();
            } catch (Exception e) { e.printStackTrace(); }
        } else if (commandMap != null) { return commandMap; }
        return getCommandMap();
    }

	protected PluginCommand<T> registerCommand(PluginCommand<T> cmd) {
		
		cmd.setExecutor(cmd);
		getCommandMap().register(this.getName(), cmd);
		
		this.pluginHelp.addCommand(cmd);
		
		return cmd;
	}
	protected PluginCommand<T> registerCommand(PluginCommand<T> cmd, PluginHelp helpPage) {

		cmd.setExecutor(cmd);
		getCommandMap().register(this.getName(), cmd);
		
		helpPage.addCommand(cmd);
		return cmd;
	}
	
	/*****************************************************/
	// FileConfiguration

	/**
	 * Map of all used configurations
	 * Key is the Path/Name
	 */
	private HashMap<String, FileConfiguration> Configurations;
	
	/**
	 * Retrieves {@link FileConfiguration} <br>
	 * Uses default 'config.yml' as FileConfiguration
	 * @return {@link FileConfiguration}
	 */
	public FileConfiguration getFileConfiguration() {
		return getFileConfiguration("config");
	}
	
	/**
	 * Retrieves {@link FileConfiguration}
	 * @param File Path of the configuration
	 * @return {@link FileConfiguration}
	 */
	public FileConfiguration getFileConfiguration(String File) {
		if (Configurations.containsKey(File)) {
			return Configurations.get(File);
		} else {
			// Create a new config.
			FileConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), File + ".yml"));
			config.options().copyDefaults(true);
			Configurations.put(File, config);
			return config;
		}
	}
	

	public boolean saveFileConfiguration() {
		return saveFileConfiguration("config");
	}
	
	/**
	 * Saves {@link FileConfiguration}
	 * @param File Path of the configuration
	 * @return if the {@link FileConfiguration} could be saved
	 */
	public boolean saveFileConfiguration(String File) {
		
		if (!Configurations.containsKey(File)) return false;
		
		try {
			Configurations.remove(File).save(new File(getDataFolder(), File + ".yml"));
			return true;
		} catch (IOException ex) {
			return false;
		}
	}

	
	/**
	 * Saves all {@link FileConfiguration}
	 * @return if all {@link FileConfiguration} could be saved
	 */
	public boolean saveFileConfigurations() {
		Set<String> keys = Configurations.keySet();
		
		boolean savedEverything = true;
		for (String s : keys.toArray(new String[0])) {
			if (!saveFileConfiguration(s)) savedEverything = false;
		}
		
		
		return savedEverything;
	}
	
	/**
	 * Reloads all {@link FileConfiguration}<br>
	 * Does not save the FileConfigurations!
	 */
	public void reloadConfigurations() {
		Set<String> keys = Configurations.keySet();
		
		for (String s : keys.toArray(new String[0])) {
			Configurations.remove(s);
			getFileConfiguration(s);
		}
	}

	/*****************************************************/
	// MySQL
	public boolean isMySqlEnabled() {
		return BaseApi.isMySqlEnabled();
	}
	
	public Connection createConnection() {
		return BaseApi.createConnection();
	}
	
	public Connection createConnection(String Database) {
		return BaseApi.createConnection(Database);
	}

	public void closeConnection() {
		BaseApi.closeConnection();
	}
	
	/*****************************************************/
	
	
	public abstract void enablePlugin();
	public abstract void disablePlugin();
}
