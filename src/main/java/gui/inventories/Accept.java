package gui.inventories;

import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public interface Accept {

    default void putPanes(Inventory inventory){
        ItemStack greenPane = GuiManager.createGlassPane(ChatColor.BOLD + (ChatColor.GREEN + "Согласен"), "green");
        ItemStack redPane = GuiManager.createGlassPane(ChatColor.BOLD + (ChatColor.RED + "Не согласен"), "red");
        ItemStack yellowPane = GuiManager.createGlassPane(ChatColor.BOLD + (ChatColor.YELLOW + "На главную страницу"), "yellow");

        inventory.setItem(13, greenPane);
        inventory.setItem(17, redPane);
        inventory.setItem(21, yellowPane);
    }

}
