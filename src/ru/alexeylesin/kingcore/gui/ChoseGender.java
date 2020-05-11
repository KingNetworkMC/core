package ru.alexeylesin.kingcore.gui;

import java.util.ArrayList;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ChoseGender implements Listener {
  private ArrayList<String> male() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВыбрать мужской пол");
    return lore;
  }
  
  private ArrayList<String> female() {
    ArrayList<String> lore = new ArrayList<>();
    lore.add("");
    lore.add("§fВыбрать женский пол");
    return lore;
  }
  
  public void open(Player player) {
    Inventory inv = KingCoreAPI.createInventory("Выбор пола", 3);
    ItemStack male = KingCoreAPI.createColorGlass(3, "§bМужской", male());
    ItemStack female = KingCoreAPI.createColorGlass(2, "§dЖенский", female());
    inv.setItem(11, male);
    if (MainClass.player.getString("players." + player.getName() + ".gender") == null)
      inv.setItem(13, KingCoreAPI.createItem(Material.AIR)); 
    if (MainClass.player.getString("players." + player.getName() + ".gender") != null)
      inv.setItem(13, KingCoreAPI.createItem(Material.BARRIER, "§cУдалить пол")); 
    inv.setItem(15, female);
    player.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    Player player = (Player)e.getWhoClicked();
    if (e.getInventory().getName().equalsIgnoreCase("Выбор пола")) {
      e.setCancelled(true);
      if (e.getRawSlot() == 11) {
        e.setCancelled(true);
        KingCoreAPI.setMale(player);
        player.closeInventory();
        player.sendMessage("§aKingNetwork §8| §fВы выбрали §bМужской §fпол.");
      } 
      if (e.getRawSlot() == 15) {
        e.setCancelled(true);
        KingCoreAPI.setFemale(player);
        player.closeInventory();
        player.sendMessage("§aKingNetwork §8| §fВы выбрали §dЖенский §fпол.");
      } 
      if (e.getRawSlot() == 13) {
        e.setCancelled(true);
        KingCoreAPI.removeGender(player);
        player.closeInventory();
        player.sendMessage("§aKingNetwork §8| §fВы удалили пол.");
      } 
    } 
  }
}
