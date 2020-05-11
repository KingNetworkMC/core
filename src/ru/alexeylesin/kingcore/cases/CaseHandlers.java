 package ru.alexeylesin.kingcore.cases;
 import org.bukkit.entity.Player;
 import org.bukkit.event.EventHandler;
 import org.bukkit.event.Listener;
 import org.bukkit.event.player.PlayerQuitEvent;

import ru.alexeylesin.kingcore.MainClass;
 
 public class CaseHandlers
   implements Listener
 {
   private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
   
   @EventHandler
   public void onQuit(PlayerQuitEvent e) {
     Player player = e.getPlayer();
     this.plugin.opencase.put(player, Integer.valueOf(12));
     if (this.plugin.opencase.containsKey(player))
     {     
       this.plugin.opencase.remove(player);
     }
   }
 }