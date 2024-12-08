package gui.inventories;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class GuiManager {

//    private FirstPage firstPage = new FirstPage();
//    private MainPage mainPage = new MainPage();
//    private Storage storage = new Storage();

//    private ArrayList<Inventory> ahPages = new ArrayList<>();

//    public ArrayList<Inventory> getAhPages() {
//        return ahPages;
//    }

    public GuiManager() {

    }

    protected static ItemStack createGlassPane(String name, String color){
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
