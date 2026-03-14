package me.imxtheus.CustomDeathMessagesAndCombat.combat;

import me.imxtheus.CustomDeathMessagesAndCombat.deathmessages.CustomDeathMessages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatQuitListener implements Listener {

    private final CustomDeathMessages plugin;
    private final CombatManager combatManager;

    public CombatQuitListener(CustomDeathMessages plugin, CombatManager manager) {
        this.plugin = plugin;
        this.combatManager = manager;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

        Player player = e.getPlayer();

        if (!combatManager.isInCombat(player)) return;

        // zruší combat
        combatManager.remove(player);

        // zabije hráče
        player.setHealth(0.0);

        // death message z config.yml
        String msg = ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString(
                        "combat-log-message",
                        "&3%victim% &4tried to escape combat and died"
                ).replace("%victim%", player.getName())
        );

        Bukkit.broadcastMessage(msg);
    }
}