package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCancelCommand implements CommandExecutor {
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
          target.sendMessage("§aKingNetwork §8| §fИгрок §6" + player.getName() + "§c отклонил §fзапрос на телепортацию.");
          player.sendMessage("§aKingNetwork §8| §fЗапрос на телепортацию от игрока §6" + target.getName() + "§c отклонен§f.");
          this.plugin.teleport.remove(player);
          this.plugin.teleporttarget.remove(player);
          return true;
        } 
      } 
      if (args.length == 1) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
          player.sendMessage("§aKingNetwork §8| §fВы не отправляли запросов игроку §6" + args[0]);
          return true;
        } 
        if (target == player) {
          player.sendMessage("§aKingNetwork §8| §fТак делать нельзя.");
          return true;
        } 
        if (player.getName() != this.plugin.teleporttarget.get(target)) {
          player.sendMessage("§aKingNetwork §8| §fВы не отправляли запросов игроку §6" + args[0]);
          return true;
        } 
        if (this.plugin.teleport.containsKey(target)) {
          this.plugin.teleport.remove(target);
          this.plugin.teleporttarget.remove(target);
          player.sendMessage("§aKingNetwork §8| §fВаш запрос на телепортацию к игроку §6" + args[0] + "§f отменен.");
          target.sendMessage("§aKingNetwork §8| §fИгрок §6" + player.getName() + "§c отменил §fзапрос на телепортацию к вам.");
          return true;
        } 
      } 
    } 
    return true;
  }
}
