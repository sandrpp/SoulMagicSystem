package me.sandrp.soulMagicSystem.customFood.soulMeal.ingredients;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SoulBlood {
    private static final String ITEM_ID = "soul_blood";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GUNPOWDER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§dSoul Blood");
        meta.setLore(List.of("§9Ingredients"));
        meta.setCustomModelData(3);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);

        item.setItemMeta(meta);
        return item;
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);
    }
}
