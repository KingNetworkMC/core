package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class RandomTeleport implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public Location getSafeLocation(World w) {
    int randomX = (int)(Math.random() * 1000.0D);
    int randomZ = (int)(Math.random() * 1000.0D);
    int randomY = w.getHighestBlockYAt(randomX, randomZ);
    Location randomLocation = new Location(w, randomX, randomY, randomZ);
    if (randomLocation.getBlock().getBiome().toString().contains("OCEAN"))
      return getSafeLocation(w); 
    return randomLocation.add(0.0D, 1.0D, 0.0D);
  }
  
  private void teleport(final Player player) {
    (new BukkitRunnable() {
        public void run() {
          if (!RandomTeleport.this.plugin.timetp.containsKey(player))
            cancel(); 
          if (RandomTeleport.this.plugin.timetp.containsKey(player)) {
            int times = ((Integer)RandomTeleport.this.plugin.timetp.get(player)).intValue();
            if (((Integer)RandomTeleport.this.plugin.timetp.get(player)).intValue() > 0) {
              times--;
              RandomTeleport.this.plugin.timetp.put(player, Integer.valueOf(times));
              KingCoreAPI.sendActionBar(player, "§7Подбираем Вам локацию. Не двигайтесь §a" + KingCoreAPI.convertSeconds(((Integer)RandomTeleport.this.plugin.timetp.get(player)).intValue()));
            } 
            if (times == 0) {
              RandomTeleport.this.plugin.timetp.remove(player);
              Location loc = RandomTeleport.this.getSafeLocation(player.getWorld()).clone();
              player.teleport(loc);
              KingCoreAPI.sendActionBar(player, "§7Локация §aНайдена§7! Вы телепортированы по координатам §ax:" + player.getLocation().getX() + ", y: " + player.getLocation().getY() + ", z: " + player.getLocation().getZ());
              player.sendMessage("§aKingNetwork §8| §fВы телепортированы по координатам §ax:" + player.getLocation().getX() + ", y: " + player.getLocation().getY() + ", z: " + player.getLocation().getZ());
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)MainClass.getPlugin(MainClass.class), 0L, 20L);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender)
      return true; 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        player.sendMessage("§aKingNetwork §8| §fИдет поиск локации, пожалуйста не двигайтесь!");
        this.plugin.timetp.put(player, Integer.valueOf(4));
        teleport(player);
        return true;
      } 
    } 
    return true;
  }
}
