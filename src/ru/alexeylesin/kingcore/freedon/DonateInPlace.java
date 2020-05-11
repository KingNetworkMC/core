package ru.alexeylesin.kingcore.freedon;

import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class DonateInPlace implements Listener {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public void setCountdown(final Player player) {
    final PermissionUser user = PermissionsEx.getUser(player);
    (new BukkitRunnable() {
        public void run() {
          if (!DonateInPlace.this.plugin.time.containsKey(player))
            cancel(); 
          if (DonateInPlace.this.plugin.time.containsKey(player)) {
            if (((Integer)DonateInPlace.this.plugin.time.get(player)).intValue() > 0) {
              String text = "§6Бесплатный донат §8| §fСтойте не выходя из точки: §a" + KingCoreAPI.convertSeconds(((Integer)DonateInPlace.this.plugin.time.get(player)).intValue());
              KingCoreAPI.sendActionBar(player, text);
              DonateInPlace.this.plugin.time.put(player, Integer.valueOf(((Integer)DonateInPlace.this.plugin.time.get(player)).intValue() - 1));
            } 
            if (((Integer)DonateInPlace.this.plugin.time.get(player)).intValue() == 0) {
              user.addGroup("Premium");
              player.sendMessage("§6Бесплатный донат §8| §fВы простояли на точке §a" + KingCoreAPI.convertSeconds(DonateInPlace.this.plugin.getConfig().getInt("donateinplace.time")) + "§f, и получили донат §3[Премиум]§f!");
              player.teleport(KingCoreAPI.getSpawn());
              DonateInPlace.this.plugin.time.remove(player);
              player.performCommand("spawn");
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 20L);
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e) {
    Player player = e.getPlayer();
    PermissionUser user = PermissionsEx.getUser(player);
    if (!this.plugin.time.containsKey(player) && 
      e.getFrom().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(KingCoreAPI.getBlock(299.0D, 64.0D, 2.0D))) {
      if (user.inGroup("Premium")) {
        KingCoreAPI.sendActionBar(player, "§6Бесплатный донат §8| §fВаш статус лучше, чем вы можете получить здесь.");
        return;
      } 
      this.plugin.time.put(player, Integer.valueOf(this.plugin.getConfig().getInt("time")));
      player.sendMessage("§6Бесплатный донат §8| §fПостойте на точке §a" + KingCoreAPI.convertSeconds(this.plugin.getConfig().getInt("time")) + "§f, и получите §3☾Премиум☽ §fбесплатно! Если совершите прыжок, или покинете точку, донат вы не получите.");
      System.out.print("Игрок " + player.getName() + " встал на точку.");
      setCountdown(player);
      return;
    } 
    if (this.plugin.time.containsKey(player) && 
      !e.getFrom().getBlock().getRelative(BlockFace.DOWN).getLocation().equals(KingCoreAPI.getBlock(299.0D, 64.0D, 2.0D))) {
      KingCoreAPI.sendActionBar(player, "");
      player.sendMessage("§6Бесплатный донат §8| §cВы покинули точку.");
      System.out.print("Игрок " + player.getName() + " покинул точку.");
      this.plugin.time.remove(player);
      setCountdown(player);
    } 
  }
  
  @EventHandler
  public void onPlace(BlockPlaceEvent e) {
    Player player = e.getPlayer();
    if (this.plugin.mode.containsKey(player)) {
      e.getBlockPlaced().setMetadata("donate", (MetadataValue)new FixedMetadataValue((Plugin)this.plugin, "donate"));
      player.sendMessage("§aKingCore §8| §fБлок точки §a§nустановлен§f по координатам §cx: " + e.getBlockPlaced().getLocation().getX() + " y: " + e.getBlockPlaced().getLocation().getY() + " z: " + e.getBlockPlaced().getLocation().getZ());
    } 
  }
  
  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    Player player = e.getPlayer();
    if (this.plugin.mode.containsKey(player) && 
      e.getBlock().hasMetadata("donate")) {
      e.getBlock().removeMetadata("donate", (Plugin)this.plugin);
      player.sendMessage("§aKingCore донат §8| §fБлок точки §c§тудален§f по координатам §cx: " + e.getBlock().getLocation().getX() + " y: " + e.getBlock().getLocation().getY() + " z: " + e.getBlock().getLocation().getZ());
    } 
  }
}
