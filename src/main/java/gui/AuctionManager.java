package gui;

import gui.holders.AcceptHolder;
import gui.holders.PagesHolder;
import gui.holders.StorageHolder;
import gui.inventories.GuiManager;
import gui.inventories.PageInventory;
import jdk.internal.dynalink.linker.GuardedInvocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

import static gui.inventories.GuiManager.*;
import static gui.inventories.GuiManager.getLastPage;


public class AuctionManager {

    static AuctionSettings auctionSettings = AuctionSettings.getInstance();

    private static ArrayList<AuctionItem> auctionItems = new ArrayList();

    private static HashMap<Player, ArrayList<AuctionItem>> playersItems = new HashMap<>();

    public static HashMap<Player, ArrayList<AuctionItem>> getPlayersItems() {
        return playersItems;
    }

    public static ArrayList<AuctionItem> getAuctionItems() {
        return auctionItems;
    }

    public static void sellItem(Player player, ItemStack itemStack, int amount, int price){
        if(getAhPages().isEmpty()){
            PageInventory firstPage = new PageInventory();
        } else if(getLastPage() != null && getLastPage().getItem(getLastPage().getSize() - 10) != null){
            PageInventory newPage = new PageInventory();
        }


        AuctionItem item = new AuctionItem(player, itemStack.clone(), amount, price);
        getLastPage().addItem(item.getItemStack());
//        getPlayersItems().put(player, getAuctionItemByItemStack(itemStack));
        removeItemFromMainHand(player, amount);
        addItemToStorage(player, item);
        arrangeItems(player);

    }

    private static AuctionItem itemBeforeAcceptPage;
    private static Inventory inventoryBeforeAcceptPage;

    public static String inventoryBeforeAcceptPageName;


    public static void buyItem(Player player, InventoryClickEvent event, boolean isAcceptOn) {
        if (event.getCurrentItem().getType() == Material.AIR) return;

        if (event.getClickedInventory().getHolder() instanceof PagesHolder) {
            inventoryBeforeAcceptPage = event.getClickedInventory();
            if(isAcceptOn){
                inventoryBeforeAcceptPageName = "Auction";
                ItemStack is = event.getCurrentItem();
                itemBeforeAcceptPage = getAuctionItemByItemStack(is);
                getAcceptPage().getInventory().setItem(13, itemBeforeAcceptPage.getItemStack());
                player.openInventory(getAcceptPage().getInventory());
                return;
            }
        }

        int slot = event.getSlot();
        if (slot >= 45 && slot <= 53) return;

        if (event.getClickedInventory().getHolder() instanceof AcceptHolder && event.getSlot() == 15){
            if (itemBeforeAcceptPage == null) return;

            AuctionItem item = itemBeforeAcceptPage;

            ItemStack is = item.getItemStack();

            if (item == null) return;

            GuiManager.removeItems(inventoryBeforeAcceptPage, is, item.getAmount());
            getAuctionItems().remove(item);
            getPlayersItems().get(item.getPlayer()).remove(item);

            arrangeItemsInStorage(item.getPlayer());

//            ItemMeta meta = is.getItemMeta();
//            if (meta.hasLore()) meta.setLore(null);
//            is.setItemMeta(meta);



            player.getInventory().addItem(getItemWithoutLore(is));
            player.closeInventory();
            player.openInventory(getLastPage());


        } else if (event.getClickedInventory().getHolder() instanceof PagesHolder) {

            ItemStack is = event.getCurrentItem();
            AuctionItem item = getAuctionItemByItemStack(is);

            if (item == null) return;

            GuiManager.removeItems(event.getClickedInventory(), item.getItemStack(), item.getAmount());
            getAuctionItems().remove(item);
            getPlayersItems().get(item.getPlayer()).remove(item);

            arrangeItemsInStorage(item.getPlayer());

//            ItemMeta meta = is.getItemMeta();
//            if (meta.hasLore()) meta.setLore(null);
//            is.setItemMeta(meta);

            player.getInventory().addItem(getItemWithoutLore(is));


        }

        player.updateInventory();



        inventoryBeforeAcceptPage = null;


        if((getLastPage().getItem(0) == null && (getLastPage().getItem(1) == null || getLastPage().getItem(2) == null))){

            getAhPages().remove(getAhPages().size() - 1);
            if(!getAhPages().isEmpty()) {
                getLastPage().remove(54);
                player.openInventory(getLastPage());
            } else {
                player.openInventory(getMainPage().getInventory());
            }

        }

        arrangeItems(player);

    }


