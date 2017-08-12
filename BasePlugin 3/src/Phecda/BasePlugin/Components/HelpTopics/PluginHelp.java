package Phecda.BasePlugin.Components.HelpTopics;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

import Phecda.BasePlugin.BasePlugin;
import Phecda.BasePlugin.Components.PluginCommand;

public class PluginHelp extends HelpTopic {
	
	ArrayList<PluginCommand<?>> Commands = new ArrayList<PluginCommand<?>>();
	protected BasePlugin<?> plugin;
	
	
	public PluginHelp(BasePlugin<?> plugin) {
		this.plugin = plugin;
		this.name = plugin.getName();
		this.shortText = plugin.getDescription().getDescription();
	}
	
	@Override
    public String getFullText(CommandSender forWho) {
//		return forWho.getName();
		
		forWho.sendMessage("Plugin Name: " + plugin.getName());
		
		String s = ChatColor.GRAY + "Below is a list of all " + this.name + " commands:\n";
		
		for (PluginCommand<?> c : Commands) {
			if (c.getHelpPage().canSee(forWho)) s += ChatColor.GOLD + "/" + c.getName() + ": " + ChatColor.WHITE + c.getCommandDescription() + "\n";
		}
		
		return s;
	}
	
	@Override
	public boolean canSee(CommandSender player) {
		if (Commands.size() <= 0) return false;
		if (player.isOp()) return true;
		
		for (PluginCommand<?> cmd : Commands) {
			if (cmd.getHelpPage().canSee(player)) return true;
		}
		return false;
	}
	
	public boolean addCommand(PluginCommand<?> cmd) {
		return Commands.add(cmd);
	}
	
	public boolean removeCommand(PluginCommand<?> cmd) {
		return Commands.remove(cmd);
	}
	
	

}
