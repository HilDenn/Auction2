package command;

import gui.AuctionItem;
import gui.AuctionManager;
import gui.inventories.GuiManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AuctionCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) return true;
        Player player = ((Player) sender).getPlayer();

        if(args.length == 0){
            player.openInventory(GuiManager.getMainPage().getInventory());
            return false;
        } else if(args.length == 3){
            if(args[0].equals("sell")){
                int price;
                int amount;
                try {
                    amount = Integer.parseInt(args[1].trim());
                    price = Integer.parseInt(args[2].trim());
                    player.sendMessage("num = " + args[1]);
                } catch (NumberFormatException nfe) {
                    System.out.println("NumberFormatException: " + nfe.getMessage());
                    player.sendMessage("Введите количество и цену!");
                    return true;
                }

                if(price != 0 && amount != 0 && args[1] != null && args[2] != null && player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                    if(player.getInventory().getItemInMainHand().getAmount() >= price) {
                        player.sendMessage("Ваш предмет в руках" + player.getInventory().getItemInMainHand().toString());

                        ItemStack itemStack = player.getInventory().getItemInHand();

                        AuctionManager.sellItem(player, itemStack, amount, price);

                        return false;
                    }
                } else return true;
            } else return true;
        } else return true;
        return true;
    }

}
