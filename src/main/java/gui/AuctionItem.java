package gui;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class AuctionItem {

    private ArrayList auctionItems = new ArrayList();

    private Player player;
    private ItemStack itemStack;
    private int price;
    private int amount;
    private int number;

    public AuctionItem(Player player, ItemStack itemStack, int price, int amount, int number) {
        this.player = player;
        this.itemStack = itemStack;
        this.price = price;
        this.amount = amount;
        this.number = number;


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
}
