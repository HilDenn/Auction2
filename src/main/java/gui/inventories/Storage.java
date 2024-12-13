package gui.inventories;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Storage {

    default void putPane(Inventory inventory){
        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");
        inventory.setItem(49, yellowPane);
    }
}
