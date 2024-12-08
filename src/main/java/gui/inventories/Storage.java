package gui.inventories;

import gui.holders.StorageHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Storage {

//    private Inventory storage;

//    public Inventory getStorage() {
//        return storage;
//    }
//
//    public Storage() {
//        storage = Bukkit.createInventory(new StorageHolder(), 54, "Ваше хранилище");
//
//        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");
//
//        storage.setItem(49, yellowPane);
//    }

    default void putPanes(Inventory inventory){
        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");
        inventory.setItem(49, yellowPane);
    }
}
