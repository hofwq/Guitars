package ru.hofwq.guitars.config;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import ru.hofwq.guitars.Guitars;

public class Config{
	Guitars plugin = Guitars.getPlugin();
	private FileConfiguration config;
	
	public void checkConfig() {
	    File configFolder = plugin.getDataFolder();
	    if (!configFolder.exists()) {
	        configFolder.mkdirs();
	    }
	    
	    File configFile = new File(configFolder, "config.yml");

	    config = new YamlConfiguration();
	    
	    if(!configFile.exists()) {
	        plugin.log.info("Config is not exists, creating.");
	        
	        try {
	            configFile.createNewFile();
	        } catch (IOException ex) {
	            ex.printStackTrace();
	        }
	    }
	    
	    try {
	        config.load(configFile);
	    } catch (IOException | InvalidConfigurationException e) {
	        e.printStackTrace();
	    }
	    
	    createStringLists();
	}
	
	private void createStringLists() {
		File configFile = new File(plugin.getDataFolder(), "config.yml");
		
		if(!config.contains("Guitar.Distance")){
		    config.set("Guitar.Distance", 100);
		}
		
		saveConfig(configFile);
	}
	
    private void saveConfig(File configFile) {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
