package net.idrisdev.mc.hoardofthedead;

import net.idrisdev.mc.hoardofthedead.commands.StartCommand;
import net.idrisdev.mc.hoardofthedead.listeners.BlockBreakListener;
import net.idrisdev.mc.hoardofthedead.listeners.EntityDeathListener;
import net.idrisdev.mc.hoardofthedead.managers.GameManager;
import net.idrisdev.mc.hoardofthedead.tasks.KeepNightTask;
import org.bukkit.plugin.java.JavaPlugin;

public final class HoardOfTheDead extends JavaPlugin {
    public final int MAX_WAVES = 10;
    private GameManager gameManager;

    public GameManager getGameManager() {
        return gameManager;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Hoard of the Dead Initialized.");
        this.gameManager = new GameManager(this);
        getCommand("start").setExecutor(new StartCommand(this));
        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDeathListener(this), this);

        // keep it nighttime
        new KeepNightTask().runTaskTimer(this, 0, 100L);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Hoard of the Dead De-Initialized.");
    }


}
