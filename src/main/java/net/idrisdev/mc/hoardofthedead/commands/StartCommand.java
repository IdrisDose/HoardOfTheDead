package net.idrisdev.mc.hoardofthedead.commands;

import net.idrisdev.mc.hoardofthedead.HoardOfTheDead;
import net.idrisdev.mc.hoardofthedead.managers.GameState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StartCommand implements CommandExecutor {
    private final HoardOfTheDead plugin;

    public StartCommand(HoardOfTheDead plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        plugin.getGameManager().setGameState(GameState.STARTING);
        return true;
    }
}
