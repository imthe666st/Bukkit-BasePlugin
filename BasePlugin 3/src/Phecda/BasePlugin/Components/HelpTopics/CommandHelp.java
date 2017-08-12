package Phecda.BasePlugin.Components.HelpTopics;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.help.HelpTopic;

import Phecda.BasePlugin.Components.PluginCommand;

public class CommandHelp extends HelpTopic {
	
	private PluginCommand<?> cmd;
	
	public CommandHelp(PluginCommand<?> cmd) {
		this.cmd = cmd;
		
		this.name = "/" + cmd.getCommandName();
		this.shortText = cmd.getCommandDescription();
		this.fullText = "";
		if (!cmd.getDescription().isEmpty())  this.fullText += "\n" + ChatColor.GOLD + "Description: " + ChatColor.WHITE + cmd.getDescription();
		if (!cmd.getUsage().isEmpty()) this.fullText += "\n" + ChatColor.GOLD + "Usage: " + ChatColor.WHITE + cmd.getUsage();
		if (cmd.getAliases().size() > 0) this.fullText += "\n" + ChatColor.GOLD + "Aliases: " + ChatColor.WHITE + String.join(", ", cmd.getAliases());
		
		if (this.fullText.length() > 2) this.fullText = this.fullText.substring(1);
		
		this.amendedPermission = cmd.getPermission();
		
	}
	
	@Override
	public boolean canSee(CommandSender player) {
		if (cmd.getPermission().isEmpty()) return true;
		return player.isOp() || player.hasPermission(cmd.getPermission());
	}

}
