package me.imxtheus.CustomDeathMessagesAndCombat.combat;

import me.imxtheus.CustomDeathMessagesAndCombat.deathmessages.CustomDeathMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandBlocker implements Listener {

    private final CustomDeathMessages plugin;

    public CommandBlocker(CustomDeathMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player player = e.getPlayer();

        // pokud hráč není v combat, nic neblokujeme
        if (!plugin.getCombatManager().isInCombat(player)) return;

        // Seznam zablokovaných příkazů
        List<String> blockedCommands = List.of(
                "gamemode",
                "tp",
                "tpa",
                "tpaccept",
                "1v1"
        );

        // Extrahuje samotný příkaz bez /
        String cmd = e.getMessage().split(" ")[0].replace("/", "").toLowerCase();

        if (blockedCommands.contains(cmd)) {
            e.setCancelled(true);
            player.sendMessage(plugin.getConfig().getString("messages.commandDisabledDuringCombat", "&cYou cannot use this command during combat!").replace("&", "§"));
        }
    }
}
