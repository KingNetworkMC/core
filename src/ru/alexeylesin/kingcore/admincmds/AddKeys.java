 package ru.alexeylesin.kingcore.admincmds;
 import org.bukkit.Bukkit;
 import org.bukkit.command.Command;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.command.CommandSender;
 import org.bukkit.entity.Player;

import ru.alexeylesin.kingcore.api.KingCoreAPI;
 
 
 
 public class AddKeys
   implements CommandExecutor
 {
   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
       if (args.length == 0) {
         sender.sendMessage("§aKingCore §8| §fВыдача ключей от Сундука удачи");
         sender.sendMessage("§fВыдать игроку ключи - §e/chest add §7(Ник) (donate) (Кол-во)");
         sender.sendMessage("§fУстановить игроку ключи - §e/chest set §7(Ник) (donate) (Кол-во)");
         return true;
       } 
       if (args[0].equalsIgnoreCase("set")) {
         if (args.length < 3) {
           sender.sendMessage("§fУстановить игроку ключи - §e/chest set §7(Ник) (donate) (Кол-во)");
           return true;
         } 
         if (args.length == 4) {
           Player target = Bukkit.getPlayer(args[1]);
           int amount = Integer.parseInt(args[3]);
           if (args[2].equalsIgnoreCase("donate")) {
             if (target == null) {
               sender.sendMessage("§aKingCore §8| §fИгрока §6" + args[1] + "§f нет на сервере!");
               return true;
             } 
             if (target == sender) {
               sender.sendMessage("§aKingCore §8| §fЗачем консоли кейсы §6XD");
               return true;
             } 
             target.sendMessage("§aKingCore §8| §6" + sender.getName() + "§f установил Вам §e" + amount + "§f ключей от §7Сундука с привилегиями§f.");
             sender.sendMessage("§aKingCore §8| §fВы установили §e" + amount + "§f ключей от §7Сундука с привилегиями §fигроку §6" + target.getName());
             KingCoreAPI.setKeys(target, amount);
             return true;
           } 
         } 
       } 
       
       if (args[0].equalsIgnoreCase("add")) {
         if (args.length < 4) {
           sender.sendMessage("§fДобавить игроку ключи - §e/chest add §7(Ник) (donate) (Кол-во)");
           return true;
         } 
         if (args.length == 4) {
           Player target = Bukkit.getPlayer(args[1]);
           int amount = Integer.parseInt(args[3]);
           if (args[2].equalsIgnoreCase("donate")) {
             if (target == null) {
               sender.sendMessage("§aKingCore §8| §fИгрока §6" + args[1] + "§f нет на сервере!");
               return true;
             } 
             KingCoreAPI.addKeys(target, amount);
             target.sendMessage("§aKingCore §8| §6" + sender.getName() + " §fдобавил Вам §e" + amount + "§f ключей от §7Сундука с привилегиями§f. Теперь у вас §e" + KingCoreAPI.getKeys(target) + "§f ключей.");
             sender.sendMessage("§aKingCore §8| §fВы добавили §e" + amount + "§f ключей от §7Сундука с привилегиями §fигроку §6" + target.getName());
             return true;
           } 
         } 
       } 
     } 
     
     if (sender instanceof Player) {
       Player player = (Player)sender;
       if (!player.isOp()) {
         player.sendMessage("§aKingCore §8| §fКоманду могут использовать только §cАдминистраторы§f!");
         return true;
       } 
       if (args.length == 0) {
         player.sendMessage("§aKingCore §8| §fВыдача ключей от Сундука удачи");
         player.sendMessage("§fВыдать игроку ключи - §e/chest add §7(Ник) (donate) (Кол-во)");
         player.sendMessage("§fУстановить игроку ключи - §e/chest set §7(Ник) (donate) (Кол-во)");
         return true;
       } 
       if (args[0].equalsIgnoreCase("set")) {
         if (args.length < 4) {
           player.sendMessage("§fУстановить игроку ключи - §e/chest set §7(Ник) (donate) (Кол-во)");
           return true;
         } 
         if (args.length == 4) {
           Player target = Bukkit.getPlayer(args[1]);
           int amount = Integer.parseInt(args[3]);
           if (args[2].equalsIgnoreCase("donate")) {
             if (target == null) {
               player.sendMessage("§aKingCore §8| §fИгрока §6" + args[1] + "§f нет на сервере!");
               return true;
             } 
             if (target == player) {
               player.sendMessage("§aKingCore §8| §fВы установили себе §e" + amount + "§f ключей от §7Сундука с привилегиями§f. Теперь у вас ключей §e" + KingCoreAPI.getKeys(target));
               KingCoreAPI.setKeys(player, amount);
               return true;
             } 
             target.sendMessage("§aKingCore §8| §6" + player.getName() + "§f установил Вам §e" + amount + "§f ключей от §7Сундука с привилегиями§f.");
             player.sendMessage("§aKingCore §8| §fВы установили §e" + amount + "§f ключей от §7Сундука с привилегиями §fигроку §6" + target.getName());
             KingCoreAPI.setKeys(target, amount);
             return true;
           } 
         } 
       } 
       
       if (args[0].equalsIgnoreCase("add")) {
         if (args.length < 3) {
           player.sendMessage("§fДобавить игроку ключи - §e/chest add §7(Ник) (donate) (Кол-во)");
           return true;
         } 
         if (args.length == 4) {
           Player target = Bukkit.getPlayer(args[1]);
             int amount = Integer.parseInt(args[3]);
             if (args[2].equalsIgnoreCase("donate")) {
             if (target == null) {
               player.sendMessage("§aKingCore §8| §fИгрока §6" + args[1] + "§f нет на сервере!");
               return true;
             } 
             if (target == player) {
               KingCoreAPI.addKeys(player, amount);
               player.sendMessage("§aKingCore §8| §fВы добавили себе §e" + amount + "§f ключей от §7Сундука с привилегиями§f. Теперь у вас ключей: §e" + KingCoreAPI.getKeys(target));
               return true;
             } 
             KingCoreAPI.addKeys(target, amount);
             target.sendMessage("§aKingCore §8| §6" + player.getName() + "§f добавил Вам §e" + amount + "§f ключей от §7Сундука с привилегиями§f. Теперь у вас ключей §e" + KingCoreAPI.getKeys(target));
             player.sendMessage("§aKingCore §8| §fВы добавили §e" + amount + "§f ключей от §7Сундука с привилегиями §fигроку §6" + target.getName());
             return true;
           } 
         } 
       } 
     } 
     
     return true;
   }
 }