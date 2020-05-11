 package ru.alexeylesin.kingcore.admincmds;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;

import ru.alexeylesin.kingcore.api.KingCoreAPI;
 
 public class SetSpawnCommand
   implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
       sender.sendMessage("§cКоманду можно использовать только в игре!");
       return true;
     } 
     if (sender instanceof Player) {
       Player player = (Player)sender;
       if (!player.isOp()) {
         player.sendMessage("§aKingCore §8| §fКоманду могут использовать только §cАдминистраторы§f сервера");
         return true;
       } 
       if (args.length == 0) {
         KingCoreAPI.setSpawn(player.getLocation());
         player.sendMessage("§aKingCore §8| §fТочка спавна установлена.");
         return true;
       } 
       if (args.length > 0) {
         KingCoreAPI.setSpawn(player.getLocation());
         player.sendMessage("§aKingCore §8| §fТочка спавна установлена.");
         return true;
       } 
     } 
     return true;
   }
 }