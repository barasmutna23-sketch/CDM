package me.imxtheus.CustomDeathMessagesAndCombat.combat;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CombatManager {

    private final JavaPlugin plugin;
    private final long combatTime;

    private final Map<UUID, Long> combat = new HashMap<>();

    public CombatManager(JavaPlugin plugin, long combatTime) {
        this.plugin = plugin;
        this.combatTime = combatTime;
    }

    public void tag(Player player) {
        combat.put(player.getUniqueId(), System.currentTimeMillis() + combatTime);
    }

    public boolean isInCombat(Player player) {

        if (!combat.containsKey(player.getUniqueId())) return false;

        return combat.get(player.getUniqueId()) > System.currentTimeMillis();
    }

    public long getRemaining(Player player) {

        if (!combat.containsKey(player.getUniqueId())) return 0;

        return combat.get(player.getUniqueId()) - System.currentTimeMillis();
    }

    public void remove(Player player) {
        combat.remove(player.getUniqueId());
    }
}