    public static void arrangeItems(Player player){
        if(auctionSettings.isSortingUpAuctionPage(player)) {

            int ai = 0;
            for (PageInventory pageInventory : getAhPages()) {

                if (getAuctionItems().isEmpty()) {

                    getAhPages().remove(0);
                    return;

                }

                pageInventory.getInventory().clear();

                pageInventory.getInventory().setItem(49, yellowGlassPane);

                for (; ai <= (getAuctionItems().size() - 1); ai++) {

                    pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                    if (getAhPages().get(pageInventory.getNumber() - 1).getInventory()
                            .getItem(pageInventory.getInventory().getSize() - 9) != null) {

                        break;
                    }

                }
                getAhPages().get(pageInventory.getNumber() - 1).getInventory().clear(45);


                if (getAhPages().size() > 1) {
                    if (pageInventory.getNumber() == 1) {
                        pageInventory.getInventory().setItem(53, greenGlassPane);
                    } else if (pageInventory.getNumber() == getAhPages().size()) {
                        pageInventory.getInventory().setItem(45, redGlassPane);
                    } else {
                        pageInventory.getInventory().setItem(53, greenGlassPane);
                        pageInventory.getInventory().setItem(45, redGlassPane);
                    }
                }
                pageInventory.getInventory().setItem(47, getSortingItem(auctionSettings.isSortingUpAuctionPage(player)));
            }
        } else {
                int ai = getAuctionItems().size() - 1;
                for (PageInventory pageInventory : getAhPages()) {

                    if (getAuctionItems().isEmpty()) {

                        getAhPages().remove(0);
                        return;

                    }

                    pageInventory.getInventory().clear();

                    pageInventory.getInventory().setItem(49, yellowGlassPane);

                    for (; ai >= 0; ai--) {

                        pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                        if (getAhPages().get(pageInventory.getNumber() - 1).getInventory()
                                .getItem(pageInventory.getInventory().getSize() - 9) != null) {

                            break;
                        }

                    }
                    getAhPages().get(pageInventory.getNumber() - 1).getInventory().clear(45);


                    if (getAhPages().size() > 1) {
                        if (pageInventory.getNumber() == 1) {
                            pageInventory.getInventory().setItem(53, greenGlassPane);
                        } else if (pageInventory.getNumber() == getAhPages().size()) {
                            pageInventory.getInventory().setItem(45, redGlassPane);
                        } else {
                            pageInventory.getInventory().setItem(53, greenGlassPane);
                            pageInventory.getInventory().setItem(45, redGlassPane);
                        }
                    }
                    pageInventory.getInventory().setItem(47, getSortingItem(auctionSettings.isSortingUpAuctionPage(player)));
            }
            player.updateInventory();
        }
    }

