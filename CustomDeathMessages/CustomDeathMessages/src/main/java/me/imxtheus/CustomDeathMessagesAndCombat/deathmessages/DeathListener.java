package me.imxtheus.CustomDeathMessagesAndCombat.deathmessages;

import me.imxtheus.CustomDeathMessagesAndCombat.combat.CombatManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DeathListener implements Listener {

    private final CustomDeathMessages plugin;
    private final MessageManager messageManager;
    private final CombatManager combatManager;

    public DeathListener(CustomDeathMessages plugin, MessageManager manager, CombatManager combatManager) {
        this.plugin = plugin;
        this.messageManager = manager;      // ← opraveno (dříve se ignoroval!)
        this.combatManager = combatManager;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent e) {

        Player victim = e.getEntity();
        Player killer = victim.getKiller();

        String message;

        if (killer != null) {
            // PvP kill → používá global-pvp-death-messages + %kill-weapon%
            message = messageManager.getPvPMessage(killer, victim);

        } else {
            // Zjistíme, jestli to byl mob nebo environment (fall, lava, atd.)
            EntityDamageEvent lastDamage = victim.getLastDamageCause();
            Entity damager = (lastDamage != null) ? lastDamage.getEntity() : null;

            if (damager != null && !(damager instanceof Player)) {
                // Mob / custom entity kill
                message = messageManager.handleEntityDeath(victim, damager);
            } else {
                // Fall, lava, fire, drowning, atd.
                message = messageManager.getDeathMessage(victim);
            }
        }

        // fallback kdyby něco selhalo
        if (message == null || message.isEmpty()) {
            message = ChatColor.RED + victim.getName() + " died";
        }

        e.setDeathMessage(message);

        // reset combat (původní funkce zůstává)
        combatManager.remove(victim);
        if (killer != null) combatManager.remove(killer);
    }
}