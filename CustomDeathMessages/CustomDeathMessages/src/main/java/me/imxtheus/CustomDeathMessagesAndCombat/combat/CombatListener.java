package me.imxtheus.CustomDeathMessagesAndCombat.combat;

import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    private final CombatManager combatManager;

    public CombatListener(CombatManager combatManager) {
        this.combatManager = combatManager;
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {

        if (!(e.getEntity() instanceof Player)) return;

        Player victim = (Player) e.getEntity();
        Player attacker = null;

        if (e.getDamager() instanceof Player) {
            attacker = (Player) e.getDamager();
        }

        if (e.getDamager() instanceof Projectile p) {
            if (p.getShooter() instanceof Player) {
                attacker = (Player) p.getShooter();
            }
        }

        if (attacker != null) {

            combatManager.tag(attacker);
            combatManager.tag(victim);
        }

        if (e.getDamager() instanceof EnderCrystal) {
            combatManager.tag(victim);
        }
    }
}