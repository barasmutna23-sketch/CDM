package me.imxtheus.CustomDeathMessagesAndCombat.deathmessages;

import me.imxtheus.CustomDeathMessagesAndCombat.combat.CombatListener;
import me.imxtheus.CustomDeathMessagesAndCombat.combat.CombatManager;
import me.imxtheus.CustomDeathMessagesAndCombat.combat.CombatQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomDeathMessages extends JavaPlugin {

    private static CustomDeathMessages instance;

    private CombatManager combatManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        combatManager = new CombatManager(this, 20000);
        messageManager = new MessageManager(this);

        // registrace listenerů
        Bukkit.getPluginManager().registerEvents(new CombatListener(combatManager), this);
        Bukkit.getPluginManager().registerEvents(new CombatQuitListener(this, combatManager), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this, messageManager, combatManager), this);

        startActionBar();

        getLogger().info("CustomDeathMessages enabled");
    }

    public static CustomDeathMessages getInstance() {
        return instance;
    }

    public CombatManager getCombatManager() {
        return combatManager;
    }

    public MessageManager getMessageManager() {
        return messageManager;
    }

    private void startActionBar() {

        Bukkit.getScheduler().runTaskTimer(this, () -> {

            for (Player p : Bukkit.getOnlinePlayers()) {

                if (!combatManager.isInCombat(p)) continue;

                long seconds = (combatManager.getRemaining(p) + 999) / 1000;

                String msg = ChatColor.translateAlternateColorCodes('&',
                                getConfig().getString("combat-actionbar",
                                        "&cIn Combat | {SECONDS} Seconds Remaining"))
                        .replace("{SECONDS}", String.valueOf(seconds));

                p.sendActionBar(msg);
            }

        }, 0L, 10L);
    }
}