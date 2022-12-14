package net.idrisdev.mc.hoardofthedead.managers;

import net.idrisdev.mc.hoardofthedead.HoardOfTheDead;
import net.idrisdev.mc.hoardofthedead.tasks.GameStartCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class GameManager {
    private GameState gameState = GameState.WAITING;
    private final PlayerManager playerManager;
    private final HoardOfTheDead plugin;

    public GameManager(HoardOfTheDead plugin) {
        this.plugin = plugin;

        playerManager = new PlayerManager();
    }

    public GameState getGameState() {
        return gameState;
    }
    public void setGameState(GameState gameState){
        if(this.gameState == gameState){
            System.out.println("Cannot set the same gamestate over and over");
            return;
        }
        if(this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) {
            System.out.println("Cannot set GameState to Starting while game is active.");
            return;
        }

        if(this.gameState == GameState.ACTIVE && gameState == GameState.WAITING) {
            System.out.println("Cannot set GameState to Waiting while game is active.");
            return;
        }

        if(this.gameState != GameState.ACTIVE && (gameState == GameState.WON || gameState == GameState.LOSS)) {
            System.out.println("You cannot win or lose a game without the game being active.");
            return;
        }

        this.gameState = gameState;

        switch(gameState){
            case ACTIVE:
                Bukkit.broadcastMessage("Game is Active!");
                break;
            case STARTING:
                Bukkit.broadcastMessage("Game is Starting");
                this.playerManager.giveKits();
                Bukkit.getOnlinePlayers().forEach(ply -> {
                    Location location = ply.getLocation();
                    World world = location.getWorld();
                    ply.teleport(new Location(world, 215.00, 23.00, 297.00));
                });

                GameStartCountdownTask gameStartCountdownTask = new GameStartCountdownTask(this);
                gameStartCountdownTask.runTaskTimer(plugin, 8, 20);
                break;
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}
