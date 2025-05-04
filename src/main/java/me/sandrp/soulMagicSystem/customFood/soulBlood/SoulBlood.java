package me.sandrp.soulMagicSystem.customFood.soulBlood;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SoulBlood {
    private static final String ITEM_ID = "soul_blood";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.POTION);
        ItemMeta meta = item.getItemMeta();
        PotionMeta potionMeta = (PotionMeta) meta;

        potionMeta.setDisplayName("§fSoul Blood");
        potionMeta.setLore(List.of("§9Potion"));

        potionMeta.setColor(Color.fromBGR(6, 107, 148));
        potionMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);

        item.setItemMeta(potionMeta);
        return item;
    }

    public static boolean isSoulBlood(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);;
    }
}
