package gui.inventories;

import gui.holders.MainPageHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public interface MainPage {

//    private Inventory mainPage;
//
//    public Inventory getMainPage() {
//        return mainPage;
//    }
//
//    public MainPage(){
//        mainPage = Bukkit.createInventory(new MainPageHolder(), 54, "Главная страница");
//
//        placeItem(Material.CHEST, mainPage, ChatColor.GOLD + "Аукцион" ,11);
//        placeItem(Material.ENDER_CHEST, mainPage, ChatColor.GOLD + "Ваше хранилище" ,15);
//    }

    default void placeItem(Material material, Inventory inventory, String name, int i){

        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(i, itemStack);

    }
}
