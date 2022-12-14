package net.idrisdev.mc.hoardofthedead.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    private void onBlockBreak(BlockBreakEvent e){
        e.setCancelled(true);
    }
}
