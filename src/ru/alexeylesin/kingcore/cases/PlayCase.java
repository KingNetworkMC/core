package ru.alexeylesin.kingcore.cases;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import io.netty.util.internal.ThreadLocalRandom;
import java.util.HashMap;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayCase {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  private HashMap<Player, Integer> s = new HashMap<>();
  
  public static void changeChestState(Location loc, boolean open) {
    byte dataByte = (byte) (open ? 1 : 0);
    for (Player player : Bukkit.getOnlinePlayers()) {
      BlockPosition position = new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
      PacketPlayOutBlockAction blockActionPacket = new PacketPlayOutBlockAction(position, Block.getById(loc.getBlock().getTypeId()), 1, dataByte);
      (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)blockActionPacket);
    } 
  }
  
  public void animatedInv(final Player player) {
    final int i = ThreadLocalRandom.current().nextInt(8);
    final Inventory inv = KingCoreAPI.createInventory("Сундук удачи | Прокрутка", 3);
    ItemStack stake = KingCoreAPI.createItem(Material.COOKED_BEEF, "§7Стейк");
    stake.setAmount(32);
    ItemStack iron = KingCoreAPI.createItem(Material.IRON_INGOT, "§bЖелезо");
    iron.setAmount(16);
    ItemStack diamond = KingCoreAPI.createItem(Material.DIAMOND, "§bАлмаз");
    diamond.setAmount(16);
    ItemStack gapple = KingCoreAPI.createItem(Material.GOLDEN_APPLE, "§bЗолотое яблоко");
    gapple.setAmount(8);
    ItemStack kitstart = KingCoreAPI.createItem(Material.IRON_SWORD, "§eКит старт");
    ItemStack money = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§e1000$");
    ItemStack money2 = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§65000$");
    ItemStack money3 = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§610000$");
    ItemStack fly = KingCoreAPI.createItem(Material.FEATHER, "§cПолет");
    ItemStack key = KingCoreAPI.createItem(Material.TRIPWIRE_HOOK, "§dКлюч от сундука с привилегиями");
    final ItemStack[] prize = { stake, iron, diamond, kitstart, money, money2, money3, fly, key };
    player.openInventory(inv);
    (new BukkitRunnable() {
        public void run() {
          inv.setItem(13, prize[i]);
          player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
          cancel();
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 20L);
  }
  
  public PlayCase(final Player player) {
    final int i = ThreadLocalRandom.current().nextInt(9);
    final ItemStack stake = KingCoreAPI.createItem(Material.COOKED_BEEF, "§7Стейк");
    stake.setAmount(32);
    final ItemStack iron = KingCoreAPI.createItem(Material.IRON_INGOT, "§bЖелезо");
    iron.setAmount(16);
    final ItemStack diamond = KingCoreAPI.createItem(Material.DIAMOND, "§bАлмаз");
    diamond.setAmount(16);
    final ItemStack gapple = KingCoreAPI.createItem(Material.GOLDEN_APPLE, "§bЗолотое яблоко");
    gapple.setAmount(8);
    ItemStack kitstart = KingCoreAPI.createItem(Material.IRON_SWORD, "§eКит старт");
    ItemStack money = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§e1000$");
    ItemStack money2 = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§65000$");
    ItemStack money3 = KingCoreAPI.createItem(Material.DOUBLE_PLANT, "§610000$");
    ItemStack fly = KingCoreAPI.createItem(Material.FEATHER, "§cПолет");
    ItemStack key = KingCoreAPI.createItem(Material.TRIPWIRE_HOOK, "§dКлюч от сундука с привилегиями");
    final ItemStack[] prize = { stake, iron, diamond, gapple, kitstart, money, money2, money3, fly, key };
    this.plugin.opencase.put(player, Integer.valueOf(12));
    (new BukkitRunnable() {
        public void run() {
          if (!PlayCase.this.plugin.opencase.containsKey(player))
            cancel(); 
          if (PlayCase.this.plugin.opencase.containsKey(player) && (
            (Integer)PlayCase.this.plugin.opencase.get(player)).intValue() > 0) {
            PlayCase.this.plugin.opencase.put(player, Integer.valueOf(((Integer)PlayCase.this.plugin.opencase.get(player)).intValue() - 1));
            PlayCase.this.animatedInv(player);
            PlayCase.changeChestState(KingCoreAPI.getCase(), true);
            if (((Integer)PlayCase.this.plugin.opencase.get(player)).intValue() == 1) {
              Inventory inv = KingCoreAPI.createInventory("Сундук удачи | Ваш приз", 3);
              player.openInventory(inv);
              inv.setItem(13, prize[i]);
              player.openInventory(inv);
              PlayCase.this.s.put(player, Integer.valueOf(i));
              Block chest = (Block) Bukkit.getWorld("world").getBlockAt(KingCoreAPI.getCase());
              ((Metadatable) chest).removeMetadata("open", (Plugin)PlayCase.this.plugin);
            } 
            if (((Integer)PlayCase.this.plugin.opencase.get(player)).intValue() == 0) {
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 0)
                KingCoreAPI.addItem(player, stake); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 1)
                KingCoreAPI.addItem(player, iron); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 2)
                KingCoreAPI.addItem(player, diamond); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 3)
                KingCoreAPI.addItem(player, gapple); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 4)
                Bukkit.dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "kit start " + player.getName()); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 5)
                try {
                  Economy.add(player.getName(), 1000.0D);
                } catch (NoLoanPermittedException e) {
                  e.printStackTrace();
                } catch (UserDoesNotExistException e) {
                  e.printStackTrace();
                }  
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 6)
                try {
                  Economy.add(player.getName(), 5000.0D);
                } catch (NoLoanPermittedException e) {
                  e.printStackTrace();
                } catch (UserDoesNotExistException e) {
                  e.printStackTrace();
                }  
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 7)
                try {
                  Economy.add(player.getName(), 10000.0D);
                } catch (NoLoanPermittedException e) {
                  e.printStackTrace();
                } catch (UserDoesNotExistException e) {
                  e.printStackTrace();
                }  
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 8)
                player.setAllowFlight(true); 
              if (((Integer)PlayCase.this.s.get(player)).intValue() == 9)
                KingCoreAPI.addKeys(player, 1); 
              player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
              player.closeInventory();
              KingCoreAPI.broadcast("§aСундук удачи §8| §fИгрок §e" + player.getName() + "§f выбил " + KingCoreAPI.prize(((Integer)PlayCase.this.s.get(player)).intValue()) + "§f из игрового сундука!");
              PlayCase.changeChestState(KingCoreAPI.getCase(), false);
              PlayCase.this.plugin.opencase.remove(player);
              PlayCase.this.s.remove(player);
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 10L);
  }
}
