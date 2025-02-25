package gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.UUID;

import static gui.AuctionManager.getAuctionItems;

public class AuctionItem {

    private ArrayList<String> lore = new ArrayList();

    public ArrayList<String> getLore() {
        return lore;
    }

//    public void initialiseLore(){
//        lore.add(ChatColor.GOLD + "Купить за " + ChatColor.GREEN + price + "$");
//        lore.add(ChatColor.BLUE + "Продает: " + ChatColor.WHITE + player.getName());
//        lore.add(ChatColor.YELLOW + "Истекает через: " + formattedTime);
//        lore.add(ChatColor.DARK_GRAY + String.valueOf(this.number));
//    }



    private Player player;
    private ItemStack itemStack;
    private int price;
    private int amount;
    private UUID number;
    private int minutesBeforeExpiration;


    public String getFormattedTime(){
        return String.format("%02d:%02d", this.getMinutesBeforeExpiration() / 60, this.getMinutesBeforeExpiration() % 60);
    }
//    private String formattedTime = String.format("%02d:%02d", this.getMinutesBeforeExpiration() / 60, this.getMinutesBeforeExpiration() % 60);

    public AuctionItem(Player player, ItemStack itemStack, int amount, int price) {
        this.player = player;
        this.price = price;
        this.amount = amount;
        this.number = UUID.randomUUID();
        this.minutesBeforeExpiration = 2;

        lore.add(ChatColor.GOLD + "Купить за " + ChatColor.GREEN + price + "$");
        lore.add(ChatColor.BLUE + "Продает: " + ChatColor.WHITE + player.getName());
        lore.add(ChatColor.YELLOW + "Истекает через: " + getFormattedTime());
        lore.add(ChatColor.DARK_GRAY + String.valueOf(this.number));

//        initialiseLore();


        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(amount);


        getAuctionItems().add(this);

        this.itemStack = item;

//        lore.clear();
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

    public UUID getNumber() {
        return number;
    }

    public int getMinutesBeforeExpiration() {
        return minutesBeforeExpiration;
    }

    public void setMinutesBeforeExpiration(int minutesBeforeExpiration) {
        this.minutesBeforeExpiration = minutesBeforeExpiration;
    }

//    public String getFormattedTime() {
//        return formattedTime;
//    }
}
