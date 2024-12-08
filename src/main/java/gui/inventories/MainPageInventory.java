package gui.inventories;

import gui.holders.AcceptHolder;
import gui.holders.MainPageHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public class MainPageInventory implements Accept, MainPage, Storage{

    private Inventory inventory;
    private InventoryHolder inventoryHolder;
    private String title;
    private int size;

    public MainPageInventory(InventoryHolder inventoryHolder, String title, int size) {
        this.inventoryHolder = inventoryHolder;
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(inventoryHolder, size, title);

        if(inventoryHolder instanceof AcceptHolder) {
            putPanes(inventory);
        } else if(inventoryHolder instanceof Storage){
            putPane(inventory);
        } else if(inventoryHolder instanceof MainPageHolder){
            placeItem(Material.CHEST, inventory, ChatColor.GOLD + "Аукцион" ,11);
            placeItem(Material.ENDER_CHEST, inventory, ChatColor.GOLD + "Ваше хранилище" ,15);
        }

    }

    public Inventory getInventory() {
        return inventory;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }



}
