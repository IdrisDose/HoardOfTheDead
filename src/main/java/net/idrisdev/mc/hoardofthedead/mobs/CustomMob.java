package net.idrisdev.mc.hoardofthedead.mobs;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public enum CustomMob {
    TANK_ZOMBIE(ChatColor.YELLOW + "Tank Zombie", 150D, EntityType.ZOMBIE),
    STRONG_ZOMBIE(ChatColor.AQUA + "Strong Zombie", 50D, EntityType.ZOMBIE),
    ANGRY_ZOMBIE( ChatColor.DARK_RED + "Angry Zombie", 10D, EntityType.ZOMBIE);
    private final String name;
    private final double maxHealth;
    private final EntityType type;

    CustomMob(String name, double maxHealth, EntityType type) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public LivingEntity spawn (Location loc){
        LivingEntity entity = (LivingEntity) loc.getWorld().spawnEntity(loc, type);
        entity.setCustomNameVisible(true);
        entity.setCustomName(name);
        entity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        entity.setHealth(maxHealth);


        return entity;
    }
}
