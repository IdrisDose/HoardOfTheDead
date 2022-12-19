package net.idrisdev.mc.hoardofthedead.tasks;

import net.idrisdev.mc.hoardofthedead.managers.GameManager;
import net.idrisdev.mc.hoardofthedead.managers.GameState;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class NextWaveCountdownTask extends BukkitRunnable {
    private final GameManager gameManager;
    private int timeLeft = 10;

    public NextWaveCountdownTask(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @Override
    public void run() {
        if (gameManager.getGameState() != GameState.NEXT_WAVE) {
            cancel();
            System.out.println("You cannot proceed to the next wave without the wave being finished.");
            return;
        }

        timeLeft--;
        if (timeLeft <= 0) {
            cancel();
            gameManager.setGameState(GameState.ACTIVE);
            return;
        }

        Bukkit.broadcastMessage(timeLeft + " until wave starts!");
    }
}
