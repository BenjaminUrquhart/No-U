package net.benjaminurquhart.nou;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NOUBaseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!sender.hasPermission("nou.manage") && !sender.isOp()) {
			sender.sendMessage("Missing permissions: nou.manage");
			return false;
		}
		if(args.length == 0) {
			//sender.sendMessage(command.getUsage());
			return false;
		}
		switch(args[0]) {
		case "reload": {
			NOU.getInstance().loadConfig(sender);
			sender.sendMessage("Reloaded config (" + NOU.getInstance().getDisabledCommands().size() + " commands disabled)");
			return true;
		}
		case "list": {
			sender.sendMessage("Disabled commands:");
			NOU.getInstance().getDisabledCommands().stream().map(c -> "- " + c).forEach(sender::sendMessage);
			return true;
		}
		default: {
			//sender.sendMessage(command.getUsage());
			return false;
		}
		}
	}

}
