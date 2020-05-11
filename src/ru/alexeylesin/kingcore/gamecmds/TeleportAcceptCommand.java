package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TeleportAcceptCommand implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        if (!this.plugin.teleport.containsKey(player)) {
          player.sendMessage("§aKingNetwork §8| §fУ вас нет запросов на телепортацию.");
          return true;
        } 
        if (this.plugin.teleport.containsKey(player)) {
          Player target = Bukkit.getPlayer((String)this.plugin.teleporttarget.get(player));
          if (target == null) {
            player.sendMessage("§aKingNetwork §8| §fКажется игрок §6" + (String)this.plugin.teleporttarget.get(player) + "§f вышел из сервера.");
            return true;
          } 
          target.teleport((Entity)player);
          target.sendMessage("§aKingNetwork §8| §fИгрок §6" + player.getName() + "§f §aпринял §fваш запрос на телепортацию.");
          player.sendMessage("§aKingNetwork §8| §fИгрок §6" + target.getName() + "§f телепортирован к вам.");
          this.plugin.teleport.remove(player);
          this.plugin.teleporttarget.remove(player);
          return true;
        } 
      } 
    } 
    return true;
  }
}
