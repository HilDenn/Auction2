package listener;

import gui.AuctionSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    AuctionSettings auctionSettings = AuctionSettings.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        auctionSettings.getAcceptOnAuctionPages().put(player, true);
        auctionSettings.getAcceptOnStoragePage().put(player, true);
        player.sendMessage("Halo");
    }


}
