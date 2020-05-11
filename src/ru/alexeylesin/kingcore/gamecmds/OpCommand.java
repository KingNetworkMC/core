package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.gui.Op;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class OpCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      return true; 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        Op op = new Op();
        op.open(player);
        return true;
      } 
      player.sendMessage("§aKingNetwork §8| §fИспользуйте - §e/opka");
      return true;
    } 
    return false;
  }
}
