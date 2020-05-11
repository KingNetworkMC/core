 package ru.alexeylesin.kingcore.api;
 
 import com.earth2me.essentials.api.Economy;
 import com.earth2me.essentials.api.NoLoanPermittedException;
 import com.earth2me.essentials.api.UserDoesNotExistException;
 import java.util.ArrayList;
 import java.util.concurrent.TimeUnit;

 import net.minecraft.server.v1_12_R1.IChatBaseComponent;
 import net.minecraft.server.v1_12_R1.Packet;
 import net.minecraft.server.v1_12_R1.PacketPlayOutTitle;
 import org.bukkit.Bukkit;
 import org.bukkit.Location;
 import org.bukkit.Material;
 import org.bukkit.block.Block;
 import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
 import org.bukkit.entity.Player;
 import org.bukkit.inventory.Inventory;
 import org.bukkit.inventory.ItemStack;
 import org.bukkit.inventory.meta.ItemMeta;
 import org.bukkit.plugin.Plugin;
 import org.bukkit.scheduler.BukkitRunnable;

import ru.alexeylesin.kingcore.MainClass;
 import ru.tehkode.permissions.PermissionUser;
 import ru.tehkode.permissions.bukkit.PermissionsEx;
 
 public class KingCoreAPI {
   private static MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
 
   
   public static String convertSeconds(long seconds) {
     long hour = TimeUnit.SECONDS.toHours(seconds) - TimeUnit.SECONDS.toDays(seconds) * 60L;
     long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
     long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
     if (hour > 0L) {
       return String.valueOf(String.valueOf(hour)) + " ч. " + minute + " мин. " + second + " сек.";
     }
     if (hour == 1L) {
       return String.valueOf(String.valueOf(hour)) + " ч.";
     }
     if (minute == 1L) {
       return String.valueOf(String.valueOf(minute)) + " мин";
     }
     if (minute > 0L) {
       return String.valueOf(String.valueOf(minute)) + " мин. " + second + " сек.";
     }
     return String.valueOf(String.valueOf(second)) + " сек.";
   }
   
   public static void sendActionBar(Player player, String message) {
     PacketPlayOutTitle action = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.ACTIONBAR, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"));
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)action);
   }
   
   public static void sendTitle(Player player, String title_message, String subtitle_message) {
     PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title_message + "\"}"));
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)title);
     PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle_message + "\"}"));
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)subtitle);
   }
   
   public static void sendTitle(Player player, String title_message, String subtitle_message, int arg1, int arg2, int arg3) {
     PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title_message + "\"}"), arg1, arg2, arg3);
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)title);
     PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle_message + "\"}"), arg1, arg2, arg3);
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)subtitle);
   }
   
   public static void sendTitleOnly(Player player, String title_message) {
     PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title_message + "\"}"));
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)title);
   }
   
   public static void sendSubTitleOnly(Player player, String subtitle_message) {
     PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle_message + "\"}"));
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)subtitle);
   }
   
   public static void sendTitleOnly(Player player, String title_message, int arg1, int arg2, int arg3) {
     PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title_message + "\"}"), arg1, arg2, arg3);
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)title);
   }
   
   public static void sendSubTitleOnly(Player player, String subtitle_message, int arg1, int arg2, int arg3) {
     PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle_message + "\"}"), arg1, arg2, arg3);
     (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)subtitle);
   }
   
   public static void addPlayedTimes(Player player, int time) {
     MainClass.player.set("players." + player.getName() + ".playedtimes", Integer.valueOf(getPlayedTimes(player) + time));
     MainClass.savePlayer();
   }
   
   public static int getPlayedTimes(Player player) {
     return MainClass.player.getInt("players." + player.getName() + ".playedtimes");
   }
   
   public static ItemStack createItem(Material type) {
     ItemStack item = new ItemStack(type);
     return item;
   }
   
   public static ItemStack createItem(Material type, String name) {
     ItemStack item = new ItemStack(type);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     item.setItemMeta(meta);
     return item;
   }
   
   public static ItemStack createItem(Material type, String name, ArrayList<String> lore) {
     ItemStack item = new ItemStack(type);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     meta.setLore(lore);
     item.setItemMeta(meta);
     return item;
   }
   
   public static ItemStack createColorGlass(int colorid, String name, ArrayList<String> lore) {
     ItemStack item = new ItemStack(Material.STAINED_GLASS, 1, (short)(byte)colorid);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     meta.setLore(lore);
     item.setItemMeta(meta);
     return item;
   }
   
   public static ItemStack createColorGlass(int colorid, String name) {
     ItemStack item = new ItemStack(Material.STAINED_GLASS, 1, (short)(byte)colorid);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     item.setItemMeta(meta);
     return item;
   }
 
   
   public static ItemStack createDye(int dye) {
     ItemStack item = new ItemStack(351, 1, (short)(byte)dye);
     return item;
   }
   
   public static ItemStack createColorGlassPane(int colorid, String name, ArrayList<String> lore) {
     ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)(byte)colorid);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     meta.setLore(lore);
     item.setItemMeta(meta);
     return item;
   }
   
   public static ItemStack createColorGlassPane(int colorid, String name) {
     ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)(byte)colorid);
     ItemMeta meta = item.getItemMeta();
     meta.setDisplayName(name);
     item.setItemMeta(meta);
     return item;
   }
   
   public static Inventory createInventory(String name, int rows) {
     return Bukkit.getServer().createInventory(null, rows * 9, name);
   }
   
   public static void setSpawn(Location loc) {
     plugin.getConfig().set("spawn.x", Integer.valueOf(loc.getBlockX()));
     plugin.getConfig().set("spawn.y", Integer.valueOf(loc.getBlockY()));
     plugin.getConfig().set("spawn.z", Integer.valueOf(loc.getBlockZ()));
     plugin.getConfig().set("spawn.yaw", Float.valueOf(loc.getYaw()));
     plugin.getConfig().set("spawn.pitch", Float.valueOf(loc.getPitch()));
     plugin.saveConfig();
   }
   
   public static Location getSpawn() {
     double x = plugin.getConfig().getInt("spawn.x");
     double y = plugin.getConfig().getInt("spawn.y");
     double z = plugin.getConfig().getInt("spawn.z");
     float yaw = plugin.getConfig().getInt("spawn.yaw");
     float pitch = plugin.getConfig().getInt("spawn.pitch");
     Location loc = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
     return loc;
   }
   
   public static void setWarp(Player player, String name, Location loc) {
     plugin.getConfig().set("warps." + name + ".x", Double.valueOf(loc.getX()));
     plugin.getConfig().set("warps." + name + ".y", Double.valueOf(loc.getY()));
     plugin.getConfig().set("warps." + name + ".z", Double.valueOf(loc.getZ()));
     plugin.getConfig().set("warps." + name + ".yaw", Float.valueOf(loc.getYaw()));
     plugin.getConfig().set("warps." + name + ".pitch", Float.valueOf(loc.getPitch()));
     plugin.getConfig().set("warps." + name + ".creator", player.getName());
     plugin.saveConfig();
   }
   
   public static Location getWarp(String name) {
     double x = plugin.getConfig().getInt("warps." + name + ".x");
     double y = plugin.getConfig().getInt("warps." + name + ".y");
     double z = plugin.getConfig().getInt("warps." + name + ".z");
     float yaw = plugin.getConfig().getInt("warps." + name + ".yaw");
     float pitch = plugin.getConfig().getInt("warps." + name + ".pitch");
     Location loc = new Location(Bukkit.getWorld("world"), x, y, z, yaw, pitch);
     return loc;
   }
   
   public static void removeWarp(String name) {
     plugin.getConfig().set("warps." + name, null);
     plugin.saveConfig();
   }
   
   public static void setCase(Location loc) {
     plugin.getConfig().set("case.x", Double.valueOf(loc.getX()));
     plugin.getConfig().set("case.y", Double.valueOf(loc.getY()));
     plugin.getConfig().set("case.z", Double.valueOf(loc.getZ()));
     plugin.saveConfig();
   }
   
   public static Location getCase() {
     double x = plugin.getConfig().getInt("case.x");
     double y = plugin.getConfig().getInt("case.y");
     double z = plugin.getConfig().getInt("case.z");
     Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
     return loc;
   }
   
   public static void setKeys(Player player, int amount) {
     MainClass.player.set("players." + player.getName() + ".keys", Integer.valueOf(amount));
     MainClass.savePlayer();
   }
   
   public static void addKeys(Player player, int amount) {
     MainClass.player.set("players." + player.getName() + ".keys", Integer.valueOf(getKeys(player) + amount));
     MainClass.savePlayer();
   }
   
   public static void removeKeys(Player player, int amount) {
     MainClass.player.set("players." + player.getName() + ".keys", Integer.valueOf(getKeys(player) - amount));
     MainClass.savePlayer();
   }
   
   public static int getKeys(Player player) {
     return MainClass.player.getInt("players." + player.getName() + ".keys");
   }
   
   public static void broadcast(String message) {
     for (Player online : Bukkit.getOnlinePlayers()) {
       online.sendMessage(message);
     }
   }
   
   public static ItemStack donate(int ID) {
     ItemStack vip = createColorGlass(5, "§a☾Вип☽");
     ItemStack premium = createColorGlass(9, "§3☾Премиум☽");
     ItemStack creative = createColorGlass(3, "§b☾Креатив☽");
     ItemStack elite = createColorGlass(4, "§e☾Элита☽");
     ItemStack legend = createColorGlass(10, "§5☾Легенда☽");
     ItemStack ultra = createColorGlass(14, "§c☾Ультра☽");
     ItemStack lord = createColorGlass(4, "§e☾Лорд☽");
     ItemStack operator = createColorGlass(1, "§6☾Оператор☽");
     ItemStack imperator = createColorGlass(2, "§d☾Император☽");
     switch (ID) {
       case 0: 
    	 return vip;
       
       case 1:
         return premium;
       
       case 2:
         return creative;
       
       case 3:
         return elite;
       
       case 4:
         return legend;
       
       case 5:
         return ultra;
       
       case 6:
         return lord;
       
       case 7:
         return operator;
       
       case 8:
         return imperator;
     } 
     
     return vip;
   }
 
 
   
   public static String donatename(int ID) {
     String vip = "§a☾Вип☽";
     String premium = "§3☾Премиум☽";
     String creative = "§b☾Креатив☽";
     String elite = "§e☾Элита☽";
     String legend = "§5☾Легенда☽";
     String ultra = "§c☾Ультра☽";
     String lord = "§e☾Лорд☽";
     String operator = "§6☾Оператор☽";
     String imperator = "§d☾Император☽";
     switch (ID) {
       case 0:
         return "§a☾Вип☽";
       
       case 1:
         return "§3☾Премиум☽";
       
       case 2:
         return "§b☾Креатив☽";
       
       case 3:
         return "§e☾Элита☽";
       
       case 4:
         return "§5☾Легенда☽";
       
       case 5:
         return "§c☾Ультра☽";
       
       case 6:
         return "§e☾Лорд☽";
       
       case 7:
         return "§6☾Оператор☽";
       
       case 8:
         return "§d☾Император☽";
     } 
     
     return "§a☾Вип☽";
   }
 
 
   
   public static String groupname(int ID) {
     String vip = "Vip";
     String premium = "premium";
     String creative = "creative";
     String elite = "elite";
     String legend = "legend";
     String ultra = "ultra";
     String lord = "lord";
     String operator = "operator";
     String imperator = "imperator";
     switch (ID) {
       case 0:
         return "Vip";
       
       case 1:
         return "premium";
       
       case 2:
         return "creative";
       
       case 3:
         return "elite";
       
       case 4:
         return "legend";
       
       case 5:
         return "ultra";
       
       case 6:
         return "lord";
       
       case 7:
         return "operator";
       
       case 8:
         return "imperator";
     } 
     
     return "Vip";
   }
 
 
   
   public static String prize(int ID) {
     switch (ID) {
       case 0:
         return "§7Стейк (32 шт.)";
       
       case 1:
         return "§bЖелезо (16 шт.)";
       
       case 2:
         return "§bАлмазы (16 шт.)";
       
       case 3:
         return "§bЗачарованные яблоки (8 шт.)";
       
       case 4:
         return "§eКит старт";
       
       case 5:
         return "§e1000$";
       
       case 6:
         return "§65000$";
       
       case 7:
         return "§610000$";
       
       case 8:
         return "§cПолет";
       
       case 9:
         return "§dКлюч от сундука с привилегиями §7(1 шт.)";
     } 
     
     return "null";
   }
 
 
 
   
   public static void checkGroup(Player player, int i, int compensation) {
     PermissionUser user = PermissionsEx.getUser(player);
     if (user.inGroup(groupname(i))) {
       player.sendMessage("§aСундук удачи §8| §fВаша привилегия лучше выбитой. Вам начислено §e" + compensation + "$");
       try {
         Economy.add(player.getName(), compensation);
       }
       catch (NoLoanPermittedException e) {
         e.printStackTrace();
       }
       catch (UserDoesNotExistException e2) {
         e2.printStackTrace();
       } 
     } else {
       
       user.addGroup(groupname(i));
     } 
   }
   
   public static Inventory getInventory(Player player) {
     return (Inventory)player.getInventory();
   }
   
   public static void addMineBlock(Location loc) {
     plugin.getConfig().getStringList("mine-blocks").add(String.valueOf(String.valueOf(loc.getX())) + "," + loc.getY() + "," + loc.getZ());
     plugin.saveConfig();
   }
   
   public static Material randomMaterial(int i) {
     switch (i) {
       case 0:
         return Material.COAL_ORE;
       
       case 1:
         return Material.IRON_ORE;
       
       case 2:
         return Material.DIAMOND_ORE;
       
       case 3:
         return Material.GOLD_ORE;
       
       case 4:
         return Material.LAPIS_ORE;
       
       case 5:
         return Material.REDSTONE_ORE;
     } 
     
     return Material.COAL_ORE;
   }
 
 
   
   public static Location getBlock(double d, double e, double z) {
     Location loc = new Location(Bukkit.getWorld("world"), d, e, z);
     return loc;
   }
   
   public static Location whereBlock(Block block) {
     Location loc = new Location(Bukkit.getWorld("world"), block.getX(), block.getY(), block.getZ());
     return loc;
   }
   
   public static int moneyInBlock(Material block) {
     switch (block) {
       case COAL_ORE:
         return 15;
       
       case DIAMOND_ORE:
         return 75;
       
       case GOLD_ORE:
         return 45;
       
       case IRON_ORE:
         return 35;
       
       case LAPIS_ORE:
         return 30;
     }   
     return 0;
   }
 
 
   
   public static void setDaylyTime(Player player, int time) {
     MainClass.player.set("players." + player.getName() + ".daylychesttime", Integer.valueOf(time));
     MainClass.savePlayer();
   }
   
   public static void removeDaylyTime(Player player) {
     MainClass.player.set("players." + player.getName() + ".daylychesttime", Integer.valueOf(getDaylyTime(player) - 1));
     MainClass.savePlayer();
   }
   
   public static int getDaylyTime(Player player) {
     return MainClass.player.getInt("players." + player.getName() + ".daylychesttime");
   }
   
   public static void addItem(Player player, ItemStack item) {
     player.getInventory().addItem(new ItemStack[] { item });
   }
   
   public static void daylyCountdown(final Player player) {
     (new BukkitRunnable() {
         public void run() {
           if (!MainClass.dayly.containsKey(player)) {
             cancel();
           }
           if (MainClass.dayly.containsKey(player)) {
             if (((Integer)MainClass.dayly.get(player)).intValue() > 0) {
               MainClass.dayly.put(player, Integer.valueOf(((Integer)MainClass.dayly.get(player)).intValue() - 1));
               KingCoreAPI.removeDaylyTime(player);
             } 
             if (((Integer)MainClass.dayly.get(player)).intValue() == 0) {
               MainClass.dayly.remove(player);
               cancel();
             } 
           } 
         }
       }).runTaskTimer((Plugin)plugin, 0L, 20L);
   }
   
   public static void setMale(Player player) {
     MainClass.player.set("players." + player.getName() + ".gender", "male");
   }
   
   public static void setFemale(Player player) {
     MainClass.player.set("players." + player.getName() + ".gender", "female");
   }
   
   public static void removeGender(Player player) {
     MainClass.player.set("players." + player.getName() + ".gender", null);
   }
   
   public static String getGender(Player player) {
     return MainClass.player.getString("players." + player.getName() + ".gender");
   }
   
   public static void addPlayedtimes(final Player player) {
     if (!plugin.played.containsKey(player)) {
       Bukkit.getScheduler().cancelTasks((Plugin)plugin);
     }
     if (plugin.played.containsKey(player)) {
       Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)plugin, new Runnable()
           {
             public void run() {
               KingCoreAPI.addPlayedTimes(player, 1);
             }
           }, 1, 20);
     }
   }
   
   public static void setMarry(Player player, Player target) {
     MainClass.player.set("players." + player.getName() + ".marry", target.getName());
     MainClass.savePlayer();
   }
   
   public static String getMarry(Player player) {
     return MainClass.player.getString("players." + player.getName() + ".marry");
   }
 }