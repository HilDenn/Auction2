package gui.inventories;

import gui.holders.ItemTypeCheckerHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemTypeCheckerInventory {

    private static Inventory itemTypeCheckerInventory;

    public static Inventory getItemTypeCheckerInventory() {
        return itemTypeCheckerInventory;
    }

    public ItemTypeCheckerInventory(){
        itemTypeCheckerInventory = Bukkit.createInventory(new ItemTypeCheckerHolder(),  54, "Выбере предмет для сортировки");

        initialiseItems();
    }

    private void initialiseItems(){
        itemTypeCheckerInventory.setItem(0, createItem(Material.DIAMOND_SWORD, ChatColor.BLUE + "Оружие"));
        itemTypeCheckerInventory.setItem(1, createItem(Material.IRON_PICKAXE, ChatColor.BLUE + "Инструменты"));
        itemTypeCheckerInventory.setItem(2, createItem(Material.GOLD_CHESTPLATE, ChatColor.BLUE + "Броня"));
        itemTypeCheckerInventory.setItem(3, createItem(Material.DIRT, ChatColor.BLUE + "Блоки"));
        itemTypeCheckerInventory.setItem(4, createItem(Material.COOKED_BEEF, ChatColor.BLUE + "Еда"));
        itemTypeCheckerInventory.setItem(5, createItem(Material.PISTON_BASE, ChatColor.BLUE + "Механизмы"));
        itemTypeCheckerInventory.setItem(6, createItem(Material.SEEDS, ChatColor.BLUE + "Фермерство"));
        itemTypeCheckerInventory.setItem(7, createItem(Material.TOTEM, ChatColor.BLUE + "Остальное"));
        itemTypeCheckerInventory.setItem(8, createItem(Material.COMPASS, ChatColor.GREEN + "Всё"));

        itemTypeCheckerInventory.setItem(49, GuiManager.createGlassPane(ChatColor.YELLOW + "Назад", "yellow"));

    }

    private ItemStack createItem(Material material, String name){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(name);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

}
