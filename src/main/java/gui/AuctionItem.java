package gui;

import gui.inventories.GuiManager;
import gui.inventories.PageInventory;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static gui.AuctionManager.getAuctionItems;
import static gui.inventories.GuiManager.*;

public class AuctionItem {

    private ArrayList<String> lore = new ArrayList();

    public ArrayList<String> getLore() {
        return lore;
    }

    private Player player;
    private ItemStack itemStack;
    private int price;
    private int amount;
    private int number;

    public AuctionItem(Player player, ItemStack itemStack, int amount, int price) {
        this.player = player;
        this.price = price;
        this.amount = amount;
        this.number = getAuctionItems().size() + 1;

        lore.add(ChatColor.GOLD + "Купить за " + ChatColor.GREEN + price + "$");
        lore.add(ChatColor.BLUE + "Продает: " + ChatColor.WHITE + player.getName());
        lore.add(ChatColor.DARK_GRAY + "Номер предмета: " + number);

        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(amount);
        player.sendMessage(lore.get(2));

//        player.getInventory().addItem(item);

        getAuctionItems().add(this);

        this.itemStack = item;

        lore.clear();
    }


    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public int getNumber() {
        return number;
    }



//    public void sellItem(){
//        if(getLastPage().getItem(getLastPage().getSize() - 10) != null){
//            player.sendMessage("Должно работать вроде");
//            PageInventory newPage = new PageInventory();
//        }
//
//        getLastPage().addItem(this.getItemStack());
//        removeItemFromMainHand(this.getPlayer(), this.getAmount());
//
//    }
//
//    public void buyItem(InventoryClickEvent event){
//        if(event.getCurrentItem().getType() == Material.AIR) return;
//
//        int slot = event.getSlot();
//        ItemStack is = event.getCurrentItem();
//        if(slot >= 45 && slot <= 53) return;
//
//        ItemMeta meta = is.getItemMeta();
//        if(meta.hasLore()) meta.setLore(null);
//        is.setItemMeta(meta);
//
//        GuiManager.removeItems(event.getClickedInventory(), this.getItemStack(), this.getAmount());
//        player.getInventory().addItem(is);
//
//        if(getLastPage().getItem(0) == null && getAhPages().size() > 1){
//            getAhPages().remove(getLastPage());
//            getLastPage().remove(53);
//        }
//        player.openInventory(getLastPage());
//
////        for(int i = this.number; i <= auctionItems.size(); i++){
////             ==
////        }
//    }
//
//    public AuctionItem getAuctionItemByItemStack(ItemStack itemStack) {
//        for (AuctionItem item : auctionItems) {
//            if (itemStack.isSimilar(item.getItemStack())) {
//                return item;
//            }
//        }
//        return null;
//    }
}
