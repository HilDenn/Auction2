package gui;

import gui.inventories.GuiManager;
import org.bukkit.scheduler.BukkitRunnable;

public class UpdateItemsTime extends BukkitRunnable {
    @Override
    public void run() {

        for(int i = 0; i <= AuctionManager.getAuctionItems().size() - 1; i++){
            AuctionItem auctionItem = AuctionManager.getAuctionItems().get(i);

            auctionItem.setMinutesBeforeExpiration(auctionItem.getMinutesBeforeExpiration() - 1);
            auctionItem.getPlayer().updateInventory();
            auctionItem.getPlayer().sendMessage("Уменьшено на минуту");
            AuctionManager.changeTimeInLore(auctionItem);

//            auctionItem.initialiseLore();

            if(auctionItem.getMinutesBeforeExpiration() <= 0){
//                AuctionManager.getAuctionItems().remove(auctionItem);
                auctionItem.getPlayer().sendMessage("Удалено");
                AuctionManager.expirationAuctionItem(auctionItem);
//                GuiManager.getPlayersInventories().get(auctionItem.getPlayer()).
            }

            AuctionManager.arrangeItems(auctionItem.getPlayer(), AuctionManager.itemTypeCheckerAuctionName);
            AuctionManager.arrangeItemsInStorage(auctionItem.getPlayer(), AuctionManager.itemTypeCheckerStorageName);

            continue;
        }

//        for(AuctionItem auctionItem : AuctionManager.getAuctionItems()){
//            auctionItem.setMinutesBeforeExpiration(auctionItem.getMinutesBeforeExpiration() - 1);
//            auctionItem.getPlayer().updateInventory();
//            auctionItem.getPlayer().sendMessage("Уменьшено на минуту");
//            AuctionManager.changeTimeInLore(auctionItem);
//
////            auctionItem.initialiseLore();
//
//            if(auctionItem.getMinutesBeforeExpiration() <= 0){
////                AuctionManager.getAuctionItems().remove(auctionItem);
//                auctionItem.getPlayer().sendMessage("Удалено");
//                AuctionManager.expirationAuctionItem(auctionItem);
////                GuiManager.getPlayersInventories().get(auctionItem.getPlayer()).
//            }
//
//            AuctionManager.arrangeItems(auctionItem.getPlayer(), AuctionManager.itemTypeCheckerAuctionName);
//            AuctionManager.arrangeItemsInStorage(auctionItem.getPlayer(), AuctionManager.itemTypeCheckerStorageName);
//
//            continue;
//        }

    }
}
