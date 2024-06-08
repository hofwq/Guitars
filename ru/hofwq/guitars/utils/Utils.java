package ru.hofwq.guitars.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import ru.hofwq.guitars.Guitars;

public class Utils {
	public static List<UUID> playerCooldown = new ArrayList<>();
	public static Guitars plugin = Guitars.getPlugin();
	
	public static void applyPlayerCooldown(Player player) {
		if(!playerCooldown.contains(player.getUniqueId())) {
			playerCooldown.add(player.getUniqueId());
			
			removePlayerFromList(1, player);
		}
	}
	
	private static void removePlayerFromList(int delaySeconds, Player player) {
	    int delayTicks = delaySeconds * 20;
	    
	    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
	        @Override
	        public void run() {
	        	if(playerCooldown.contains(player.getUniqueId())) {
	    			playerCooldown.remove(player.getUniqueId());
	        	}
	        }
	    }, delayTicks);
	}
}
