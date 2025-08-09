package gui.inventories;

import gui.holders.PagesHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static gui.inventories.GuiManager.*;

public class PageInventory {

    private Inventory inventory;
    private String title;
    private int size;
    private int number;

//    private ArrayList<PageInventory> ahPages = new ArrayList<>();

    public PageInventory() {
        this.title = title;
        this.size = size;
        this.number = getAhPages().size() + 1;

        this.inventory = Bukkit.createInventory(new PagesHolder(), 54, "Страница " + number);

//        ItemStack yellowPane = GuiManager.createGlassPane(ChatColor.BOLD + (ChatColor.YELLOW + "На главную страницу"), "yellow");

//        inventory.setItem(49, yellowPane);
//        inventory.setItem(47, getSortingItem(true));
//        inventory.setItem(52, getTypeCheckerItem());
        placeLowerStringItems();

//        getAhPages().add(this);

//        if(getAhPages().size() > 1){
//            getAhPages().get(getAhPages().size() - 2).getInventory().setItem(53, GuiManager.getGreenGlassPane);
//            getAhPages().get(getAhPages().size() - 1).getInventory().setItem(45, GuiManager.getRedGlassPane);
//        }

        placeGlassPanes();

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

    public Inventory getLastInventory() {
        return getAhPages().get(getAhPages().size() - 1).getInventory();
    }

    public void placeLowerStringItems(){
        inventory.setItem(49, getYellowGlassPane);
        inventory.setItem(47, getSortingItem(true));
        inventory.setItem(52, getTypeCheckerItem());
    }

    public void placeGlassPanes(){
        if(getAhPages().size() > 1){
            getAhPages().get(getAhPages().size() - 2).getInventory().setItem(53, GuiManager.getGreenGlassPane);
            getAhPages().get(getAhPages().size() - 1).getInventory().setItem(45, GuiManager.getRedGlassPane);
        }
    }
}
