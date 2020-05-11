package ru.alexeylesin.kingcore.games;

import java.util.HashMap;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class KingOfTheHill implements Listener {
  public HashMap<Player, Integer> game = new HashMap<>();
  
  private void gameCountdown(final Player player) {
    (new BukkitRunnable() {
        public void run() {
          if (!KingOfTheHill.this.game.containsKey(player))
            cancel(); 
          if (KingOfTheHill.this.game.containsKey(player)) {
            if (((Integer)KingOfTheHill.this.game.get(player)).intValue() > 0) {
              KingOfTheHill.this.game.put(player, Integer.valueOf(((Integer)KingOfTheHill.this.game.get(player)).intValue() - 1));
              KingCoreAPI.sendActionBar(player, "§fПродержитесь на вершине §6" + KingCoreAPI.convertSeconds(((Integer)KingOfTheHill.this.game.get(player)).intValue()) + "§f, и получите §6200$");
            } 
            if (((Integer)KingOfTheHill.this.game.get(player)).intValue() == 0) {
              KingCoreAPI.sendActionBar(player, "§fВы продержались на вершине §6" + KingCoreAPI.convertSeconds(60L) + "§f! Вам начислено §6200$");
              KingOfTheHill.this.game.remove(player);
              player.performCommand("warp king");
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)MainClass.getPlugin(MainClass.class), 0L, 20L);
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    Player player = e.getPlayer();
    if (!this.game.containsKey(player) && 
      e.getFrom().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(KingCoreAPI.getBlock(311.0D, 69.0D, -17.0D))) {
      this.game.put(player, Integer.valueOf(60));
      gameCountdown(player);
    } 
    if (this.game.containsKey(player) && 
      !e.getFrom().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(KingCoreAPI.getBlock(311.0D, 69.0D, -17.0D))) {
      this.game.remove(player);
      gameCountdown(player);
      KingCoreAPI.sendActionBar(player, "§fВы упали с вершины. Попробуйте еще раз §c:(");
    } 
  }
}
