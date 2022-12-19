package net.idrisdev.mc.hoardofthedead.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Monster;

import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class WaveManager {
    private final GameManager gameManager;
    private final World currWorld;
    private final Location[] spawnPoints;
    private final HashMap<UUID, Entity> enemies = new HashMap<>();
    private int waveNumber = 0;

    public WaveManager(GameManager gameManager) {
        this.gameManager = gameManager;
        this.currWorld = Bukkit.getWorld("world");
        this.spawnPoints = new Location[]{
                new Location(currWorld, 143.53, 23.00, 294.53),
                new Location(currWorld, 147.102, 23.00, 286.185),
                new Location(currWorld, 161.706, 23.00, 279.790),
                new Location(currWorld, 177.072, 23.00, 279.363),
                new Location(currWorld, 177.295, 23.00, 318.961),
                new Location(currWorld, 184.299, 23.00, 319.537),
                new Location(currWorld, 155.429, 23.00, 311.428),
                new Location(currWorld, 155.429, 23.00, 311.428),
        };
    }

    public int getWaveNumber() {
        return waveNumber;
    }

    public void startWave() {
        waveNumber += 1;

        gameManager.getAlivePlayers().forEach((uuid, ply) -> {
            AttributeInstance attrMaxHealth = ply.getAttribute(Attribute.GENERIC_MAX_HEALTH);
            ply.setHealth(attrMaxHealth.getValue());
        });

        int numOfMobs = getMobCount(waveNumber);
        Random random = new Random();
        for (int i = 0; i < numOfMobs; i++) {

            // Spawn zombie in random location
            Location randomSpawn = spawnPoints[random.nextInt(spawnPoints.length)];
            Entity zombie = currWorld.spawnEntity(randomSpawn, EntityType.ZOMBIE);

            // Give zombie a name
            zombie.setCustomName(ChatColor.RED + "Angry Zombie");
            zombie.setCustomNameVisible(true);

            enemies.put(zombie.getUniqueId(), zombie);

            // Set random competing player as target
//         Monster mob = (Monster) zombie;
//         Random rd = new Random();
//         Object[] values = gameManager.getAlivePlayers().values().toArray();
//         LivingEntity ply = (LivingEntity) values[rd.nextInt(values.length)];;
//         mob.setTarget(ply);
        }
    }

    private int getMobCount(int waveNumber) {
        int mobCount = waveNumber * 4;
        if (waveNumber > 10) {
            mobCount = 40 + (waveNumber % 10);
        }

        return mobCount;
    }

    public void zombieKilled(Entity zombie) {
        if(this.gameManager.getGameState() != GameState.ACTIVE) return;

        enemies.remove(zombie.getUniqueId());

        if (this.enemies.isEmpty()) {
            if ((this.getWaveNumber() + 1) > this.gameManager.getPlugin().MAX_WAVES) {
                this.gameManager.setGameState(GameState.WON);
                return;
            }
            this.gameManager.setGameState(GameState.NEXT_WAVE);
        }
    }

    public void waveLost() {
        for ( Object zombie : enemies.values().toArray()){
            Monster mob = (Monster) zombie;
            mob.setHealth(0);
            enemies.remove(mob.getUniqueId());
        }

        this.waveNumber = 0;
    }
}
