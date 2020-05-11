package ru.alexeylesin.kingcore.gui;

import java.util.ArrayList;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Op implements Listener {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  private ArrayList<String> nosecret() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§c?????");
    lore.add("§c?????");
    lore.add("§c?????");
    lore.add("");
    lore.add("§c????????????");
    return lore;
  }
  
  private ArrayList<String> secret() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fИграть со статусом §b☾Креатив☽");
    return lore;
  }
  
  private ArrayList<String> noop() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fЧтобы получить опку, необходимо выполнить все задания!");
    return lore;
  }
  
  private ArrayList<String> op() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВы выполнили все задания! Получите опку!");
    return lore;
  }
  
  private ArrayList<String> nofirst(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    int i = 7200 - KingCoreAPI.getPlayedTimes(player);
    lore.add("");
    lore.add("§fВы должны поиграть на сервере не меньше §7двух §fчасов!");
    lore.add("§fЧтобы выполнить задание наиграйте еще §e" + KingCoreAPI.convertSeconds(i));
    return lore;
  }
  
  private ArrayList<String> nosecond(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВам необходимо приобрести или получить из кейса статус §b☾Креатив☽§f.");
    return lore;
  }
  
  private ArrayList<String> second(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    PermissionUser user = PermissionsEx.getUser(player);
    lore.add("");
    lore.add("§fУ вас есть статус §b☾Креатив☽§f.");
    lore.add("§fВаш статус: " + user.getPrefix().replace("&", "§"));
    return lore;
  }
  
  private ArrayList<String> first(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВы должны поиграть на сервере не меньше §7двух §fчасов!");
    lore.add("§fВы наиграли §e" + KingCoreAPI.convertSeconds(KingCoreAPI.getPlayedTimes(player)));
    return lore;
  }
  
  private ArrayList<String> how() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fЧтобы получить опку вам необходимо:");
    lore.add("");
    lore.add("§fВыполнить задание §e№1");
    lore.add("§fВыполнить задание §a№2");
    lore.add("§fВыполнить задание §b№3");
    lore.add("");
    lore.add("§e[!] §fПосле того, как выполните все задания, нажмите на §aПолучить опку§f");
    return lore;
  }
  
  public void open(final Player player) {
    final Inventory inv = KingCoreAPI.createInventory("Получить опку", 6);
    ItemStack a = KingCoreAPI.createColorGlassPane(15, "§eplay.kingnetwork.ru");
    ItemStack help = KingCoreAPI.createItem(Material.BOOK_AND_QUILL, "§eКак получить опку?", how());
    final int i = 7200 - KingCoreAPI.getPlayedTimes(player);
    final PermissionUser user = PermissionsEx.getUser(player);
    Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
          public void run() {
            if (i > 0) {
              ItemStack first = KingCoreAPI.createColorGlass(14, "§cЗадание не выполнено.", Op.this.nofirst(player));
              inv.setItem(29, first);
            } 
            if (i <= 0) {
              ItemStack first = KingCoreAPI.createColorGlass(5, "§aЗадание выполнено!", Op.this.first(player));
              inv.setItem(29, first);
            } 
            if (!user.inGroup("Creative")) {
              ItemStack second = KingCoreAPI.createColorGlass(14, "§cЗадание не выполнено.", Op.this.nosecond(player));
              inv.setItem(31, second);
              ItemStack secret = KingCoreAPI.createColorGlass(14, "§fСекретное задание для §b☾Креатив☽ §cНе выполнено.", Op.this.nosecret());
              inv.setItem(33, secret);
            } 
            if (user.inGroup("Creative")) {
              ItemStack second = KingCoreAPI.createColorGlass(5, "§aЗадание выполнено!", Op.this.second(player));
              inv.setItem(31, second);
              ItemStack secret = KingCoreAPI.createColorGlass(5, "§aСекретное задание выполнено!", Op.this.secret());
              inv.setItem(33, secret);
            } 
            if (!user.inGroup("Creative") || KingCoreAPI.getPlayedTimes(player) < 7200 || (!user.inGroup("Creative") && KingCoreAPI.getPlayedTimes(player) < 7200)) {
              ItemStack hack = KingCoreAPI.createItem(Material.COMMAND_MINECART, "§cПолучить опку", Op.this.noop());
              inv.setItem(49, hack);
            } else {
              ItemStack hack = KingCoreAPI.createItem(Material.COMMAND_MINECART, "§aПолучить опку", Op.this.op());
              inv.setItem(49, hack);
            } 
          }
        }, 1L, 1L);
    inv.setItem(13, help);
    inv.setItem(45, a);
    inv.setItem(46, a);
    inv.setItem(47, a);
    inv.setItem(48, a);
    inv.setItem(50, a);
    inv.setItem(51, a);
    inv.setItem(52, a);
    inv.setItem(53, a);
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().equalsIgnoreCase("Получить опку")) {
      e.setCancelled(true);
      if (e.getRawSlot() == 49) {
        Player player = (Player)e.getWhoClicked();
        PermissionUser user = PermissionsEx.getUser(player);
        if (!user.inGroup("Creative") || KingCoreAPI.getPlayedTimes(player) < 7200 || (!user.inGroup("Creative") && KingCoreAPI.getPlayedTimes(player) < 7200)) {
          player.closeInventory();
          player.sendMessage("§aKingNetwork §8| §fВы не выполнили все задания!");
        } else {
          player.closeInventory();
          for (Player online : Bukkit.getOnlinePlayers())
            online.sendMessage("§7[Server: Opped: " + player.getName() + "]"); 
        } 
      } 
    } 
  }
}
