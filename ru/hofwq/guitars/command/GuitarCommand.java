package ru.hofwq.guitars.command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import ru.hofwq.guitars.Guitars;
import ru.hofwq.guitars.utils.Sounds;

public class GuitarCommand implements CommandExecutor, TabCompleter{
    private static Guitars plugin = Guitars.getPlugin();
    private static Map<Player, String> playingSounds = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Только игрокам.");
            return true;
        }

        Player player = (Player) sender;

        if(args.length == 0) {
        	player.sendMessage(ChatColor.RED + "Укажите что-нибудь.");
        	return true;
        }
        
        if (args.length > 0 && args[0].equalsIgnoreCase("stop")) {
            if (playingSounds.containsKey(player)) {
                String soundToStop = playingSounds.get(player);
                
                for (Player p : Bukkit.getOnlinePlayers()) {
                    p.stopSound("minecraft:my_sounds." + soundToStop.toLowerCase());
                }
                
                playingSounds.remove(player);
                player.sendMessage(ChatColor.GREEN + "Звук остановлен.");
            } else {
                player.sendMessage(ChatColor.RED + "Нет звука для остановки.");
            }
            return true;
        }

        ItemStack itemInOffHand = player.getInventory().getItemInOffHand();
        int guitarDistance = plugin.getConfig().getInt("Guitar.Distance");

        if(itemInOffHand != null && itemInOffHand.getItemMeta() != null && itemInOffHand.getItemMeta().getDisplayName().toLowerCase().contains("гитара")) {
            if (playingSounds.containsKey(player)) {
                player.sendMessage(ChatColor.RED + "Вы не можете использовать эту команду, пока не закончится текущий звук.");
                return true;
            }

            for(Player p : Bukkit.getOnlinePlayers()) {
                if(p.getLocation().distance(player.getLocation()) <= guitarDistance) {
                    Sounds.playSound(args[0], p);
                }
            }

            playingSounds.put(player, args[0]);

            new BukkitRunnable() {
                @Override
                public void run() {
                    playingSounds.remove(player);
                }
            }.runTaskLater(plugin, 20L * Sounds.valueOf(args[0]).getDuration());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.stream(Sounds.values()).map(Enum::name).map(String::toLowerCase).collect(Collectors.toList());
        }

        return null;
    }
}
