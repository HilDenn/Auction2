package listener;

import gui.AuctionItem;
import gui.AuctionManager;
import gui.AuctionSettings;
import gui.holders.AcceptHolder;
import gui.holders.MainPageHolder;
import gui.holders.PagesHolder;
import gui.holders.StorageHolder;
import gui.inventories.GuiManager;
import gui.inventories.Storage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import static gui.inventories.GuiManager.*;

public class AuctionPageListener implements Listener {

    AuctionSettings auctionSettings = AuctionSettings.getInstance();

//    GuiManager guiManager = GuiManager.getInstance();

    @EventHandler
    public void onClickOnPages(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getHolder() instanceof AcceptHolder ||
                event.getInventory().getHolder() instanceof MainPageHolder ||
                event.getInventory().getHolder() instanceof PagesHolder ||
                event.getInventory().getHolder() instanceof StorageHolder){
            event.setCancelled(true);

            if (event.getCurrentItem() == null) return;
            else if (event.getInventory().getHolder() instanceof MainPageHolder){
                if(event.getSlot() == 11){
                    if (!(getAhPages().isEmpty())) {
                        openInventory(getLastPage(), event, 11);
                    } else {
                        player.sendMessage("К сожалению, нет ни одного выставленного предмета на аукционе!");
                        player.sendMessage("Выставите первый предмет на продажу сами!");
                    }
                } else if(event.getSlot() == 15){

                    openStorageInventory(player);

//                    if(!AuctionManager.getPlayersItems().containsKey(player)){
//                        player.sendMessage("У вас нет ни одного выставленного товара на аукционе!");
//                        player.sendMessage("Выставите свой первый товар командой /ah sell 'количество' 'цена' ");
//                    } else {
//                        createAndOpenStorageInventory(player);
//                    }
                }

            } else if (event.getInventory().getHolder() instanceof StorageHolder){
                openInventory(getMainPage().getInventory(), event, 49);
                event.setCancelled(true);

                if((event.getClick().isRightClick() || event.getClick().isLeftClick()) && event.getSlot() < 45 && event.getCurrentItem().getType() != Material.AIR){
//                    if((event.getCurrentItem() != null || event.getCurrentItem().getType() != Material.AIR) && event.getSlot() < 45){

//                        GuiManager.getPlayersInventories().get(player).remove(event.getCurrentItem());
//                        AuctionManager.getPlayersItems().get(player).remove(AuctionManager.getAuctionItemByItemStack(event.getCurrentItem()));
//                        AuctionManager.getAuctionItems().remove(AuctionManager.getAuctionItemByItemStack(event.getCurrentItem()));
                    AuctionManager.removeItemFromStorage(player, event, auctionSettings.isAcceptOnStorage(player));
                    player.sendMessage("ауф брбрб");
//                        player.closeInventory();
//                        createAndOpenStorageInventory(player);

//                    }

//                    AuctionManager.buyItem(player, event, false);



                }

                if(event.getSlot() == 47){

                    auctionSettings.getSortingStoragePage().put(player, !auctionSettings.isSortingUpStoragePage(player));
                    AuctionManager.arrangeItemsInStorage(player);

                }


            } else if (event.getClickedInventory().getHolder() instanceof AcceptHolder){

                if(event.getSlot() == 11){
                    player.openInventory(GuiManager.getLastPage());
                    return;

                } else if(event.getSlot() == 22) {
                    player.openInventory(GuiManager.getMainPage().getInventory());
                    return;

                } else if(event.getSlot() == 15){
                    player.sendMessage("прашло");
                    if(AuctionManager.inventoryBeforeAcceptPageName.equals("Auction")){
                        AuctionManager.buyItem(player, event, false);
                        player.sendMessage("прашло в аук" );
                        return;
                    } else if(AuctionManager.inventoryBeforeAcceptPageName.equals("Storage")){
                        AuctionManager.removeItemFromStorage(player, event, false);
                        player.sendMessage("прашло в стораж");
                        return;
                    }

                }

            } else if (event.getClickedInventory().getHolder() instanceof PagesHolder){
                    if (event.getSlot() < 45) {
                        // todo

                        AuctionManager.buyItem(player, event, auctionSettings.isAcceptOnAuc(player));


                    } else {
                        openInventory(getMainPage().getInventory(), event, 49);
                        for (int i = 0; i <= getAhPages().size(); i++){
                            if(getAhPages().get(i).getInventory().getName().equals(event.getClickedInventory().getName())){

                                // если не последняя страница и страниц больше 1, то перекл на след
                                if (!(event.getClickedInventory().getName().equals(getLastPage().getName())) && (getAhPages().size() > 1)) {
                                    if(getAhPages().size() > (i + 1)){
                                        openInventory(getAhPages().get(i + 1).getInventory(), event, 53);
                                        // если не первая страница и не единственная страница, перекл на пред
                                    }
                                }
                                if(!(event.getClickedInventory().getName().equals(getAhPages().get(0).getTitle())) && getAhPages().size() > 1){
                                    if((i - 1) >= 0){
                                        openInventory(getAhPages().get(i - 1).getInventory(), event, 45);
                                    }
                                }
                            } else continue;
                            break;
                        }

                        if(event.getSlot() == 47){
                            auctionSettings.getSortingAuctionPage().put(player, !auctionSettings.isSortingUpAuctionPage(player));
                            AuctionManager.arrangeItems(player);
                        }

                    }
                    event.setCancelled(true);
            }
        }
    }

    public void openInventory(Inventory openingInventory, InventoryClickEvent event, int slot) {
        if (openingInventory != null){
            if (event.getSlot() == slot) {
                event.getWhoClicked().openInventory(openingInventory);
                event.setCancelled(true);
            } else return;
        }
    }
}
