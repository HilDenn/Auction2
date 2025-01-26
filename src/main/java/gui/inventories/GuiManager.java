package gui.inventories;

import gui.holders.AcceptHolder;
import gui.holders.MainPageHolder;
import gui.holders.StorageHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiManager {

//    private static GuiManager instance;
//
//    public static GuiManager getInstance(){
//        if (instance == null) {
//            instance = new GuiManager();
//        }
//        return instance;
//    }

    private static MainPageInventory mainPage;
    private static MainPageInventory acceptPage;
    private static MainPageInventory storage;

    private PageInventory firstPage;

    private static ArrayList<PageInventory> ahPages = new ArrayList<>();

    public static ItemStack greenGlassPane;
    public static ItemStack yellowGlassPane;
    public static ItemStack redGlassPane;


    public GuiManager() {

        mainPage = new MainPageInventory(new MainPageHolder(), "Главная страница", 54);
        acceptPage = new MainPageInventory(new AcceptHolder(), "Вы подтверждаете действие?", 27);
        storage = new MainPageInventory(new StorageHolder(), "Ваше хранилище", 54);

        firstPage = new PageInventory();

        ahPages.add(firstPage);

        initialisePanes();

    }

    public static ArrayList<PageInventory> getAhPages() {
        return ahPages;
    }

    public static MainPageInventory getMainPage() {
        return mainPage;
    }

    public static MainPageInventory getAcceptPage() {
        return acceptPage;
    }

    public static MainPageInventory getStorage() {
        return storage;
    }

    public static Inventory getLastPage(){
        return ahPages.get(ahPages.size() - 1).getInventory();
    }


    private void initialisePanes(){
        greenGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta greenMeta = greenGlassPane.getItemMeta();
        greenMeta.setDisplayName(ChatColor.GREEN + "Вперед");
        greenGlassPane.setItemMeta(greenMeta);
        greenGlassPane.setDurability((short) 5);

        yellowGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta yellowMeta = yellowGlassPane.getItemMeta();
        yellowMeta.setDisplayName(ChatColor.YELLOW + "На главную");
        yellowGlassPane.setItemMeta(yellowMeta);
        yellowGlassPane.setDurability((short) 4);

        redGlassPane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta redMeta = redGlassPane.getItemMeta();
        redMeta.setDisplayName(ChatColor.RED + "Назад");
        redGlassPane.setItemMeta(redMeta);
        redGlassPane.setDurability((short) 14);
    }

    public static ItemStack createGlassPane(String name, String color){
        ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta meta = pane.getItemMeta();
        meta.setDisplayName(name);
        pane.setItemMeta(meta);

        switch(color){
            case "green":
                pane.setDurability((short) 5);
                break;
            case "yellow":
                pane.setDurability((short) 4);
                break;
            case "red":
                pane.setDurability((short) 14);
                break;
        }
        return pane;
    }

    public static void removeItems(Inventory inventory, ItemStack item, int amount) {
        if (item == null || amount <= 0) return; // Проверка на null и количество

//        Inventory inventory = player.getInventory(); // Получаем инвентарь игрока
        int toRemove = amount; // Количество, которое нужно удалить

        for (int slot = 0; slot < inventory.getSize(); slot++) {
            ItemStack currentItem = inventory.getItem(slot); // Получаем предмет в текущем слоте

            // Пропускаем слоты, где нет предмета или предмет не совпадает
            if (currentItem == null || !currentItem.isSimilar(item)) continue;

            int currentAmount = currentItem.getAmount(); // Получаем количество предметов в слоте

            if (currentAmount > toRemove) {
                // Если в слоте больше предметов, чем нужно удалить
                currentItem.setAmount(currentAmount - toRemove); // Уменьшаем количество в слоте
                break; // Удалили нужное количество, выходим из цикла
            } else {
                // Если в слоте меньше или равно предметов, чем нужно удалить
                toRemove -= currentAmount; // Уменьшаем количество на количество предметов в слоте
                inventory.clear(slot); // Очищаем слот
//                player.sendMessage("Итерация");
            }

            // Если все предметы удалены, выходим из цикла
            if (toRemove <= 0) break;
        }
    }

    public static void removeItemFromMainHand(Player player, int amount) {
        ItemStack item = player.getItemInHand();
        if (item == null || amount <= 0) return;

        int toRemove = amount;
        int  currentAmount = item.getAmount();

        if (currentAmount > toRemove) {
            item.setAmount(currentAmount - toRemove);
        } else {
            player.setItemInHand(new ItemStack(Material.AIR)); // Очищаем слот
            player.sendMessage("Итерация");
        }
    }

}
