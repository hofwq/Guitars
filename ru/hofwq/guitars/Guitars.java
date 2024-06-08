package ru.hofwq.guitars;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import ru.hofwq.guitars.command.GuitarCommand;
import ru.hofwq.guitars.config.Config;
import ru.hofwq.guitars.events.EventListener;

public class Guitars extends JavaPlugin{
	private static Guitars plugin;
	public Logger log = getLogger();
	
	@Override
	public void onEnable() {
		plugin = this;
		
		//Initializing config
		Config createConfig = new Config();
		createConfig.checkConfig();
		
		//Registering events
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		
		//Registering commands
		GuitarCommand executor = new GuitarCommand();
		getCommand("guitar").setExecutor(new GuitarCommand());
		getCommand("guitar").setTabCompleter(executor);
		
		log.info("Guitars enabled.");
	}
	
	@Override
	public void onDisable() {
		plugin = null;
		
		log.info("Guitars disabled.");
	}
	
	public static Guitars getPlugin() {
		return plugin;
	}
}
