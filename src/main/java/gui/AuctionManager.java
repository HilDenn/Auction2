package gui;

import gui.holders.AcceptHolder;
import gui.holders.PagesHolder;
import gui.holders.StorageHolder;
import gui.inventories.GuiManager;
import gui.inventories.PageInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

import static gui.inventories.GuiManager.*;


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
        removeItemFromMainHand(player, amount);
        addItemToStorage(player, item);
        arrangeItems(player, "everything");

    }

    private static AuctionItem itemBeforeAcceptPage;
    private static Inventory inventoryBeforeAcceptPage;

    public static String inventoryBeforeAcceptPageName;
    public static String inventoryBeforeItemTypeCheckerInventoryName;

    public static String itemTypeCheckerAuctionName;
    public static String itemTypeCheckerStorageName;


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



            if(itemTypeCheckerStorageName != null){
                arrangeItemsInStorage(item.getPlayer(), itemTypeCheckerStorageName);
            }




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

            if(itemTypeCheckerStorageName != null){
                arrangeItemsInStorage(item.getPlayer(), itemTypeCheckerStorageName);
            }


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


        if(itemTypeCheckerStorageName != null){
            arrangeItems(player, itemTypeCheckerAuctionName);
        }

//        arrangeItems(player, "everything");

    }


    public static void arrangeItems(Player player, String sortingType){
        itemTypeCheckerAuctionName = sortingType;
        if(auctionSettings.isSortingUpAuctionPage(player)) {

            int ai = 0;
            for (PageInventory pageInventory : getAhPages()) {

                if (getAuctionItems().isEmpty()) {

                    getAhPages().remove(0);
                    return;

                }

                pageInventory.getInventory().clear();

                pageInventory.getInventory().setItem(47, getSortingItem(auctionSettings.isSortingUpAuctionPage(player)));
                pageInventory.getInventory().setItem(49, getYellowGlassPane);
                pageInventory.getInventory().setItem(52, getTypeCheckerItem());

                for (; ai <= (getAuctionItems().size() - 1); ai++) {

                    if(sortingType.equals("everything")) {
                        itemTypeCheckerAuctionName = "everything";
                        pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                    } else if(sortingType.equals("weapon")){
                        itemTypeCheckerAuctionName = "weapon";
                        if(ItemTypeChecker.isWeapon(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("tool")){
                        itemTypeCheckerAuctionName = "tool";
                        if(ItemTypeChecker.isTool(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("armor")){
                        itemTypeCheckerAuctionName = "armor";
                        if(ItemTypeChecker.isArmor(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("block")){
                        itemTypeCheckerAuctionName = "block";
                        if(ItemTypeChecker.isBlock(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("food")){
                        itemTypeCheckerAuctionName = "food";
                        if(ItemTypeChecker.isEdible(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("mechanism")){
                        itemTypeCheckerAuctionName = "mechanism";
                        if(ItemTypeChecker.isMechanism(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else if(sortingType.equals("farm")){
                        itemTypeCheckerAuctionName = "farm";
                        if(ItemTypeChecker.isFarmItem(getAuctionItems().get(ai).getItemStack().getType())){
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                        }
                    } else {

                        pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                    }

//                    pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                    if (getAhPages().get(pageInventory.getNumber() - 1).getInventory()
                            .getItem(pageInventory.getInventory().getSize() - 9) != null) {

                        break;
                    }

                }
                getAhPages().get(pageInventory.getNumber() - 1).getInventory().clear(45);


                if (getAhPages().size() > 1) {
                    if (pageInventory.getNumber() == 1) {
                        pageInventory.getInventory().setItem(53, getGreenGlassPane);
                    } else if (pageInventory.getNumber() == getAhPages().size()) {
                        pageInventory.getInventory().setItem(45, getRedGlassPane);
                    } else {
                        pageInventory.getInventory().setItem(53, getGreenGlassPane);
                        pageInventory.getInventory().setItem(45, getRedGlassPane);
                    }
                }
            }
        } else {
                int ai = getAuctionItems().size() - 1;
                for (PageInventory pageInventory : getAhPages()) {

                    if (getAuctionItems().isEmpty()) {

                        getAhPages().remove(0);
                        return;

                    }

                    pageInventory.getInventory().clear();

                    pageInventory.getInventory().setItem(47, getSortingItem(auctionSettings.isSortingUpAuctionPage(player)));
                    pageInventory.getInventory().setItem(49, getYellowGlassPane);
                    pageInventory.getInventory().setItem(52, getTypeCheckerItem());

                    for (; ai >= 0; ai--) {

                        if(sortingType.equals("everything")) {
                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            itemTypeCheckerAuctionName = "everything";
                        } else if(sortingType.equals("weapon")){
                            itemTypeCheckerAuctionName = "weapon";
                            if(ItemTypeChecker.isWeapon(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("tool")){
                            itemTypeCheckerAuctionName = "tool";
                            if(ItemTypeChecker.isTool(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("armor")){
                            itemTypeCheckerAuctionName = "armor";
                            if(ItemTypeChecker.isArmor(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("block")){
                            itemTypeCheckerAuctionName = "block";
                            if(ItemTypeChecker.isBlock(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("food")){
                            itemTypeCheckerAuctionName = "food";
                            if(ItemTypeChecker.isEdible(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("mechanism")){
                            itemTypeCheckerAuctionName = "mechanism";
                            if(ItemTypeChecker.isMechanism(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else if(sortingType.equals("farm")){
                            itemTypeCheckerAuctionName = "farm";
                            if(ItemTypeChecker.isFarmItem(getAuctionItems().get(ai).getItemStack().getType())){
                                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                            }
                        } else {

                            pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                        }

//                        pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());

                        if (getAhPages().get(pageInventory.getNumber() - 1).getInventory()
                                .getItem(pageInventory.getInventory().getSize() - 9) != null) {

                            break;
                        }

                    }
                    getAhPages().get(pageInventory.getNumber() - 1).getInventory().clear(45);


                    if (getAhPages().size() > 1) {
                        if (pageInventory.getNumber() == 1) {
                            pageInventory.getInventory().setItem(53, getGreenGlassPane);
                        } else if (pageInventory.getNumber() == getAhPages().size()) {
                            pageInventory.getInventory().setItem(45, getRedGlassPane);
                        } else {
                            pageInventory.getInventory().setItem(52, getGreenGlassPane);
                            pageInventory.getInventory().setItem(45, getRedGlassPane);
                        }
                    }
//                    pageInventory.getInventory().setItem(47, getSortingItem(auctionSettings.isSortingUpAuctionPage(player)));
            }
            player.updateInventory();
        }
    }

    public static void arrangeItemsInStorage(Player player, String sortingType){
        itemTypeCheckerStorageName = sortingType;
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
                inv.setItem(49, getYellowGlassPane);
                inv.setItem(52, getTypeCheckerItem());






                for (AuctionItem item : getPlayersItems().get(player)){



                    if(sortingType.equals("everything")) {
                        itemTypeCheckerStorageName = "everything";
                        inv.addItem(item.getItemStack());
                    } else if(sortingType.equals("weapon")){
                        itemTypeCheckerStorageName = "weapon";
                        if(ItemTypeChecker.isWeapon(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("tool")){
                        itemTypeCheckerStorageName = "tool";
                        if(ItemTypeChecker.isTool(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("armor")){
                        itemTypeCheckerStorageName = "armor";
                        if(ItemTypeChecker.isArmor(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("block")){
                        itemTypeCheckerStorageName = "block";
                        if(ItemTypeChecker.isBlock(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("food")){
                        itemTypeCheckerStorageName = "food";
                        if(ItemTypeChecker.isEdible(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("mechanism")){
                        itemTypeCheckerStorageName = "mechanism";
                        if(ItemTypeChecker.isMechanism(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else if(sortingType.equals("farm")){
                        itemTypeCheckerStorageName = "farm";
                        if(ItemTypeChecker.isFarmItem(item.getItemStack().getType())){
                            inv.addItem(item.getItemStack());
                        }
                    } else {

                        inv.addItem(item.getItemStack());

                    }


                }





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
                inv.setItem(49, getYellowGlassPane);
                inv.setItem(52, getTypeCheckerItem());






                for (int i = getPlayersItems().get(player).size() - 1; i >= 0; i--) {

                    if(sortingType.equals("everything")) {
                        itemTypeCheckerStorageName = "everything";
                        inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                    } else if(sortingType.equals("weapon")){
                        itemTypeCheckerStorageName = "weapon";
                        if(ItemTypeChecker.isWeapon(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("tool")){
                        itemTypeCheckerStorageName = "tool";
                        if(ItemTypeChecker.isTool(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("armor")){
                        itemTypeCheckerStorageName = "armor";
                        if(ItemTypeChecker.isArmor(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("block")){
                        itemTypeCheckerStorageName = "block";
                        if(ItemTypeChecker.isBlock(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("food")){
                        itemTypeCheckerStorageName = "food";
                        if(ItemTypeChecker.isEdible(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("mechanism")){
                        itemTypeCheckerStorageName = "mechanism";
                        if(ItemTypeChecker.isMechanism(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else if(sortingType.equals("farm")){
                        itemTypeCheckerStorageName = "farm";
                        if(ItemTypeChecker.isFarmItem(getPlayersItems().get(player).get(i).getItemStack().getType())){
                            inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                        }
                    } else {

                        inv.addItem(getPlayersItems().get(player).get(i).getItemStack());

                    }

//                    inv.addItem(getPlayersItems().get(player).get(i).getItemStack());
                }






                if (player.getOpenInventory().getTopInventory().getHolder() instanceof StorageHolder) {
                    player.updateInventory();
                    player.sendMessage("ауф бырбырбыр");
                }
            }
        }
    }


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
                AuctionManager.arrangeItems(player, "everything");
                AuctionManager.arrangeItemsInStorage(player, "everything");
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
                AuctionManager.arrangeItems(player, "everything");
                AuctionManager.arrangeItemsInStorage(player, "everything");
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
