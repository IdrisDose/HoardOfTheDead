package net.idrisdev.mc.hoardofthedead.tasks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class KeepNightTask extends BukkitRunnable {
    World world;

    public KeepNightTask() {
        this.world = Bukkit.getWorld("world");
    }

    @Override
    public void run() {
        world.setTime(13000L);
    }
}
