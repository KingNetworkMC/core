 package ru.alexeylesin.kingcore.admincmds;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;

import ru.alexeylesin.kingcore.MainClass;
 
 
 
 public class SetCase
   implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
       sender.sendMessage("§cКоманду можно использовать только в игре!");
       return true;
     } 
     if (sender instanceof Player) {
       Player player = (Player)sender;
       MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
       if (!player.isOp()) {
         player.sendMessage("§aKingCore §8| §fКоманду могут использовать только §cАдминистраторы§f сервера");
         return true;
       } 
       if (args.length == 0) {
         if (!plugin.cases.containsKey(player)) {
           plugin.cases.put(player, Integer.valueOf(1));
           player.sendMessage("§aKingCore §8| §fВозьмите блок, и поставьте его. Он станет кейсом.");
           return true;
         } 
         if (plugin.cases.containsKey(player)) {
           plugin.cases.remove(player);
           player.sendMessage("§aKingCore §8| §fВаши поставленные блоки больше не будут становиться кейсами.");
           return true;
         } 
       } 
     } 
     
     return false;
   }
 }