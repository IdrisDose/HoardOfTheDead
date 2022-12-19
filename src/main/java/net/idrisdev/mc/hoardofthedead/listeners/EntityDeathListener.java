package net.idrisdev.mc.hoardofthedead.listeners;

import net.idrisdev.mc.hoardofthedead.HoardOfTheDead;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
    private final HoardOfTheDead plugin;

    public EntityDeathListener(HoardOfTheDead plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity entity = event.getEntity();

        if (entity.getType() == EntityType.ZOMBIE) {
            plugin.getGameManager().getWaveManager().zombieKilled(entity);
        } else if (entity.getType() == EntityType.PLAYER) {
            plugin.getGameManager().playerKilled((Player) entity);
        }
    }
}
