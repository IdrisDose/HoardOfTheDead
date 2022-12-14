package net.idrisdev.mc.hoardofthedead;

import org.bukkit.plugin.java.JavaPlugin;

public final class HoardOfTheDead extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("Hoard of the Dead Initialized.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("Hoard of the Dead De-Initialized.");
    }


}
