package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      sender.sendMessage("§cКоманду можно использовать только в игре!");
      return true;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        player.sendMessage("§aKingNetwork §8| §fЧтобы телепортироваться на варп, укажите его название.");
        return true;
      } 
      if (args.length > 1) {
        player.sendMessage("§aKingNetwork §8| §fНазвание варпа должно быть без пробелов!");
        return true;
      } 
      if (args.length == 1) {
        if (this.plugin.getConfig().getString("warps." + args[0].replace("a", "A")) == null) {
          player.sendMessage("§aKingNetwork §8| §fВарпа §e" + args[0] + "§f не существует!");
          return true;
        } 
        player.teleport(KingCoreAPI.getWarp(args[0]));
        player.sendMessage("§aKingNetwork §8| §fВы телепортированы на варп §e" + args[0] + "§f.");
        return true;
      } 
    } 
    return true;
  }
}
