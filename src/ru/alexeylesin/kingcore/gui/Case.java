package ru.alexeylesin.kingcore.gui;

import java.util.ArrayList;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class Case implements Listener {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  @EventHandler
  public void checkOnline(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    if (KingCoreAPI.getDaylyTime(player) > 0) {
      MainClass.dayly.put(player, Integer.valueOf(KingCoreAPI.getDaylyTime(player)));
      KingCoreAPI.daylyCountdown(player);
    } else {
      return;
    } 
  }
  
  @EventHandler
  public void onQ(PlayerQuitEvent e) {
    Player player = e.getPlayer();
    if (KingCoreAPI.getDaylyTime(player) > 0) {
      MainClass.dayly.remove(player);
      KingCoreAPI.daylyCountdown(player);
    } else {
      return;
    } 
  }
  
  private ArrayList<String> key(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§7Кол-во ваших ключей: §f" + KingCoreAPI.getKeys(player));
    lore.add("");
    lore.add("§eОткрыв сундук вы можете получить:");
    lore.add("");
    lore.add("§a☾Вип☽ §7- §8§m10§e 5 §7рублей");
    lore.add("§3☾Премиум☽ §7- §8§m20§e 10 §7рублей");
    lore.add("§b☾Креатив☽ §7- §8§m50§e 25 §7рублей");
    lore.add("§e☾Элита☽ §7- §8§m100§e 50 §7рублей");
    lore.add("§5☾Легенда☽ §7- §8§m160§e 80 §7рублей");
    lore.add("§c☾Ультра☽ §7- §8§m220§e 110 §7рублей");
    lore.add("§e☾Лорд☽ §7- §8§m300§e 150 §7рублей");
    lore.add("§6☾Оператор☽ §7- §8§m400§e 200 §7рублей");
    lore.add("§d☾Император☽ §7- §8§m900§e 450 §7рублей");
    lore.add("");
    lore.add("§e[!] §7Если вы получите привилегию ниже вашей,");
    lore.add("§e[!] §7или точно какую же как у вас, вы получите денежный приз.");
    lore.add("");
    lore.add("§7Купить ключи можно на нашем сайте: §6kingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> daylykey(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§aВы можете открыть сундук прямо сейчас!");
    lore.add("");
    lore.add("§eОткрыв сундук вы можете получить:");
    lore.add("");
    lore.add("§7Стейк (32 шт.)");
    lore.add("§bЖелезо (16 шт.)");
    lore.add("§bАлмазы (16 шт.)");
    lore.add("§bЗачарованное яблоки (8 шт.)");
    lore.add("§eКит старт");
    lore.add("§e1000$");
    lore.add("§65000$");
    lore.add("§610000$");
    lore.add("§cПолет");
    lore.add("§dКлюч от сундука с привилегиями §7(1 шт.)");
    lore.add("");
    lore.add("§7Получать ключ можно бесплатно играя на сервере.");
    lore.add("§cВажно! §7Время будет истекать только когда вы онлайн.");
    return lore;
  }
  
  private ArrayList<String> countdaylykey(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§7Открыть сундук снова вы сможете через: §e" + KingCoreAPI.convertSeconds(((Integer)MainClass.dayly.get(player)).intValue()));
    lore.add("");
    lore.add("§eОткрыв сундук вы можете получить:");
    lore.add("");
    lore.add("§7Стейк (32 шт.)");
    lore.add("§bЖелезо (16 шт.)");
    lore.add("§bАлмазы (16 шт.)");
    lore.add("§bЗачарованное золотые яблоки (3 шт.)");
    lore.add("§eКит старт");
    lore.add("§e1000$");
    lore.add("§65000$");
    lore.add("§610000$");
    lore.add("§cПолет");
    lore.add("§dКлюч от сундука с привилегиями §7(1 шт.)");
    lore.add("");
    lore.add("§7Получать ключ можно бесплатно играя на сервере.");
    lore.add("§cВажно! §7Время будет истекать только когда вы онлайн.");
    return lore;
  }
  
  public void open(final Player player) {
    final Inventory inv = KingCoreAPI.createInventory("Сундук удачи", 3);
    ItemStack key = KingCoreAPI.createItem(Material.TRIPWIRE_HOOK, "§aОткрыть сундук с привилегиями", key(player));
    Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)this.plugin, new Runnable() {
          public void run() {
            if (KingCoreAPI.getDaylyTime(player) == 0) {
              ItemStack daylyley = KingCoreAPI.createItem(Material.TRIPWIRE_HOOK, "§aОткрыть игровой сундук", Case.this.daylykey(player));
              inv.setItem(11, daylyley);
              return;
            } 
            if (KingCoreAPI.getDaylyTime(player) > 0) {
              ItemStack daylyley = KingCoreAPI.createItem(Material.TRIPWIRE_HOOK, "§aОткрыть игровой сундук", Case.this.countdaylykey(player));
              inv.setItem(11, daylyley);
            } 
          }
        }, 1L, 1L);
    inv.setItem(15, key);
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player player = (Player)e.getWhoClicked();
    if (e.getInventory().getName().equalsIgnoreCase("Сундук удачи | Прокрутка"))
      e.setCancelled(true); 
    if (e.getInventory().getName().equalsIgnoreCase("Сундук удачи | Ваш приз")) {
      e.setCancelled(true);
      if (e.getRawSlot() == 15)
        player.closeInventory(); 
    } 
    if (e.getInventory().getName().equalsIgnoreCase("Сундук удачи")) {
      e.setCancelled(true);
      if (e.getRawSlot() == 15) {
        Block chest = Bukkit.getWorld("world").getBlockAt(KingCoreAPI.getCase());
        if (chest.hasMetadata("open")) {
          player.closeInventory();
          player.sendMessage("§aСундук удачи §8| §fСундук сейчас кто-то открывает, дождитесь пока его откроют!");
          return;
        } 
        if (KingCoreAPI.getKeys(player) == 0) {
          player.sendMessage("§aСундук удачи §8| §fУ вас нет ключей от сундука с привилегиями! Вы можете купить ключи на сайте: §6kingnetwork.ru");
          player.closeInventory();
          return;
        } 
        player.closeInventory();
        KingCoreAPI.removeKeys(player, 1);
        chest.setMetadata("open", (MetadataValue)new FixedMetadataValue((Plugin)MainClass.getPlugin(MainClass.class), "open"));
      } 
      if (e.getRawSlot() == 11) {
        Block chest = Bukkit.getWorld("world").getBlockAt(KingCoreAPI.getCase());
        if (chest.hasMetadata("open")) {
          player.closeInventory();
          player.sendMessage("§aСундук удачи §8| §fСундук сейчас кто-то открывает, дождитесь пока его откроют!");
          return;
        } 
        if (KingCoreAPI.getDaylyTime(player) > 0) {
          player.sendMessage("§aСундук удачи §8| §fВы сможете открыть сундук снова через §e" + KingCoreAPI.convertSeconds(KingCoreAPI.getDaylyTime(player)));
          player.closeInventory();
          return;
        } 
        player.closeInventory();
        MainClass.dayly.put(player, Integer.valueOf(3600));
        KingCoreAPI.setDaylyTime(player, 3600);
        KingCoreAPI.daylyCountdown(player);
        chest.setMetadata("open", (MetadataValue)new FixedMetadataValue((Plugin)MainClass.getPlugin(MainClass.class), "open"));
      } 
    } 
  }
  
  @EventHandler
  public void onOpenCase(PlayerInteractEvent e) {
    Block chest = Bukkit.getWorld("world").getBlockAt(KingCoreAPI.getCase());
    Player player = e.getPlayer();
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
      if (e.getClickedBlock().getLocation().equals(KingCoreAPI.getCase())) {
        e.setCancelled(true);
        if (((MainClass)MainClass.getPlugin(MainClass.class)).opencase.containsKey(player)) {
          player.sendMessage("§aСундук удачи §8| §fВы уже открываете сундук удачи!");
          e.setCancelled(true);
          return;
        } 
      } else {
        return;
      }  
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && 
      e.getClickedBlock().getLocation().equals(KingCoreAPI.getCase()) && 
      chest.hasMetadata("open")) {
      player.sendMessage("§aСундук удачи §8| §fСундук сейчас кто-то открывает, дождитесь пока его откроют!");
      e.setCancelled(true);
      return;
    } 
    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) && 
      e.getClickedBlock().getLocation().equals(KingCoreAPI.getCase()) && 
      !chest.hasMetadata("open")) {
      e.setCancelled(true);
      if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        open(player); 
    } 
  }
}
