package ru.hofwq.guitars.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import ru.hofwq.guitars.Guitars;
import ru.hofwq.guitars.utils.Utils;

public class EventListener implements Listener{
	private static Guitars plugin = Guitars.getPlugin();
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
		int guitarDistance = plugin.getConfig().getInt("Guitar.Distance");
		Action action = e.getAction();
		Action actionLA = Action.LEFT_CLICK_AIR;
		Action actionLB = Action.LEFT_CLICK_BLOCK;
		Action actionRA = Action.RIGHT_CLICK_AIR;
		Action actionRB = Action.RIGHT_CLICK_BLOCK;
		
		if(itemInOffHand != null && itemInOffHand.getItemMeta() != null && itemInOffHand.getItemMeta().getDisplayName().toLowerCase().contains("гитара")) {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getLocation().distance(player.getLocation()) <= guitarDistance) {
					if((action == actionLA || action == actionLB) && player.isSneaking()) {
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 1L, 1L);
						Utils.applyPlayerCooldown(player);
					} else if((action == actionRA || action == actionRB) && player.isSneaking()) {
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_XYLOPHONE, 1L, 1L);
						Utils.applyPlayerCooldown(player);
					} else if((action == actionLA || action == actionLB) && !player.isSneaking()) {
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 1L, 1L);
						Utils.applyPlayerCooldown(player);
					} else if((action == actionRA || action == actionRB) && !player.isSneaking()) {
						p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1L, 1L);
						Utils.applyPlayerCooldown(player);
					}
				}
			}
		}
	}
}
