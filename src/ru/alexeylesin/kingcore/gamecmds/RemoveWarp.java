package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveWarp implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      sender.sendMessage("§cКоманду можно использовать только в игре!");
      return true;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (!player.hasPermission("kingnetwork.setwarp")) {
        player.sendMessage("§aKingNetwork §8| §fУ вас прав. Команда доступна от §5[Legend] §fи выше!");
        return true;
      } 
      if (args.length == 0) {
        player.sendMessage("§aKingNetwork §8| §fУдалить варп - §6/removewarp §7(Название)");
        return true;
      } 
      if (args.length > 1) {
        player.sendMessage("§aKingNetwork §8| §fНазвание варпа должно быть без пробелов.");
        return true;
      } 
      if (args.length == 1) {
        if (this.plugin.getConfig().getString("warps." + args[0] + ".creator") != player.getName()) {
          if (!player.isOp()) {
            player.sendMessage("§aKingNetwork §8| §fВы не можете удалить варп §e" + args[0] + "§f, так как вы не являетесь его создателем.");
            return true;
          } 
          KingCoreAPI.removeWarp(args[0]);
          player.sendMessage("§aKingNetwork §8| §fВарп §e" + args[0] + "§f удален.");
          return true;
        } 
        KingCoreAPI.removeWarp(args[0]);
        player.sendMessage("§aKingNetwork §8| §fВарп §e" + args[0] + "§f удален.");
        return true;
      } 
    } 
    return true;
  }
}
