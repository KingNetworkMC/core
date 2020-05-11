 package ru.alexeylesin.kingcore.admincmds;
 
 import org.bukkit.Bukkit;
 import org.bukkit.Location;
 import org.bukkit.WorldCreator;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;
 
 
 public class TPWorld
   implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
       return true;
     }
     if (sender instanceof Player) {
       Player player = (Player)sender;
       if (args.length == 1) {
         player.teleport(new Location(Bukkit.createWorld(WorldCreator.name(args[0])), 0.0D, 50.0D, 0.0D));
         return true;
       } 
     } 
     return true;
   }
 }