package ru.alexeylesin.kingcore.gamecmds;

import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RandomTP implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      return true; 
    if (sender instanceof Player && 
      args.length == 0) {
      Location random = new Location(Bukkit.getWorld("world"), 
          ThreadLocalRandom.current().nextDouble(1000.0D), (
          (Player)sender).getLocation().getY(), 
          ThreadLocalRandom.current().nextDouble(1000.0D));
      ((Player)sender).teleport(random);
    } 
    return false;
  }
}
