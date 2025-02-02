package gui.inventories;

import gui.AuctionItem;
import gui.AuctionManager;
import gui.AuctionSettings;
import gui.holders.AcceptHolder;
import gui.holders.MainPageHolder;
import gui.holders.StorageHolder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class GuiManager {

//    private static GuiManager instance;
//
//    public static GuiManager getInstance(){
//        if (instance == null) {
//            instance = new GuiManager();
//        }
//        return instance;
//    }
static AuctionSettings auctionSettings = AuctionSettings.getInstance();

    private static ArrayList<String> sortingUp = new ArrayList<>();
    private static ArrayList<String> sortingDown = new ArrayList<>();

    private static MainPageInventory mainPage;
    private static MainPageInventory acceptPage;
    private static MainPageInventory storage;


    private static ArrayList<PageInventory> ahPages = new ArrayList<>();



    private static HashMap<Player, Inventory> playersInventories = new HashMap<>();

    public static HashMap<Player, Inventory> getPlayersInventories() {
        return playersInventories;
    }

    public static ItemStack greenGlassPane;
    public static ItemStack yellowGlassPane;
    public static ItemStack redGlassPane;


    public GuiManager() {

        mainPage = new MainPageInventory(new MainPageHolder(), "Главная страница", 54);
        acceptPage = new MainPageInventory(new AcceptHolder(), "Вы подтверждаете действие?", 27);
        storage = new MainPageInventory(new StorageHolder(), "Ваше хранилище", 54);


        initialisePanes();
        initialiseSortingLores();

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

    private void initialiseSortingLores(){
        sortingUp.add(ChatColor.BOLD + (ChatColor.WHITE + "Сортировать: раньше"));
        sortingUp.add(ChatColor.BOLD + (ChatColor.GRAY + "Сортировать: позже"));

        sortingDown.add(ChatColor.BOLD + (ChatColor.GRAY + "Сортировать: раньше"));
        sortingDown.add(ChatColor.BOLD + (ChatColor.WHITE + "Сортировать: позже"));
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


    public static ItemStack getSortingItem(boolean bool){
        ItemStack item = new ItemStack(Material.WATCH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "Сортировать: ");
        if(bool){
            meta.setLore(sortingUp);
        } else {
            meta.setLore(sortingDown);
        }

        item.setItemMeta(meta);
        return item;
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

    public static void createStorageInventory(Player player){
        if(AuctionManager.getPlayersItems().containsKey(player)){


            Inventory inventory = Bukkit.createInventory(new StorageHolder(), 54, "Ваше хранилище");

            inventory.setItem(47, getSortingItem(auctionSettings.isSortingUpStoragePage(player)));
            inventory.setItem(49, yellowGlassPane);

            for (AuctionItem auctionItem : AuctionManager.getPlayersItems().get(player)) {
                inventory.addItem(auctionItem.getItemStack());
            }

            playersInventories.put(player, inventory);
        }
    }

    public static void openStorageInventory(Player player){

        if(playersInventories.containsKey(player)){

            player.openInventory(playersInventories.get(player));

        } else {

            player.sendMessage("У вас нет предметов на продаже.");

        }

    }

}
