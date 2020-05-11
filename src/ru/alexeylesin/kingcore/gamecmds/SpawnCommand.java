package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      sender.sendMessage("§cКоманду можно использовать только в игре!");
      return true;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        player.teleport(KingCoreAPI.getSpawn());
        player.sendMessage("§aKingNetwork §8| §fВы телепортированы на спавн.");
        return true;
      } 
      if (args.length > 0) {
        player.teleport(KingCoreAPI.getSpawn());
        player.sendMessage("§aKingNetwork §8| §fВы телепортированы на спавн.");
        return true;
      } 
    } 
    return true;
  }
}
