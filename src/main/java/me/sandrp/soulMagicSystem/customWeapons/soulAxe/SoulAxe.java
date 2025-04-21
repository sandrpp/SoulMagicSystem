package me.sandrp.soulMagicSystem.customWeapons.soulAxe;

import me.sandrp.soulMagicSystem.customWeapons.ingredients.SoulCrystal;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class SoulAxe {
    private static final String ITEM_ID = "soul_axe";
    private static NamespacedKey key;

    public static ItemStack createItem() {
        ItemStack item = new ItemStack(Material.GOLDEN_AXE);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§fSoul Axe");
        meta.setLore(List.of("§9Combat"));
        meta.setCustomModelData(1);
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, ITEM_ID);
        meta.removeAttributeModifier(Attribute.ATTACK_DAMAGE);

        item.setItemMeta(meta);
        return item;
    }

    public static boolean isSoulAxe(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer().has(key, PersistentDataType.STRING);
    }

    public static void registerCraftingRecipe(JavaPlugin plugin) {
        ItemStack result = createItem();
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, ITEM_ID), result);
        recipe.shape(
                " MM",
                " SM",
                " S "
        );
        recipe.setIngredient('M', new RecipeChoice.ExactChoice(SoulCrystal.createItem()));
        recipe.setIngredient('S', Material.STICK);

        plugin.getServer().addRecipe(recipe);
    }

    public static void register(JavaPlugin plugin) {
        key = new NamespacedKey(plugin, ITEM_ID);;
    }
}
