package net.benjaminurquhart.nou;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class NOU extends JavaPlugin {
	
	private Set<String> disabledCommands;

	private static NOU self;
	
	public static NOU getInstance() {
		return self;
	}
	@Override
	public void onEnable() {
		self = this;
		this.loadConfig(null);
		this.getCommand("nou").setExecutor(new NOUBaseCommand());
		this.getServer().getPluginManager().registerEvents(new NOUListener(), this);
	}
	public void loadConfig(CommandSender sender) {
		File file = new File(this.getDataFolder(), "config.yml");
		if(!file.exists()) {
			this.saveDefaultConfig();
		}
		this.reloadConfig();
		FileConfiguration config = this.getConfig();
		disabledCommands = new HashSet<>(config.getStringList("disabled-commands"));
		
		/*
		 * You can't tell me what to do (05/12/2020):
		 * @Kody Simpson what if the plugin disables its own reload command
		 * 
		 * Kody Simpson (05/12/2020):
		 * a black hole opens
		 * 
		 * You can't tell me what to do (05/12/2020):
		 * cool
		 */
		int count = config.getInt("black-hole-warnings", 0);
		if(this.isDisabled("nou")) {
			String message = null;
			if(count > 1) {
				message = "Cannot disable the nou command. I warned you " + count + " times before, count yourself lucky that you're getting another chance.";
			}
			else if(count > 0) {
				message = "Cannot disable the nou command. I warned you once before, count yourself lucky that you're getting another chance.";
			}
			else {
				message = "Cannot disable the nou command. A black hole will appear if you try again.";
			}
			if(sender != null) {
				sender.sendMessage(message);
			}
			this.getLogger().warning(message);
			config.set("black-hole-warnings", count+1);
			disabledCommands.remove("nou");
		}
		else if(count > 0) {
			if(sender != null) {
				sender.sendMessage("You have heeded my warning. Black hole avoided.");
			}
			this.getLogger().info("You have heeded my warning. Black hole avoided.");
			config.set("black-hole-warnings", 0);
		}
		try {
			config.save(file);
		}
		catch(Exception e) {
			e.printStackTrace();
			if(sender != null) {
				sender.sendMessage(e.toString());
			}
		}
	}
	public Set<String> getDisabledCommands() {
		return Collections.unmodifiableSet(disabledCommands);
	}
	public boolean isDisabled(String command) {
		return disabledCommands.contains(command);
	}
}
