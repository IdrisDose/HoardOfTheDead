package net.idrisdev.mc.hoardofthedead.managers;

import net.idrisdev.mc.hoardofthedead.HoardOfTheDead;
import net.idrisdev.mc.hoardofthedead.tasks.GameStartCountdownTask;
import net.idrisdev.mc.hoardofthedead.tasks.NextWaveCountdownTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class GameManager {
    private final PlayerManager playerManager;
    private final HoardOfTheDead plugin;
    private final WaveManager waveManager;
    private final HashMap<UUID, Player> alivePlayers = new HashMap<>();
    private GameState gameState = GameState.WAITING;

    public GameManager(HoardOfTheDead plugin) {
        this.plugin = plugin;
        playerManager = new PlayerManager();
        waveManager = new WaveManager(this);
    }

    public WaveManager getWaveManager() {
        return waveManager;
    }

    public HashMap<UUID, Player> getAlivePlayers() {
        return alivePlayers;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        if (this.gameState == gameState) {
            System.out.println("Cannot set the same gamestate over and over");
            return;
        }
        if (this.gameState == GameState.ACTIVE && gameState == GameState.STARTING) {
            System.out.println("Cannot set GameState to Starting while game is active.");
            return;
        }

        if (this.gameState == GameState.ACTIVE && gameState == GameState.WAITING) {
            System.out.println("Cannot set GameState to Waiting while game is active.");
            return;
        }

        if (this.gameState != GameState.ACTIVE && (gameState == GameState.WON || gameState == GameState.LOSS)) {
            System.out.println("You cannot win or lose a game without the game being active.");
            return;
        }

        this.gameState = gameState;

        switch (gameState) {
            case ACTIVE:
                Bukkit.broadcastMessage("Game is Active!");
                waveManager.startWave();
                break;
            case STARTING:
                Bukkit.broadcastMessage("Game is Starting");
                this.playerManager.giveKits();
                Bukkit.getOnlinePlayers().forEach(ply -> {
                    Location location = ply.getLocation();
                    World world = location.getWorld();
                    ply.teleport(new Location(world, 178.471, 23.00, 299.348));
                    alivePlayers.put(ply.getUniqueId(), ply);
                });

                GameStartCountdownTask gameStartCountdownTask = new GameStartCountdownTask(this);
                gameStartCountdownTask.runTaskTimer(plugin, 8, 20);
                break;
            case NEXT_WAVE:
                this.alivePlayers.forEach((uuid, ply) -> ply.sendTitle(ChatColor.GREEN + "Wave Complete", ChatColor.BLUE + "Congratulations!",0, 12, 0));

                NextWaveCountdownTask nextWaveCountdownTask = new NextWaveCountdownTask(this);
                nextWaveCountdownTask.runTaskTimer(plugin, 8, 20);
                break;
            case WON:
                this.alivePlayers.forEach((uuid, ply) -> ply.sendTitle(ChatColor.GREEN + "Congratulations!", ChatColor.BLUE + "You have won against the hoard. Type /start to go again!",0, 12, 0));

                break;
            case LOSS:
                Bukkit.broadcastMessage("Oof! You have lost against the hoard. Type /start to go again");
                waveManager.waveLost();
                break;
        }
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public void playerKilled(Player p) {
        if(this.getGameState() != GameState.ACTIVE) return;

        Bukkit.broadcastMessage("A " + ChatColor.GOLD + "Champion" + ChatColor.RESET + " has died!");
        alivePlayers.remove(p.getUniqueId());
        if (alivePlayers.isEmpty()) {
            this.setGameState(GameState.LOSS);
        }
    }

    public HoardOfTheDead getPlugin() {
        return this.plugin;
    }
}
