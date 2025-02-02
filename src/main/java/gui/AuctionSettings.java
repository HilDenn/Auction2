package gui;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

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

//    private HashMap<Player, Boolean> sorting = new HashMap<>();
//
//    public HashMap<Player, Boolean> getSorting() {
//        return sorting;
//    }
//
//    public boolean isSortingUp(Player player){
//        if(sorting.containsKey(player)) return sorting.get(player);
//        return false;
//
//    }

}
