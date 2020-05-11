package ru.alexeylesin.kingcore.cases;

import io.netty.util.internal.ThreadLocalRandom;
import ru.alexeylesin.kingcore.MainClass;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockAction;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Cases {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
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
    ItemStack vip = KingCoreAPI.createColorGlass(5, "§a☾Вип☽");
    ItemStack premium = KingCoreAPI.createColorGlass(9, "§3☾Премиум☽");
    ItemStack creative = KingCoreAPI.createColorGlass(3, "§b☾Креатив☽");
    ItemStack elite = KingCoreAPI.createColorGlass(4, "§e☾Элита☽");
    ItemStack legend = KingCoreAPI.createColorGlass(10, "§5☾Легенда☽");
    ItemStack ultra = KingCoreAPI.createColorGlass(14, "§c☾Ультра☽");
    ItemStack lord = KingCoreAPI.createColorGlass(4, "§e☾Лорд☽");
    ItemStack operator = KingCoreAPI.createColorGlass(1, "§6☾Оператор☽");
    ItemStack imperator = KingCoreAPI.createColorGlass(2, "§d☾Император☽");
    final ItemStack[] donate = { vip, premium, creative, elite, legend, ultra, lord, operator, imperator };
    player.openInventory(inv);
    (new BukkitRunnable() {
        public void run() {
          inv.setItem(13, donate[i]);
          player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);
          cancel();
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 20L);
  }
  
  public Cases(final Player player) {
    final int i = ThreadLocalRandom.current().nextInt(8);
    this.plugin.opencase.put(player, Integer.valueOf(12));
    (new BukkitRunnable() {
        public void run() {
          if (!Cases.this.plugin.opencase.containsKey(player))
            cancel(); 
          if (Cases.this.plugin.opencase.containsKey(player) && (
            (Integer)Cases.this.plugin.opencase.get(player)).intValue() > 0) {
            Cases.this.plugin.opencase.put(player, Integer.valueOf(((Integer)Cases.this.plugin.opencase.get(player)).intValue() - 1));
            Cases.this.animatedInv(player);
            Cases.changeChestState(KingCoreAPI.getCase(), true);
            if (((Integer)Cases.this.plugin.opencase.get(player)).intValue() == 1) {
              Inventory inv = KingCoreAPI.createInventory("Сундук удачи | Ваш приз", 3);
              inv.setItem(13, KingCoreAPI.donate(i));
              player.openInventory(inv);
              Block chest = (net.minecraft.server.v1_12_R1.Block) Bukkit.getWorld("world").getBlockAt(KingCoreAPI.getCase());
              ((Metadatable) chest).removeMetadata("open", (Plugin)Cases.this.plugin);
            } 
            if (((Integer)Cases.this.plugin.opencase.get(player)).intValue() == 0) {
              player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
              player.closeInventory();
              KingCoreAPI.broadcast("§aСундук удачи §8| §fИгрок §e" + player.getName() + "§f выбил " + KingCoreAPI.donatename(i) + "§f из сундука!");
              KingCoreAPI.broadcast("§aСундук удачи §8| §fХочешь так же? Покупай сундук с привилегиями на нашем сайте: §6kingnetwork.ru");
              Cases.changeChestState(KingCoreAPI.getCase(), false);
              KingCoreAPI.checkGroup(player, i, 2500);
              Cases.this.plugin.opencase.remove(player);
              cancel();
            } 
          } 
        }
      }).runTaskTimer((Plugin)this.plugin, 0L, 10L);
  }
}
