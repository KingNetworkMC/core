package ru.alexeylesin.kingcore.freedon;

import ru.alexeylesin.kingcore.MainClass;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class PromoCodes implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      sender.sendMessage("§cКоманду можно использовать только в игре!");
      return true;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      PermissionUser user = PermissionsEx.getUser(player);
      if (args.length == 0) {
        player.sendMessage("§aПромокоды §8| §fЧтобы активировать промокод, напишите команду - §a/promocode §7(Код)");
        player.sendMessage("§e[!] §fВы должны обязательно знать, что промокоды могут выдавать только §cАдминистраторы §fсервера!");
        player.sendMessage("§e[!] §fПромокод можно получить участвуя в конкурсах а так же на стримах §6§lФАНА§f.");
        return true;
      } 
      if (args.length > 1) {
        player.sendMessage("§aПромокоды §8| §fЧтобы активировать промокод, напишите команду - §a/promocode §7(Код)");
        player.sendMessage("§e[!] §fВы должны обязательно знать, что промокоды могут выдавать только §cАдминистраторы §fсервера!");
        player.sendMessage("§e[!] §fПромокод можно получить участвуя в конкурсах а так же на стримах §6§lФАНА§f.");
        return true;
      } 
      if (args.length == 1) {
        if (MainClass.promocode.getString(String.valueOf(args[0]) + "." + player.getName() + ".used") == "yes") {
          player.sendMessage("§aПромокоды §8| §fПромокод уже использован!");
          return true;
        } 
        if (args[0].equalsIgnoreCase("KINGNETWORK-OPEN-NOW!")) {
          MainClass.promocode.set(String.valueOf(args[0]) + "." + player.getName() + ".used", "yes");
          MainClass.savePromo();
          if (user.inGroup("Vip") || 
            user.inGroup("Premium") || 
            user.inGroup("Creative") || 
            user.inGroup("Elite") || 
            user.inGroup("Legend") || 
            user.inGroup("Ultra") || 
            user.inGroup("Lord") || 
            user.inGroup("Imperator") || 
            user.inGroup("Operator")) {
            player.sendMessage("§aПромокоды §8| §fОй, кажется у вас уже есть этот подарок, но ничего! Вы получаете вместо доната §e1500$§f!");
            return true;
          } 
          player.sendMessage("§aПромокоды §8| §fПромокод §6" + args[0] + "§f использован! Вы получили донат §a[Вип]");
          user.addGroup("Vip");
          player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2.0F, 2.0F);
          return true;
        } 
        player.sendMessage("§aПромокоды §8| §fПромокода §c" + args[0] + "§f не существует!");
        return true;
      } 
    } 
    return false;
  }
}
