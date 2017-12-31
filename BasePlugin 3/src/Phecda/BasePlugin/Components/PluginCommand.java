package Phecda.BasePlugin.Components;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import Phecda.BasePlugin.Components.HelpTopics.CommandHelp;
import Phecda.Utility.Queue;

public abstract class PluginCommand<T extends JavaPlugin> extends Command implements CommandExecutor, TabCompleter {

	public abstract String getCommandName();

	public abstract String getCommandDescription();

	public abstract String getCommandUsageMessage();

	public abstract List<String> getCommandAliases();

	public abstract String getCommandPermission();

	protected T plugin;
	
	private CommandExecutor commandExecutor = null;
	
	private CommandHelp commandHelp;
	
	protected PluginCommand(T plugin) {
		super(""); // dummy argument
		this.plugin = plugin;
		
		
		this.setName(getCommandName());
		
		String description = getCommandDescription();
		this.setDescription((description != null) ? description : "");
		
		String usage = getCommandUsageMessage();
		this.setUsage((usage != null) ? usage : "");
		
		List<String> aliases = getCommandAliases();
		setAliases((aliases == null) ? new ArrayList<String>() : aliases);
		
		String perm = getCommandPermission();
		setPermission((perm != null && !perm.isEmpty()) ? perm : "dummy");
		
		
		setPermissionMessage(ChatColor.RED + "You don't have permission to execute this command!");
		
		this.commandHelp = new CommandHelp(this);
		Bukkit.getHelpMap().addTopic(commandHelp);
	}
	
	public CommandHelp getHelpPage() {
		return this.commandHelp;
	}
	
	public void setExecutor(CommandExecutor exe) {
		this.commandExecutor = exe;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		try {
			Queue<String> queue = new Queue<String>();
			for (String s : args)
				queue.push(s);
			return onCommand(sender, cmd, label, queue);
		} catch (Exception ex) {

			// Stacktrace in mc.
			sender.sendMessage(ChatColor.RED + "We have encountered an error!");
			sender.sendMessage(ChatColor.RED + ex.toString());
			sender.sendMessage(ChatColor.DARK_RED + "========== STACKTRACE ==========");
			for (StackTraceElement ste : ex.getStackTrace()) {
				sender.sendMessage(ChatColor.RED + ste.toString());
			}
			sender.sendMessage(ChatColor.DARK_RED + "============== END ==============");
		}
		return false;
	}

	public abstract boolean onCommand(CommandSender sender, Command cmd, String label, Queue<String> args) throws Exception;

	@Override
	public boolean execute(CommandSender sender, String commandLabel, String[] args) {
		if (commandExecutor != null) {
			
//			sender.sendMessage("Checking Permission: " + this.getPermission());
//			sender.sendMessage("Permission check result: " + sender.hasPermission(this.getPermission()));
			
			if (!sender.hasPermission(this.getPermission())) {
				sender.sendMessage(this.getPermissionMessage());
				return false;
			}

			if (!commandExecutor.onCommand(sender, this, commandLabel, args)) {
				sender.sendMessage(this.getPermissionMessage());
				return false;
			} else {
				return true;
			}
		}
		return false;
	}
	

	protected Player getPlayerByName(String name) {
		for (Player p : Bukkit.getOnlinePlayers()) if (p.getName().equalsIgnoreCase(name)) return p;
		return null;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String asd, String[] arg3) {
		// TODO Auto-generated method stub
		return null;
	}
}
