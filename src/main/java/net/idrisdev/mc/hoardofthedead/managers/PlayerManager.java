package net.idrisdev.mc.hoardofthedead.managers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerManager {
    public void giveKits() {
        Bukkit.getOnlinePlayers().stream().forEach(this::giveKit);
    }

    private ItemStack generateSwordOfTruth() {
        ItemStack swordOfTruth = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta itemMeta = swordOfTruth.getItemMeta();
        itemMeta.addEnchant(Enchantment.SWEEPING_EDGE, 3, true);
        itemMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        itemMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
        itemMeta.setUnbreakable(true);

        itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(ChatColor.DARK_RED + "Sword of Truth");
        swordOfTruth.setItemMeta(itemMeta);

        return swordOfTruth;
    }

    private void giveKit(Player player) {
        if (player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.SPECTATOR) {
            player.setGameMode(GameMode.SURVIVAL);
        }

        ItemStack swordOfTruth = generateSwordOfTruth();

        if (!player.getInventory().contains(swordOfTruth)) {
            player.getInventory().addItem(swordOfTruth);
        }
    }
}
