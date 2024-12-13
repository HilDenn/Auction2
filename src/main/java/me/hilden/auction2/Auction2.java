package me.hilden.auction2;

import command.AuctionCommands;
import gui.inventories.GuiManager;
import listener.AuctionPageListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Auction2 extends JavaPlugin {
    GuiManager guiManager = new GuiManager();

    AuctionPageListener auctionPageListener = new AuctionPageListener();
    AuctionCommands auctionCommands = new AuctionCommands();

    @Override
    public void onEnable() {
        // Plugin startup logic

        Bukkit.getPluginManager().registerEvents(auctionPageListener, this);

        getCommand("ah").setExecutor(auctionCommands);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