    public static void arrangeItemsInStorage(Player player){
        if(GuiManager.getPlayersInventories().containsKey(player)) {

            if (auctionSettings.isSortingUpStoragePage(player)) {

                if (AuctionManager.getPlayersItems().get(player).isEmpty()) {
                    GuiManager.getPlayersInventories().remove(player);
                    player.openInventory(getMainPage().getInventory());
                    return;
                }

                Inventory inv = getPlayersInventories().get(player);

                inv.clear();

                inv.setItem(47, getSortingItem(auctionSettings.isSortingUpStoragePage(player)));
                inv.setItem(49, yellowGlassPane);

                for (AuctionItem item : getPlayersItems().get(player)) {
                    inv.addItem(item.getItemStack());
                }
//
//            for(int i = 0; i <= getPlayersItems().get(player).size() - 1; i++){
//                if(inv.getItem(i) == null && inv.getItem(i + 1) == null){
//                    inv.setItem(i, inv.getItem(i + 1));
//                    inv.setItem(i + 1, new ItemStack(Material.AIR));
//                }
//            }
//
                if (player.getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder) {
                    player.updateInventory();
                    player.sendMessage("ауф бырбырбыр");
                }
            } else {

                if (AuctionManager.getPlayersItems().get(player).isEmpty()) {
                    GuiManager.getPlayersInventories().remove(player);
                    player.openInventory(getMainPage().getInventory());
                    return;
                }

                Inventory inv = getPlayersInventories().get(player);

                inv.clear();

                inv.setItem(47, getSortingItem(auctionSettings.isSortingUpStoragePage(player)));
                inv.setItem(49, yellowGlassPane);

                for (int i = getPlayersItems().get(player).size() - 1; i >= 0; i--) {
                    inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                }

                if (player.getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder) {
                    player.updateInventory();
                    player.sendMessage("ауф бырбырбыр");
                }
            }
        }
    }
//        if(player.getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder){
//            Inventory inv = player.getOpenInventory().getTopInventory();
//            for(int i = 0; i >= getPlayersItems().get(player).size(); i++){
//                if(inv.getItem(i).getType() == Material.AIR && inv.getItem(i + 1).getType() != Material.AIR){
//
//                    inv.setItem(i, inv.getItem(i + 1));
//
//                }
//            }
//
//            player.updateInventory();
//
////            for(AuctionItem auctionItem : getPlayersItems().get(player)){
////
////                player.getOpenInventory().getTopInventory().clear();
////
////            }
//
//        } else return;



    public static void addItemToStorage(Player player, AuctionItem item){
        if(!getPlayersItems().containsKey(player)){
            getPlayersItems().put(player, new ArrayList<>());
            player.sendMessage("Гатова");
        }

        getPlayersItems().get(player).add(item);

        createStorageInventory(player);

    }

    public static void removeItemFromStorage(Player player, InventoryClickEvent event, boolean isAcceptOn){
        if(event.getClickedInventory().getHolder() instanceof StorageHolder){
            itemBeforeAcceptPage = getAuctionItemByItemStack(event.getCurrentItem());
            player.sendMessage("стораге плаер");
            if(isAcceptOn){
                inventoryBeforeAcceptPageName = "Storage";
                getAcceptPage().getInventory().setItem(13, itemBeforeAcceptPage.getItemStack());
                player.openInventory(getAcceptPage().getInventory());
                player.sendMessage("Открывай аццепт сука");
            } else {
                AuctionManager.getPlayersItems().get(player).remove(getAuctionItemByItemStack(event.getCurrentItem()));
                AuctionManager.getAuctionItems().remove(getAuctionItemByItemStack(event.getCurrentItem()));
                AuctionManager.arrangeItems(player);
                AuctionManager.arrangeItemsInStorage(player);
                player.getInventory().addItem(getItemWithoutLore(itemBeforeAcceptPage.getItemStack()));
                player.sendMessage("Не открывай");
            }
        } else if(event.getClickedInventory().getHolder() instanceof AcceptHolder){
            inventoryBeforeAcceptPageName = "Storage";
            if(event.getSlot() == 11){
                player.sendMessage("Иди нахуй назад иди сука тварь");
                player.openInventory(getPlayersInventories().get(player));
            } else if(event.getSlot() == 15){
                player.sendMessage("Иди вперед нахуй блять чмо говно писюн");
                AuctionManager.getPlayersItems().get(player).remove(itemBeforeAcceptPage);
                AuctionManager.getAuctionItems().remove(itemBeforeAcceptPage);
                AuctionManager.arrangeItems(player);
                AuctionManager.arrangeItemsInStorage(player);
                player.getInventory().addItem(getItemWithoutLore(itemBeforeAcceptPage.getItemStack()));
                player.openInventory(GuiManager.getPlayersInventories().get(player));
            }
        }

    }

        public static ItemStack getItemWithoutLore(ItemStack itemStack){
            ItemMeta meta = itemStack.getItemMeta();
            if(meta.hasLore()){
                meta.setLore(null);
                itemStack.setItemMeta(meta);
                return itemStack;
            } return itemStack;
        }


        public static AuctionItem getAuctionItemByItemStack (ItemStack itemStack){
            for (AuctionItem item : getAuctionItems()) {
                if (itemStack.isSimilar(item.getItemStack())) {
                    return item;
                }
            }
            return null;
        }
    }
