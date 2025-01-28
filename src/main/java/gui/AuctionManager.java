package gui;

import gui.holders.AcceptHolder;
import gui.holders.PagesHolder;
import gui.inventories.GuiManager;
import gui.inventories.PageInventory;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

import static gui.inventories.GuiManager.*;
import static gui.inventories.GuiManager.getLastPage;


public class AuctionManager {

    static AuctionSettings auctionSettings = AuctionSettings.getInstance();

    private static ArrayList<AuctionItem> auctionItems = new ArrayList();

    public static ArrayList<AuctionItem> getAuctionItems() {
        return auctionItems;
    }

    public static void sellItem(Player player, ItemStack itemStack, int amount, int price){
        if(getAhPages().isEmpty()){
            PageInventory firstPage = new PageInventory();
            System.out.println("The first page has been created");
        } else if(getLastPage() != null && getLastPage().getItem(getLastPage().getSize() - 10) != null){
            player.sendMessage("Должно работать вроде");
            System.out.println("A page has been created from sellItem method");
            PageInventory newPage = new PageInventory();
        }


        AuctionItem item = new AuctionItem(player, itemStack.clone(), amount, price);
        getLastPage().addItem(item.getItemStack());
        removeItemFromMainHand(player, amount);

    }

    private static AuctionItem itemBeforeAcceptPage;
    private static Inventory inventoryBeforeAcceptPage;


    public static void buyItem(Player player, InventoryClickEvent event, boolean isAcceptOn) {
        if (event.getCurrentItem().getType() == Material.AIR) return;

        if (event.getClickedInventory().getHolder() instanceof PagesHolder) {
            inventoryBeforeAcceptPage = event.getClickedInventory();
            if (isAcceptOn) {
                ItemStack is = event.getCurrentItem();
                itemBeforeAcceptPage = getAuctionItemByItemStack(is);
                player.openInventory(getAcceptPage().getInventory());
                return;
            }
        }

        int slot = event.getSlot();
        if (slot >= 45 && slot <= 53) return;

        if (event.getClickedInventory().getHolder() instanceof AcceptHolder && event.getSlot() == 17) {
            if (itemBeforeAcceptPage == null) return;

            player.sendMessage("Короче ну да ээ");

            AuctionItem item = itemBeforeAcceptPage;

            ItemStack is = item.getItemStack();

            if (item == null) return;
            player.sendMessage("aue brbr");

            GuiManager.removeItems(inventoryBeforeAcceptPage, is, item.getAmount());
            getAuctionItems().remove(item);

            ItemMeta meta = is.getItemMeta();
            if (meta.hasLore()) meta.setLore(null);
            is.setItemMeta(meta);


            player.getInventory().addItem(is);
            player.closeInventory();
            player.openInventory(getLastPage());

//            if(getAuctionItems().isEmpty()) return;
//
//            for(PageInventory pageInventory : getAhPages()){
//                for(int i = event.getSlot(); i <= 44; i++){
//                    if(inventoryBeforeAcceptPage.getItem(i) == null && inventoryBeforeAcceptPage.getItem(i + 1) != null){
//                        inventoryBeforeAcceptPage.setItem(i, inventoryBeforeAcceptPage.getItem(i + 1));
//                        inventoryBeforeAcceptPage.setItem(i + 1, new ItemStack(Material.AIR));
//                        player.updateInventory();
//                        player.sendMessage("Прошло да");
//                    } else return;
//
//                    player.sendMessage("auf");
//                    player.openInventory(inventoryBeforeAcceptPage);
//                }
//            }

        } else if (event.getClickedInventory().getHolder() instanceof PagesHolder) {

            ItemStack is = event.getCurrentItem();
            AuctionItem item = getAuctionItemByItemStack(is);
//            itemBeforeAcceptPage = item;

            if (item == null) return;
            player.sendMessage("aue brbr");

            GuiManager.removeItems(event.getClickedInventory(), item.getItemStack(), item.getAmount());
            getAuctionItems().remove(item);

            ItemMeta meta = is.getItemMeta();
            if (meta.hasLore()) meta.setLore(null);
            is.setItemMeta(meta);

            player.getInventory().addItem(is);


        }

        player.updateInventory();





        if (getAuctionItems().isEmpty()) return;

        int ai = 0;
        for (PageInventory pageInventory : getAhPages()){
            pageInventory.getInventory().clear();

            pageInventory.getInventory().setItem(49 , yellowGlassPane);

            for(; ai <= (getAuctionItems().size() - 1); ai++){

                pageInventory.getInventory().addItem(getAuctionItems().get(ai).getItemStack());
                player.sendMessage("Ауф брбр");
                player.sendMessage("Страницы размер - " + getAhPages().size());
                player.sendMessage("Номер страницы - " + pageInventory.getNumber());

                if (getAhPages().get(pageInventory.getNumber() - 1).getInventory().getItem(pageInventory.getInventory().getSize() - 9) != null){
                    player.sendMessage("Абоби писи каки");

                    break;
                }

            }
            getAhPages().get(pageInventory.getNumber() - 1).getInventory().clear(45);


            if(getAhPages().size() > 1){
                if(pageInventory.getNumber() == 1){
                    pageInventory.getInventory().setItem(53, greenGlassPane);
                    player.sendMessage("Панель вперед");
                } else if(pageInventory.getNumber() == getAhPages().size()){
                    pageInventory.getInventory().setItem(45, redGlassPane);
                    player.sendMessage("Панель назад");
                } else {
                    pageInventory.getInventory().setItem(53, greenGlassPane);
                    pageInventory.getInventory().setItem(45, redGlassPane);
                    player.sendMessage("И то и то");
                }
            }

            player.updateInventory();
        }





        inventoryBeforeAcceptPage = null;

        if ((getLastPage().getItem(0) == null && (getLastPage().getItem(1) == null || getLastPage().getItem(2) == null)) && getAhPages().size() > 1){
            getAhPages().remove(getAhPages().size() - 1);
            getLastPage().remove(54);
            player.openInventory(getLastPage());
        }
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
