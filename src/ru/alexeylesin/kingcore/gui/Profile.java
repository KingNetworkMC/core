package ru.alexeylesin.kingcore.gui;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.UserDoesNotExistException;
import java.util.ArrayList;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class Profile implements Listener {
  public MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  private String replaceGender(String gender) {
    String str;
    switch ((str = gender).hashCode()) {
      case -1278174388:
        if (!str.equals("female"))
          break; 
        return "§dЖенский";
      case 3343885:
        if (!str.equals("male"))
          break; 
        return "§bМужской";
    } 
    return "null";
  }
  
  @EventHandler
  public void onJ(PlayerJoinEvent e) {
    this.plugin.played.put(e.getPlayer(), Boolean.valueOf(true));
    KingCoreAPI.addPlayedtimes(e.getPlayer());
  }
  
  @EventHandler
  public void onQ(PlayerQuitEvent e) {
    this.plugin.played.remove(e.getPlayer());
    KingCoreAPI.addPlayedtimes(e.getPlayer());
  }
  
  private ArrayList<String> stats(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fНаиграно времени: §e" + KingCoreAPI.convertSeconds(KingCoreAPI.getPlayedTimes(player)));
    return lore;
  }
  
  private ArrayList<String> status(Player player) {
    PermissionUser user = PermissionsEx.getUser(player);
    String prefix = user.getGroups()[0].getPrefix();
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВаш статус: §a" + prefix.replace("&", "§"));
    lore.add("");
    lore.add("§fВы можете приобрести новый статус на нашем сайте: §ekingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> balance(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    try {
      double balance = Economy.getMoney(player.getName());
      lore.add("");
      lore.add("§fВаш баланс составляет: §e" + balance + "$");
      lore.add("");
      lore.add("§fИгровую валюту можно получать играя на сервере.");
      lore.add("§fТак же Вы можете приобрести игровую валюту: §akingnetwork.ru");
    } catch (UserDoesNotExistException e) {
      e.printStackTrace();
    } 
    return lore;
  }
  
  public void open(final Player player) {
    final Inventory inv = KingCoreAPI.createInventory("Профиль", 3);
    ItemStack status = KingCoreAPI.createItem(Material.GOLD_NUGGET, "§aИнформация о статусе", status(player));
    ItemStack balance = KingCoreAPI.createItem(Material.GOLD_INGOT, "§eИнформация о балансе", balance(player));
    Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
          public void run() {
            ItemStack stats = KingCoreAPI.createItem(Material.PAPER, "§bСтатистика", Profile.this.stats(player));
            inv.setItem(13, stats);
          }
        }, 1L, 1L);
    inv.setItem(11, status);
    inv.setItem(15, balance);
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().equalsIgnoreCase("Профиль"))
      e.setCancelled(true); 
  }
}
