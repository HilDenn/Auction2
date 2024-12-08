package gui.inventories;

import gui.holders.PagesHolder;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PageInventory {

    private Inventory inventory;
    private String title;
    private int size;
    private int number;

    private ArrayList<PageInventory> ahPages = new ArrayList<>();

    public PageInventory(String title, int size, int number) {
        this.title = title;
        this.size = size;
        this.number = number;

        this.inventory = Bukkit.createInventory(new PagesHolder(), size, title);

        ItemStack greenPane = GuiManager.createGlassPane("Вперед", "green");
        ItemStack redPane = GuiManager.createGlassPane("Назад", "red");
        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");

        inventory.setItem(49, yellowPane);

        ahPages.add(this);

        if(ahPages.size() > 1){
            ahPages.get(ahPages.size() - 2).getInventory().setItem(53, greenPane);
            ahPages.get(ahPages.size() - 1).getInventory().setItem(45, redPane);
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

    public int getNumber() {
        return number;
    }
}
