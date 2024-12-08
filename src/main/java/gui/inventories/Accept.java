package gui.inventories;

import gui.holders.AcceptHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public interface Accept {

//    private Inventory acceptInventory;
//
//    public Inventory getAcceptInventory() {
//        return acceptInventory;


//    public Accept() {
//        acceptInventory = Bukkit.createInventory(new AcceptHolder(), 27, "Подтвердите действие");
//
//        ItemStack greenPane = GuiManager.createGlassPane("Согласен", "green");
//        ItemStack redPane = GuiManager.createGlassPane("Не согласен", "red");
//        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");
//
//        acceptInventory.setItem(13, greenPane);
//        acceptInventory.setItem(17, redPane);
//        acceptInventory.setItem(21, yellowPane);
//    }

    default void putPane(Inventory inventory){
        ItemStack greenPane = GuiManager.createGlassPane("Согласен", "green");
        ItemStack redPane = GuiManager.createGlassPane("Не согласен", "red");
        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");

        inventory.setItem(13, greenPane);
        inventory.setItem(17, redPane);
        inventory.setItem(21, yellowPane);
    }

}
