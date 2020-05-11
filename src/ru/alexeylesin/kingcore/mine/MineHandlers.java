package ru.alexeylesin.kingcore.mine;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import io.netty.util.internal.ThreadLocalRandom;
import ru.alexeylesin.kingcore.api.KingCoreAPI;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MineHandlers implements Listener {
  @EventHandler
  public void onBreak(BlockBreakEvent e) {
    Block block = e.getBlock();
    Player player = e.getPlayer();
    if (KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(293.0D, 59.0D, -51.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(293.0D, 59.0D, -51.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(299.0D, 59.0D, -52.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(300.0D, 60.0D, -51.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(292.0D, 60.0D, -60.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(293.0D, 59.0D, -60.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(290.0D, 59.0D, -59.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(300.0D, 59.0D, -59.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(300.0D, 59.0D, -59.0D)) || 
      KingCoreAPI.whereBlock(block).equals(KingCoreAPI.getBlock(295.0D, 62.0D, -59.0D))) {
      e.setCancelled(true);
      block.setType(Material.BEDROCK);
      int i = ThreadLocalRandom.current().nextInt(5);
      block.setType(KingCoreAPI.randomMaterial(i));
      try {
        Economy.add(e.getPlayer().getName(), KingCoreAPI.moneyInBlock(block.getType()));
      } catch (NoLoanPermittedException e1) {
        e1.printStackTrace();
      } catch (UserDoesNotExistException e1) {
        e1.printStackTrace();
      } 
      KingCoreAPI.sendActionBar(e.getPlayer(), "§7Вы заработали §6" + KingCoreAPI.moneyInBlock(block.getType()) + "$");
      if (block.getType().equals(Material.COAL_ORE))
        player.getInventory().addItem(new ItemStack[] { KingCoreAPI.createItem(Material.COAL) }); 
      if (block.getType().equals(Material.IRON_ORE))
        player.getInventory().addItem(new ItemStack[] { KingCoreAPI.createItem(Material.IRON_INGOT) }); 
      if (block.getType().equals(Material.GOLD_ORE))
        player.getInventory().addItem(new ItemStack[] { KingCoreAPI.createItem(Material.GOLD_INGOT) }); 
      if (block.getType().equals(Material.DIAMOND_ORE))
        player.getInventory().addItem(new ItemStack[] { KingCoreAPI.createItem(Material.DIAMOND) }); 
      if (block.getType().equals(Material.LAPIS_ORE))
        player.getInventory().addItem(new ItemStack[] { KingCoreAPI.createDye(4) }); 
    } 
  }
}
