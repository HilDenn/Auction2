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

    private Player player;
    private ItemStack itemStack;
    private int price;
    private int amount;
    private UUID number;

    public AuctionItem(Player player, ItemStack itemStack, int amount, int price) {
        this.player = player;
        this.price = price;
        this.amount = amount;
        this.number = UUID.randomUUID();

        lore.add(ChatColor.GOLD + "Купить за " + ChatColor.GREEN + price + "$");
        lore.add(ChatColor.BLUE + "Продает: " + ChatColor.WHITE + player.getName());
        lore.add(ChatColor.DARK_GRAY + "Номер предмета: " + this.number);


        ItemStack item = itemStack.clone();
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(amount);


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

    public UUID getNumber() {
        return number;
    }

}
