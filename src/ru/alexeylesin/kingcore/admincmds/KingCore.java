 package ru.alexeylesin.kingcore.admincmds;
 
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;
 
 
 public class KingCore
   implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender instanceof org.bukkit.command.ConsoleCommandSender && 
       args.length == 0) {
       sender.sendMessage("§aKingCore §fby §ealexeylesin");
       sender.sendMessage("§fПоставить кейс - §e/setcase");
       sender.sendMessage("§fУстановить спавн - §e/setspawn");
       sender.sendMessage("§fСундук удачи - §e/chest");
       sender.sendMessage("§fТелепортироваться в мир - §e/tpworld §7(Название мира)");
       return true;
     } 
     
     if (sender instanceof Player) {
       if (args.length == 0) {
         sender.sendMessage("§aKingCore §fby §ealexeylesin");
         sender.sendMessage("§fПоставить кейс - §e/setcase");
         sender.sendMessage("§fУстановить спавн - §e/setspawn");
         sender.sendMessage("§fСундук удачи - §e/chest");
         sender.sendMessage("§fТелепортироваться в мир - §e/tpworld §7(Название мира)");
         return true;
       } 
     }  
     return true;
   }
 }