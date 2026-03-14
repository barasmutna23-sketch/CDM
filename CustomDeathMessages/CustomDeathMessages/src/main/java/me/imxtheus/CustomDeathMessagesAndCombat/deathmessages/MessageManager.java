package me.imxtheus.CustomDeathMessagesAndCombat.deathmessages;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Random;

public class MessageManager {

    private final JavaPlugin plugin;
    private final Random random = new Random();

    public MessageManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    // PvP zpráva
    public String getPvPMessage(Player killer, Player victim) {
        List<String> messages = plugin.getConfig().getStringList("global-pvp-death-messages");

        if (messages.isEmpty()) {
            return ChatColor.RED + victim.getName() + " died";
        }

        String msg = messages.get(random.nextInt(messages.size()));

        msg = msg.replace("%killer%", killer.getName())
                .replace("%victim%", victim.getName())
                .replace("%kill-weapon%", getWeaponName(killer));

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    // Mob zpráva
    public String handleEntityDeath(Player victim, Entity entity) {

        if (entity == null) return null;

        if (entity.getCustomName() != null &&
                plugin.getConfig().getBoolean("enable-custom-name-entity-messages")) {

            List<String> messages = plugin.getConfig().getStringList("custom-name-entity-messages");

            if (!messages.isEmpty()) {
                String msg = messages.get(random.nextInt(messages.size()));

                msg = msg.replace("%victim%", victim.getName())
                        .replace("%entity-name%", entity.getCustomName());

                return ChatColor.translateAlternateColorCodes('&', msg);
            }
        }

        String path = entity.getType().name().toLowerCase() + "-messages";
        List<String> messages = plugin.getConfig().getStringList(path);

        if (messages.isEmpty()) {
            return ChatColor.RED + victim.getName() + " died";
        }

        String msg = messages.get(random.nextInt(messages.size()));

        msg = msg.replace("%victim%", victim.getName());

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    // Environment death
    public String getDeathMessage(Player victim) {

        if (victim.getLastDamageCause() == null) {
            return ChatColor.RED + victim.getName() + " died";
        }

        String path = victim.getLastDamageCause().getCause().name().toLowerCase() + "-messages";
        List<String> messages = plugin.getConfig().getStringList(path);

        if (messages.isEmpty()) {
            return ChatColor.RED + victim.getName() + " died";
        }

        String msg = messages.get(random.nextInt(messages.size()));

        msg = msg.replace("%victim%", victim.getName());

        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    private String getWeaponName(Player killer) {

        ItemStack item = killer.getInventory().getItemInMainHand();

        if (item == null || item.getType().isAir()) {
            return "FISTS";
        }

        return item.getType().name();
    }

    public void broadcast(String message) {

        if (message == null) return;

        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(message));
    }
}