package me.sandrp.soulMagicSystem.utils;

import me.sandrp.soulMagicSystem.customFood.soulBlood.SoulBlood;
import me.sandrp.soulMagicSystem.customWeapons.gravityWand.GravityWand;
import me.sandrp.soulMagicSystem.customWeapons.ingredients.LuminaCrystal;
import me.sandrp.soulMagicSystem.customWeapons.luminaSword.LuminaSword;
import me.sandrp.soulMagicSystem.customWeapons.soulAxe.SoulAxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdminCommand extends Command {

    public AdminCommand(@NotNull String name) {
        super(name);
    }

    public AdminCommand(){
        this("magicitem");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)){
            return false;
        }
        if (args.length != 1) {
            player.sendMessage("§cUsage: /crystal <type>");
            return false;
        }
        if (!player.hasPermission("soulmagic.admin") && !player.isOp()) {
            player.sendMessage("§cYou do not have permission to use this command.");
            return false;
        }
        switch (args[0]) {
            case "soul_crystal":
                player.getInventory().addItem(LuminaCrystal.createItem(1));
                break;
            case "soul_blood":
                player.getInventory().addItem(SoulBlood.createItem());
                break;
            case "lumina_sword":
                player.getInventory().addItem(LuminaSword.createItem());
                break;
            case "gravity_wand":
                player.getInventory().addItem(GravityWand.createItem());
                break;
            case "soul_axe":
                player.getInventory().addItem(SoulAxe.createItem());
                break;
            default:
                player.sendMessage("§cInvalid type. Use 'lumina_crystal', 'gravity_wand', 'soul_axe', 'soul_crystal' oder 'soul_blood'.");
                return false;
        }
        return true;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String @NotNull [] args) throws IllegalArgumentException {
        List<String> items = List.of("soul_blood", "soul_crystal", "gravity_wand", "lumina_sword", "soul_axe");
        if (args.length == 1) {
            return items;
        }
        return List.of();
    }
}
