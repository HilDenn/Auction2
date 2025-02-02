package gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class AuctionSettings {

    private static AuctionSettings instance;

    public static AuctionSettings getInstance() {
        if(instance == null){
            instance = new AuctionSettings();
        }
        return instance;
    }

    private HashMap<Player, Boolean> acceptOnAuctionPages = new HashMap<>();

    public HashMap<Player, Boolean> getAcceptOnAuctionPages() {
        return acceptOnAuctionPages;
    }

    public boolean isAcceptOnAuc(Player player){
        if(acceptOnAuctionPages.containsKey(player)) return acceptOnAuctionPages.get(player);
        return false;

    }

    private HashMap<Player, Boolean> acceptOnStoragePage = new HashMap<>();

    public HashMap<Player, Boolean> getAcceptOnStoragePage() {
        return acceptOnStoragePage;
    }

    public boolean isAcceptOnStorage(Player player){
        if(acceptOnStoragePage.containsKey(player)) return acceptOnStoragePage.get(player);
        return false;

    }

    private HashMap<Player, Boolean> sortingAuctionPage = new HashMap<>();

    public HashMap<Player, Boolean> getSortingAuctionPage() {
        return sortingAuctionPage;
    }

    public boolean isSortingUpAuctionPage(Player player){
        if(sortingAuctionPage.containsKey(player)) return sortingAuctionPage.get(player);
        return false;

    }

    private HashMap<Player, Boolean> sortingStoragePage = new HashMap<>();

    public HashMap<Player, Boolean> getSortingStoragePage() {
        return sortingStoragePage;
    }

    public boolean isSortingUpStoragePage(Player player){
        if(sortingStoragePage.containsKey(player)) return sortingStoragePage.get(player);
        return false;

    }

}
