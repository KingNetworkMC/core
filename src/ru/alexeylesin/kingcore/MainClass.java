 package ru.alexeylesin.kingcore;
 
 import java.io.File;
 import java.io.IOException;
 import java.util.HashMap;

import ru.alexeylesin.kingcore.admincmds.AddKeys;
import ru.alexeylesin.kingcore.admincmds.KingCore;
import ru.alexeylesin.kingcore.admincmds.SetCase;
import ru.alexeylesin.kingcore.admincmds.SetSpawnCommand;
import ru.alexeylesin.kingcore.admincmds.TPWorld;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import ru.alexeylesin.kingcore.cases.CaseHandlers;
import ru.alexeylesin.kingcore.freedon.DonateInPlace;
import ru.alexeylesin.kingcore.freedon.DonateInPlaceSetPoint;
import ru.alexeylesin.kingcore.freedon.PromoCodes;
import ru.alexeylesin.kingcore.gamecmds.CountownTeleport;
import ru.alexeylesin.kingcore.gamecmds.GamemodeCommand;
import ru.alexeylesin.kingcore.gamecmds.OpCommand;
import ru.alexeylesin.kingcore.gamecmds.ProfileCommand;
import ru.alexeylesin.kingcore.gamecmds.RandomTeleport;
import ru.alexeylesin.kingcore.gamecmds.SpawnCommand;
import ru.alexeylesin.kingcore.gamecmds.TeleportAcceptCommand;
import ru.alexeylesin.kingcore.gamecmds.TeleportCancelCommand;
import ru.alexeylesin.kingcore.games.KingOfTheHill;
import ru.alexeylesin.kingcore.gui.Case;
import ru.alexeylesin.kingcore.gui.ChoseGender;
import ru.alexeylesin.kingcore.gui.Gamemode;
import ru.alexeylesin.kingcore.gui.Op;
import ru.alexeylesin.kingcore.gui.Profile;
import ru.alexeylesin.kingcore.mine.MineHandlers;

import org.bukkit.Bukkit;
 import org.bukkit.command.CommandExecutor;
 import org.bukkit.configuration.file.FileConfiguration;
 import org.bukkit.configuration.file.YamlConfiguration;
 import org.bukkit.entity.Player;
 import org.bukkit.event.Listener;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.plugin.java.JavaPlugin;
 
 public class MainClass
   extends JavaPlugin {
   public static File freedonate = new File("plugins/KingCore", "players.yml");
   public static FileConfiguration free = (FileConfiguration)YamlConfiguration.loadConfiguration(freedonate);
   public static File promo = new File("plugins/KingCore", "promo.yml");
   public static FileConfiguration promocode = (FileConfiguration)YamlConfiguration.loadConfiguration(promo);
   public static File p = new File("plugins/KingCore", "players.yml");
   public static FileConfiguration player = (FileConfiguration)YamlConfiguration.loadConfiguration(p);
   
   public HashMap<Player, String> teleporttarget = new HashMap<>();
   public HashMap<Player, Integer> time = new HashMap<>();
   public HashMap<Player, Integer> mode = new HashMap<>();
   public HashMap<Player, Integer> teleport = new HashMap<>();
   public HashMap<Player, Integer> cases = new HashMap<>();
   public HashMap<Player, Integer> opencase = new HashMap<>();
   public HashMap<Player, Integer> timetp = new HashMap<>();
   public static HashMap<Player, Integer> dayly = new HashMap<>();
   public HashMap<Player, Integer> playedtime = new HashMap<>();
   public HashMap<Player, Boolean> played = new HashMap<>();
   
   public static void savePlayer() {
     try {
       player.save(p);
     } catch (IOException e) {
       System.out.print("Файл Players не сохранен.");
     } 
   }
   
   public static void savePromo() {
     try {
       promocode.save(promo);
     } catch (IOException e) {
       System.out.print("Файл Promocode не сохранен.");
     } 
   }
   
   public static void saveFree() {
     try {
       free.save(freedonate);
     } catch (IOException e) {
       System.out.print("Файл FreeDonate не сохранен.");
     } 
   }
 
   
   public void onEnable() {
     Bukkit.getServer().getWorld("world").getBlockAt(KingCoreAPI.getCase()).removeMetadata("open", (Plugin)this);
     for (Player online : Bukkit.getOnlinePlayers()) {
       Player player = Bukkit.getPlayer(online.getName());
       if (KingCoreAPI.getDaylyTime(player) > 0) {
         dayly.put(player, Integer.valueOf(KingCoreAPI.getDaylyTime(player)));
         KingCoreAPI.daylyCountdown(player);
       } 
     } 
     for (Player online : Bukkit.getOnlinePlayers()) {
       Player player = Bukkit.getPlayer(online.getName());
       if (KingCoreAPI.getPlayedTimes(player) >= 0) {
         this.played.put(player, Boolean.valueOf(true));
         KingCoreAPI.addPlayedtimes(player);
       } 
     } 
     Bukkit.getConsoleSender().sendMessage("[KingCore] Плагин включен!");
     getCommand("promocode").setExecutor((CommandExecutor)new PromoCodes());
     getCommand("donateinplace").setExecutor((CommandExecutor)new DonateInPlaceSetPoint());
     getCommand("gamemode").setExecutor((CommandExecutor)new GamemodeCommand());
     getCommand("tpa").setExecutor((CommandExecutor)new CountownTeleport());
     getCommand("tpyes").setExecutor((CommandExecutor)new TeleportAcceptCommand());
     getCommand("tpno").setExecutor((CommandExecutor)new TeleportCancelCommand());
     getCommand("spawn").setExecutor((CommandExecutor)new SpawnCommand());
     getCommand("setspawn").setExecutor((CommandExecutor)new SetSpawnCommand());
     getCommand("setcase").setExecutor((CommandExecutor)new SetCase());
     getCommand("chest").setExecutor((CommandExecutor)new AddKeys());
     getCommand("rtp").setExecutor((CommandExecutor)new RandomTeleport());
     getCommand("profile").setExecutor((CommandExecutor)new ProfileCommand());
     getCommand("opka").setExecutor((CommandExecutor)new OpCommand());
     getCommand("tpworld").setExecutor((CommandExecutor)new TPWorld());
     getCommand("kingcore").setExecutor((CommandExecutor)new KingCore());
     Bukkit.getPluginManager().registerEvents((Listener)new DonateInPlace(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new CaseHandlers(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new Gamemode(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents(new Listeners(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new Case(), (Plugin)this);     Bukkit.getPluginManager().registerEvents((Listener)new MineHandlers(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new KingOfTheHill(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new Profile(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new ChoseGender(), (Plugin)this);
     Bukkit.getPluginManager().registerEvents((Listener)new Op(), (Plugin)this);
     saveDefaultConfig();
   }
   
   public void onDisable() {
     this.time.clear();
     Bukkit.getConsoleSender().sendMessage("[KingCore] Плагин выключен.");
   }
 }