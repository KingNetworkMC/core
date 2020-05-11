package ru.alexeylesin.kingcore.gui;

import java.util.ArrayList;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Gamemode implements Listener {
  private ArrayList<String> survival(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§a  §7Выбрать режим §6Выживания");
    lore.add("");
    lore.add("§7Наш сайт: §6kingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> creative(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§a▸ §7Выбрать §cТворческий §7режим");
    lore.add("");
    lore.add("§7Наш сайт: §6kingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> adventure(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§a▸ §7Выбрать режим §aПриключения");
    lore.add("");
    lore.add("§7Наш сайт: §6kingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> spectator(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§a▸ §7Выбрать режим §bНаблюдение");
    lore.add("");
    lore.add("§7Наш сайт: §6kingnetwork.ru");
    return lore;
  }
  
  private ArrayList<String> fly(Player player) {
    ArrayList<String> lore = new ArrayList<>();
    if (!player.getAllowFlight()) {
      lore.add("");
      lore.add("§a▸ §aВключить §7флай");
      lore.add("");
      lore.add("§7Наш сайт: §6kingnetwork.ru");
    } 
    if (player.getAllowFlight()) {
      lore.add("");
      lore.add("§a▸ §cОтключить §7флай");
      lore.add("");
      lore.add("§7Наш сайт: §6kingnetwork.ru");
    } 
    return lore;
  }
  
  public void open(Player player) {
    Inventory inv = KingCoreAPI.createInventory("Выбор игрового режима", 5);
    ItemStack survival = KingCoreAPI.createColorGlass(1, "§6Выживание", survival(player));
    ItemStack creative = KingCoreAPI.createColorGlass(14, "§cТворческий", creative(player));
    ItemStack adventure = KingCoreAPI.createColorGlass(5, "§aПриключение", adventure(player));
    ItemStack spectator = KingCoreAPI.createColorGlass(3, "§bНаблюдение", spectator(player));
    ItemStack fly = KingCoreAPI.createItem(Material.FEATHER, "§7Флай", fly(player));
    inv.setItem(11, survival);
    inv.setItem(13, creative);
    inv.setItem(15, adventure);
    inv.setItem(21, spectator);
    inv.setItem(23, fly);
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player player = (Player)e.getWhoClicked();
    if (e.getInventory().getName().equalsIgnoreCase("Выбор игрового режима")) {
      e.setCancelled(true);
      if (e.getRawSlot() == 11) {
        player.setGameMode(GameMode.SURVIVAL);
        player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §6Выживание");
        player.closeInventory();
      } 
      if (e.getRawSlot() == 13) {
        player.setGameMode(GameMode.CREATIVE);
        player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §cТворческий");
        player.closeInventory();
      } 
      if (e.getRawSlot() == 15) {
        player.setGameMode(GameMode.ADVENTURE);
        player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §aПриключение");
        player.closeInventory();
      } 
      if (e.getRawSlot() == 21) {
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §bНаблюдение");
        player.closeInventory();
      } 
      if (e.getRawSlot() == 23) {
        if (player.getAllowFlight()) {
          player.sendMessage("§aKingNetwork §8| §fПолет §cОтключен§f.");
          player.setAllowFlight(false);
          player.closeInventory();
          return;
        } 
        if (!player.getAllowFlight()) {
          player.sendMessage("§aKingNetwork §8| §fПолет §aВключен§f.");
          player.setAllowFlight(true);
          player.closeInventory();
          return;
        } 
      } 
    } 
  }
}
