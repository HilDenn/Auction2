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

    private HashMap<Player, Boolean> accept = new HashMap<>();

    public HashMap<Player, Boolean> getAccept() {
        return accept;
    }

    public boolean isAcceptOn(Player player){
        if(accept.containsKey(player)) return accept.get(player);
        return false;
    }
}
