package net.benjaminurquhart.nou;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;

public class NOUListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerCommand(PlayerCommandPreprocessEvent event) {
		this.processCommand(event.getPlayer(), event.getMessage(), event);
	}
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onConsoleCommand(ServerCommandEvent event) {
		this.processCommand(event.getSender(), event.getCommand(), event);
	}
	private void processCommand(CommandSender sender, String command, Cancellable event) {
		String cmd = command.split(" ")[0].replace("/", "");
		if(NOU.getInstance().isDisabled(cmd)) {
			sender.sendMessage("This command is disabled. Contact your server owner for more details.");
			event.setCancelled(true);
		}
	}
}
