package me.sandrp.soulMagicSystem.customWeapons.gravityGun.ingredients;

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

public class GravityCrystal {
    private static final String ITEM_ID = "gravity_crystal";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GUNPOWDER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§fGravity Crystal");
        meta.setLore(List.of("§9Utility"));
        meta.setCustomModelData(4);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);

        item.setItemMeta(meta);
        return item;
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
        recipe.setIngredient('M', Material.PHANTOM_MEMBRANE);
        recipe.setIngredient('S', Material.LODESTONE);

        plugin.getServer().addRecipe(recipe);
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);;
    }
}
