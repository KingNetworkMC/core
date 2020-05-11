package ru.alexeylesin.kingcore;
 
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import ru.alexeylesin.kingcore.api.KingCoreAPI;

public class Listeners
   implements Listener {
   @EventHandler
   public void onBlockPlace(BlockPlaceEvent e) {
     if (((MainClass)MainClass.getPlugin(MainClass.class)).cases.containsKey(e.getPlayer())) {
       KingCoreAPI.setCase(e.getBlockPlaced().getLocation());
     }
   }
   
   @EventHandler
   public void onBlockBreak(BlockBreakEvent e) {
     if (e.getBlock().getLocation().equals(KingCoreAPI.getCase())) {
         e.getPlayer().sendMessage("§aKingCore §8| §fЭтот блок является кейсом. Его ломать нельзя.");
         e.setCancelled(true);
       } 
     }
 
   
   @EventHandler
   public void onJoin(PlayerJoinEvent e) {
     Player player = e.getPlayer();
     if (!player.hasPlayedBefore()) {
       player.teleport(KingCoreAPI.getSpawn());
       for (Player online : Bukkit.getOnlinePlayers()) {
         online.sendMessage("§aKingNetwork §8| §fДавайте поприветствуем нового игрока §e" + player.getName() + "§f!");
       }
     } 
   }
   
   @EventHandler
   public void onMove(PlayerMoveEvent e) {
     Player player = e.getPlayer();
     if (((MainClass)MainClass.getPlugin(MainClass.class)).timetp.containsKey(player) && (
       e.getFrom().getBlockX() != e.getTo().getBlockX() || 
       e.getFrom().getBlockY() != e.getTo().getBlockY() || 
       e.getFrom().getBlockZ() != e.getTo().getBlockZ())) {
       ((MainClass)MainClass.getPlugin(MainClass.class)).timetp.remove(player);
       KingCoreAPI.sendActionBar(player, "§7Телепортация §cотменена§7! Вы сдвинулись с места!");
       player.sendMessage("§aKingNetwork §8| §fПоиск локации отменен! Вы сдвинулись с места.");
     } 
   }
 
   
   @EventHandler
   public void onResp(final PlayerDeathEvent e) {
     e.getEntity().getPlayer().teleport(KingCoreAPI.getSpawn());
     (new BukkitRunnable()
       {
         public void run()
         {
           e.getEntity().getPlayer().spigot().respawn();
           e.getEntity().getPlayer().teleport(KingCoreAPI.getSpawn());
           cancel();
         }
       }).runTaskTimer((Plugin)MainClass.getPlugin(MainClass.class), 0L, 40L);
   }
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void clearJoinMessage(PlayerJoinEvent e) {
     e.setJoinMessage(null);
   }
   
   @EventHandler(priority = EventPriority.HIGHEST)
   public void clearQuitMessage(PlayerQuitEvent e) {
     e.setQuitMessage(null);
   }
   
   @EventHandler
   public void onPlac(BlockPlaceEvent e) {
     Player player = e.getPlayer();
     if (e.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.TNT)) {
       if (e.getBlockPlaced().getType().equals(Material.REDSTONE_BLOCK)) {
         player.sendMessage("§aKingNetwork §8| §fНа сервере запрещено активировать динамит!");
         e.setCancelled(true);
       } else {
         return;
       } 
     }
     if (e.getBlock().getRelative(BlockFace.DOWN).getType().equals(Material.REDSTONE_BLOCK)) {
       if (e.getBlockPlaced().getType().equals(Material.TNT)) {
         player.sendMessage("§aKingNetwork §8| §fНа сервере запрещено активировать динамит!");
         e.setCancelled(true);
       } else {
         return;
       } 
     }
   }
   
 
   
   @EventHandler
   public void onInt(PlayerInteractEvent e) {
     Action action = e.getAction();
     Player player = e.getPlayer();
     if (action.equals(Action.RIGHT_CLICK_BLOCK))
       if (e.getClickedBlock().getType().equals(Material.TNT)) {
         if (player.getInventory().getItemInMainHand().getType().equals(Material.FLINT_AND_STEEL)) {
           e.setCancelled(true);
           player.sendMessage("§aKingNetwork §8| §fНа сервере запрещено поджигать динамиты.");
         } 
       } else {
         return;
       }  
   }
 }