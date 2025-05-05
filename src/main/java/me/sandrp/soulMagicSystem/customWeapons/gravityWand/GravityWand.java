package me.sandrp.soulMagicSystem.customWeapons.gravityWand;

import me.sandrp.soulMagicSystem.customWeapons.ingredients.LuminaCrystal;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class GravityWand {
    private static final String ITEM_ID = "gravity_wand";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§fGravity Wand");
        meta.setLore(List.of("§9Utility"));
        meta.setCustomModelData(1);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isGravityWand(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static void registerCraftingRecipe(JavaPlugin plugin) {
        ItemStack result = createItem();
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, ITEM_ID), result);

        recipe.shape(
                " M ",
                " S ",
                " S "
        );

        // M = Custom Ingredient, S = Stick
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(LuminaCrystal.createItem(1)));
        recipe.setIngredient('S', Material.STICK);

        plugin.getServer().addRecipe(recipe);
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);;
    }
}
