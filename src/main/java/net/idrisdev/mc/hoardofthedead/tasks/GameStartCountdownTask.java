package net.idrisdev.mc.hoardofthedead.tasks;

import net.idrisdev.mc.hoardofthedead.managers.GameManager;
import net.idrisdev.mc.hoardofthedead.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class GameStartCountdownTask extends BukkitRunnable {
    private int timeLeft = 10;
    private final GameManager gameManager;

    public GameStartCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run(){
        if(gameManager.getGameState() != GameState.STARTING){
            cancel();
            System.out.println("You cannot start a countdown without the state being starting.");
            return;
        }
        timeLeft --;
        if(timeLeft <= 0){
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }

        Bukkit.broadcastMessage(timeLeft + " until game starts!");
    }
}
