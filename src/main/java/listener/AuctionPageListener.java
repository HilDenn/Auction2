package listener;

import gui.AuctionManager;
import gui.AuctionSettings;
import gui.holders.AcceptHolder;
import gui.holders.MainPageHolder;
import gui.holders.PagesHolder;
import gui.holders.StorageHolder;
import gui.inventories.GuiManager;
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
                if(!(getAhPages().isEmpty())){
                    openInventory(getLastPage(), event, 11);
                } else {
                    player.closeInventory();
                    player.sendMessage("К сожалению, нет ни одного выставленного предмета на аукционе!");
                    player.sendMessage("Выставите первый предмет на продажу сами!");
                }
                openInventory(GuiManager.getStorage().getInventory(), event, 15);

            } else if (event.getInventory().getHolder() instanceof StorageHolder) {
                event.getWhoClicked().sendMessage("ауе");
                openInventory(getMainPage().getInventory(), event, 49);
                event.setCancelled(true);

            } else if (event.getClickedInventory().getHolder() instanceof AcceptHolder){

                if(event.getSlot() == 13){
                    player.sendMessage("Галя, отмена");
                    player.openInventory(GuiManager.getLastPage());
                    return;

                } else if(event.getSlot() == 21) {
                    player.sendMessage("Ладно иди нахуй");
                    player.openInventory(GuiManager.getMainPage().getInventory());
                    return;

                } else if(event.getSlot() == 17){
                    player.sendMessage("Продолжаем");
                    AuctionManager.buyItem(player, event, false);
                    return;
                }

            } else if (event.getClickedInventory().getHolder() instanceof PagesHolder){
                    player.sendMessage("ahahahahahaahha");
                    if (event.getSlot() < 45) {
                        // todo

                        AuctionManager.buyItem(player, event, auctionSettings.isAcceptOn(player));

                        player.sendMessage("aue brbrbr shkebede");

                    } else {
                        openInventory(getMainPage().getInventory(), event, 49);
                        event.getWhoClicked().sendMessage("еее бр шкебеде доп доп доп ес ес");
                        for (int i = 0; i <= getAhPages().size(); i++){
                            if(getAhPages().get(i).getInventory().getName().equals(event.getClickedInventory().getName())){

                                player.sendMessage("Это чо такое ты чо на");
                                // если не последняя страница и страниц больше 1, то перекл на след
                                if (!(event.getClickedInventory().getName().equals(getLastPage().getName())) && (getAhPages().size() > 1)) {
                                    if(getAhPages().size() > (i + 1)){
                                        openInventory(getAhPages().get(i + 1).getInventory(), event, 53);
                                        player.sendMessage("Вперед надо");
                                        // если не первая страница и не единственная страница, перекл на пред
                                    }
                                }
                                if(!(event.getClickedInventory().getName().equals(getAhPages().get(0).getTitle())) && getAhPages().size() > 1){
                                    if((i - 1) >= 0){
                                        openInventory(getAhPages().get(i - 1).getInventory(), event, 45);
                                        player.sendMessage("Назад надо");
                                    }
                                }
                            } else continue;
                            break;
                        }
                    }
                    event.setCancelled(true);
            }
        }
    }

    public void openInventory(Inventory openingInventory, InventoryClickEvent event, int slot) {
        if (openingInventory != null){
            if (event.getSlot() == slot) {
                event.getWhoClicked().sendMessage("da " + getAhPages().size());
                event.getWhoClicked().openInventory(openingInventory);
                event.setCancelled(true);
            } else return;
        }
    }
}
