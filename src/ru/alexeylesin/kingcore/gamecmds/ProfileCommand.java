package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.gui.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ProfileCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      return true; 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        Profile profile = new Profile();
        profile.open(player);
        return true;
      } 
      player.sendMessage("§aKingNetwork §8| §fИспользуйте - §e/profile");
      return true;
    } 
    return false;
  }
}
