package gui.inventories;

import gui.holders.StorageHolder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class StorageInventory {

    private Player owner;
    private int amountOfItems;

    public StorageInventory(Player player){
        this.owner = player;

        Inventory storage = Bukkit.createInventory(new StorageHolder(), 54, "Ваше хранилище");

        storage.setItem(49, GuiManager.getYellowGlassPane);

        storage.addItem();

    }

}
