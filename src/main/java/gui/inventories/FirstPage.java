//package gui.inventories;
//
//import gui.holders.PagesHolder;
//import org.bukkit.Bukkit;
//import org.bukkit.ChatColor;
//import org.bukkit.Material;
//import org.bukkit.inventory.Inventory;
//import org.bukkit.inventory.ItemStack;
//
//public class FirstPage {
//
//    private Inventory firstPage;
//
//    public Inventory getFirstPage() {
//        return firstPage;
//    }
//
//    public FirstPage(){
//        firstPage = Bukkit.createInventory(new PagesHolder(), 54, "Страница 1");
//
//        ItemStack greenPane = GuiManager.createGlassPane("Вперед", "green");
//        ItemStack redPane = GuiManager.createGlassPane("Назад", "red");
//        ItemStack yellowPane = GuiManager.createGlassPane("На главную страницу", "yellow");
//
//        firstPage.setItem(49, yellowPane);
//    }
}
