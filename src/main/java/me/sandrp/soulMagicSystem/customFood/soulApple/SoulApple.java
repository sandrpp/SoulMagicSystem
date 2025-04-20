package me.sandrp.soulMagicSystem.customFood.soulApple;

import me.sandrp.soulMagicSystem.customFood.soulApple.ingredients.SoulBlood;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.ingredients.LuminaCrystal;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SoulApple {
    private static final String ITEM_ID = "soul_apple";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.ENCHANTED_GOLDEN_APPLE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§fSoul Apple");
        meta.setLore(List.of("§9Food"));

        meta.setCustomModelData(1);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isSoulApple(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static void registerCraftingRecipe(JavaPlugin plugin) {
        ItemStack result = createItem();
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, ITEM_ID), result);

        recipe.shape(
                " M ",
                "MSM",
                " M "
        );

        // M = Custom Ingredient, S = Stick
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(SoulBlood.createItem()));
        recipe.setIngredient('S', Material.GOLDEN_APPLE);

        plugin.getServer().addRecipe(recipe);
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);;
    }
}
