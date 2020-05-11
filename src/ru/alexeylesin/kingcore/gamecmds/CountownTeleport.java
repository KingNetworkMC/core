package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class CountownTeleport implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public void setCountDown(final Player player) {
    (new BukkitRunnable() {
        public void run() {
          if (!CountownTeleport.this.plugin.teleport.containsKey(player))
            cancel(); 
          if (CountownTeleport.this.plugin.teleport.containsKey(player)) {
            if (((Integer)CountownTeleport.this.plugin.teleport.get(player)).intValue() > 0)
              CountownTeleport.this.plugin.teleport.put(player, Integer.valueOf(((Integer)CountownTeleport.this.plugin.teleport.get(player)).intValue() - 1)); 
            if (((Integer)CountownTeleport.this.plugin.teleport.get(player)).intValue() == 0) {
              CountownTeleport.this.plugin.teleport.remove(player);
              CountownTeleport.this.plugin.teleporttarget.remove(player);
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 20L);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      return true; 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        player.sendMessage("Запрос на телепортацию - §6/tpa §7(Ник)");
        player.sendMessage("Так же можно указать сообщение - §6/tpa §7(Ник) (Сообщение)");
        return true;
      } 
      if (args.length == 1) {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
          player.sendMessage("§aKingNetwork §8| §fИгрока §6" + args[0] + "§f нет на сервере.");
          return true;
        } 
        if (target == player) {
          player.sendMessage("§aKingNetwork §8| §fВы не можете телепортироваться к самому себе.");
          return true;
        } 
        if (target != null) {
          if (player.getName() == this.plugin.teleporttarget.get(target)) {
            player.sendMessage("§fВы уже отправили запрос игроку §6" + args[0]);
            return true;
          } 
          this.plugin.teleport.put(target, Integer.valueOf(this.plugin.getConfig().getInt("teleport.time")));
          this.plugin.teleporttarget.put(target, player.getName());
          setCountDown(player);
          player.sendMessage("§fЗапрос на телепортацию к игроку §6" + args[0] + "§f отправлен.");
          player.sendMessage("§fВы можете отменить запрос - §6/tpcancel §7(Ник игрока)");
          target.sendMessage("§fИгрок §6" + player.getName() + "§f хочет телепортироваться к вам.");
          target.sendMessage("§fУ вас есть §6" + KingCoreAPI.convertSeconds(this.plugin.getConfig().getInt("teleport.time")) + "§f чтобы принять запрос.");
          target.sendMessage("§fПринять запрос на телепортацию - §a/tpyes");
          target.sendMessage("§fОтклонить запрос на телепортацию - §c/tpno");
          return true;
        } 
      } 
      if (args.length > 1) {
        Player target = Bukkit.getPlayer(args[0]);
        StringBuilder x = new StringBuilder();
        for (int i = 1; i < args.length; i++)
          x.append(String.valueOf(String.valueOf(args[i])) + " "); 
        String message = x.toString().trim();
        if (target == null) {
          player.sendMessage("§aKingNetwork §8| §fИгрока §6" + args[0] + "§f нет на сервере.");
          return true;
        } 
        if (target == player) {
          player.sendMessage("§aKingNetwork §8| §fВы не можете телепортироваться к самому себе.");
          return true;
        } 
        if (target != null) {
          if (player.getName() == this.plugin.teleporttarget.get(target)) {
            player.sendMessage("§fВы уже отправили запрос игроку §6" + args[0]);
            return true;
          } 
          this.plugin.teleport.put(target, Integer.valueOf(this.plugin.getConfig().getInt("teleport.time")));
          this.plugin.teleporttarget.put(target, player.getName());
          setCountDown(player);
          player.sendMessage("§fЗапрос на телепортацию к игроку §6" + args[0] + "§f отправлен.");
          player.sendMessage("§fВы можете отменить запрос - §6/tpcancel §7(Ник игрока)");
          target.sendMessage("§fИгрок §6" + player.getName() + "§f хочет телепортироваться к вам.");
          target.sendMessage("§fУ вас есть §6" + KingCoreAPI.convertSeconds(this.plugin.getConfig().getInt("teleport.time")) + "§f чтобы принять запрос.");
          target.sendMessage("§fПринять запрос на телепортацию - §a/tpyes");
          target.sendMessage("§fОтклонить запрос на телепортацию - §c/tpno");
          target.sendMessage("§fСообщение от игрока: §7" + message);
          return true;
        } 
      } 
    } 
    return true;
  }
}
