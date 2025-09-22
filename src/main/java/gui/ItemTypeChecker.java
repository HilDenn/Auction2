package gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemTypeChecker {

    public static String getItemType(ItemStack item) {
        if (item == null || item.getType() == Material.AIR) {
            return "Нет предмета";
        }

        Material material = item.getType();

        if (isEdible(material)) {
            return "food";
        } else if (isArmor(material)) {
            return "armor";
        } else if (isWeapon(material)) {
            return "weapon";
        } else if (isTool(material)) {
            return "tool";
        } else if (isBlock(material)) {
            return "block";
        } else if (isMechanism(material)) {
            return "mechanism";
        } else if (isFarmItem(material)) {
            return "farm";
        }

        return "other";
    }

    public static boolean isEdible(Material material) {
        return material.isEdible();
    }

    public static boolean isArmor(Material material) {
        return material == Material.DIAMOND_HELMET ||
                material == Material.IRON_HELMET ||
                material == Material.GOLD_HELMET ||
                material == Material.LEATHER_HELMET ||
                material == Material.DIAMOND_CHESTPLATE ||
                material == Material.IRON_CHESTPLATE ||
                material == Material.GOLD_CHESTPLATE ||
                material == Material.LEATHER_CHESTPLATE ||
                material == Material.DIAMOND_LEGGINGS ||
                material == Material.IRON_LEGGINGS ||
                material == Material.GOLD_LEGGINGS ||
                material == Material.LEATHER_LEGGINGS ||
                material == Material.DIAMOND_BOOTS ||
                material == Material.IRON_BOOTS ||
                material == Material.GOLD_BOOTS ||
                material == Material.LEATHER_BOOTS;
    }

    public static boolean isWeapon(Material material) {
        return material == Material.DIAMOND_SWORD ||
                material == Material.IRON_SWORD ||
                material == Material.GOLD_SWORD ||
                material == Material.STONE_SWORD ||
                material == Material.WOOD_SWORD ||
                material == Material.BOW;
    }

    public static boolean isTool(Material material) {
        return material == Material.DIAMOND_PICKAXE ||
                material == Material.IRON_PICKAXE ||
                material == Material.GOLD_PICKAXE ||
                material == Material.STONE_PICKAXE ||
                material == Material.WOOD_PICKAXE ||
                material == Material.DIAMOND_AXE ||
                material == Material.IRON_AXE ||
                material == Material.GOLD_AXE ||
                material == Material.STONE_AXE ||
                material == Material.WOOD_AXE ||
                material == Material.DIAMOND_SPADE ||
                material == Material.IRON_SPADE ||
                material == Material.GOLD_SPADE ||
                material == Material.STONE_SPADE ||
                material == Material.WOOD_SPADE ||
                material == Material.FISHING_ROD; // Добавьте другие инструменты по мере необходимости
    }

    public static boolean isBlock(Material material) {
        // Пример проверки блоков
        return material.isBlock();
    }

    public static boolean isMechanism(Material material) {
        return material == Material.REDSTONE ||
                material == Material.LEVER ||
                material == Material.WOOD_BUTTON ||
                material == Material.STONE_BUTTON ||
                material == Material.PISTON_EXTENSION ||
                material == Material.PISTON_MOVING_PIECE ||
                material == Material.PISTON_STICKY_BASE ||
                material == Material.PISTON_BASE ||
                material == Material.DISPENSER ||
                material == Material.DROPPER ||
                material == Material.HOPPER ||
                material == Material.REDSTONE_COMPARATOR ||
                material == Material.COMMAND_REPEATING ||
                material == Material.DAYLIGHT_DETECTOR;
    }

    public static boolean isFarmItem(Material material) {
        return material == Material.WHEAT ||
                material == Material.CARROT ||
                material == Material.POTATO ||
                material == Material.BEETROOT ||
                material == Material.SEEDS ||
                material == Material.MELON_SEEDS ||
                material == Material.PUMPKIN_SEEDS ||
                material == Material.NETHER_WARTS;
    }
}
