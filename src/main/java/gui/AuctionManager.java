package gui;

import gui.inventories.GuiManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class AuctionManager {

    public void addItemToAh(Player player, ItemStack itemStack, int amount, int price){
        if(getLastPage().getItem(getLastPage().getSize() - 10) != null){
            player.sendMessage("АУЕАУАУАЫФЗАЛЩВФЫОщзЫАВОЩЗЫВАОщзвы");
            Inventory inventory = Bukkit.createInventory(ahPagesHolder, 54, "Страница " + (ahPages.size() + 1));
            getAhPages().put(getAhPages().size() + 1, inventory);
            setLastPage(inventory);
//            getAhPages().put(getAhPages().size() + 1, inventory);
            if(getAhPages().size() > 1){
                getAhPages().get(getAhPages().size() - 1).setItem(53, greenGlassPane);
                getAhPages().get(getAhPages().size()).setItem( 45, redGlassPane);
            }
        }
//        Inventory lastPage = getAhPages().get(getAhPages().size());
        getLore().add(ChatColor.GOLD + "Купить за " + ChatColor.GREEN + price + "$");
        getLore().add(ChatColor.BLUE + "Продает: " + ChatColor.WHITE + player.getName());
        getLore().add(ChatColor.DARK_GRAY + "Номер предмета: " + getAhItemNumber());
        ItemStack is = itemStack.clone();
        ItemMeta meta = is.getItemMeta();
        meta.setLore(getLore());
        is.setItemMeta(meta);
        is.setAmount(amount);

        setAhItemNumber(getAhItemNumber() + 1);
        setAmountOfItemsInAh(getAmountOfItemsInAh() + 1);

//        removeItems(player, itemStack, amount);
        GuiManager.removeItemFromMainHand(player, amount);
        getLastPage().addItem(is);
        getAhPagesItems().add(is);
        getLore().clear();

    }

    public void buyItemAtAh(Player player, InventoryClickEvent event){
        //todo проверка на баланс
//        Player plr = (Player) event.getWhoClicked();
        if(event.getCurrentItem().getType() == Material.AIR) return;

        int slot = event.getSlot();
        ItemStack item = event.getCurrentItem();
        if(slot >= 45 && slot <= 53) return;

        ItemMeta meta = item.getItemMeta();
        if(meta.hasLore()) meta.setLore(null);
        item.setItemMeta(meta);

        GuiManager.removeItems(event.getClickedInventory(), item, item.getAmount());
        player.getInventory().addItem(item);

        if(getLastPage().getItem(0) == null && getAhPages().size() > 1){
            setLastPage(ahPages.get(ahPages.size() - 1));
            ahPages.remove(ahPages.size());
            getLastPage().remove(53);
        }
        player.closeInventory();

        if(getAhPagesItems().size() > 1){
            for(int i = ; getAhPagesItems().size() - 1; i++){

            }
        }

    }




    }
}
