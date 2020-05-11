package ru.alexeylesin.kingcore.gamecmds;

import ru.alexeylesin.kingcore.gui.Gamemode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GamemodeCommand implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("gamemode")) {
      if (sender instanceof org.bukkit.command.ConsoleCommandSender)
        return true; 
      if (sender instanceof Player) {
        if (!sender.hasPermission("kingnetwork.gamemode")) {
          sender.sendMessage("§aKingNetwork §8| §fУ вас нет прав! Команда доступна от §b[Креатив] §fи выше!");
          return true;
        } 
        Player player = (Player)sender;
        if (args.length == 0) {
          Gamemode gamemode = new Gamemode();
          gamemode.open(player);
          return true;
        } 
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("survival")) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §6Выживание");
            return true;
          } 
          if (args[0].equalsIgnoreCase("creative")) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §cТворческий");
            return true;
          } 
          if (args[0].equalsIgnoreCase("adventure")) {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §aПриключение");
            return true;
          } 
          if (args[0].equalsIgnoreCase("0")) {
            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §6Выживание");
            return true;
          } 
          if (args[0].equalsIgnoreCase("1")) {
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §cТворческий");
            return true;
          } 
          if (args[0].equalsIgnoreCase("2")) {
            player.setGameMode(GameMode.ADVENTURE);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §aПриключение");
            return true;
          } 
        } 
        if (args.length == 2) {
          Player target = Bukkit.getPlayer(args[1]);
          if (args[0].equalsIgnoreCase("survival")) {
            target.setGameMode(GameMode.SURVIVAL);
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим на: §6Выживание");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §6Выживание");
            return true;
          } 
          if (args[0].equalsIgnoreCase("creative")) {
            target.setGameMode(GameMode.CREATIVE);
            player.sendMessage("§aKingNetwork §8| §fВаш игровой режим был изменен на: §cТворческий");
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим §fна: §cТворческий");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §cТворческий");
            return true;
          } 
          if (args[0].equalsIgnoreCase("adventure")) {
            target.setGameMode(GameMode.ADVENTURE);
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим на: §aПриключение");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §aПриключение");
            return true;
          } 
          if (args[0].equalsIgnoreCase("0")) {
            target.setGameMode(GameMode.SURVIVAL);
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим на: §6Выживание");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §6Выживание");
            return true;
          } 
          if (args[0].equalsIgnoreCase("1")) {
            target.setGameMode(GameMode.CREATIVE);
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим §fна: §cТворческий");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §cТворческий");
            return true;
          } 
          if (args[0].equalsIgnoreCase("2")) {
            target.setGameMode(GameMode.ADVENTURE);
            target.sendMessage("§aKingNetwork §8| §fИгрок §e" + player.getName() + " §fизменил ваш игровой режим на: §aПриключение");
            player.sendMessage("§aKingNetwork §8| §fВы изменили игровой режим игроку §e" + target.getName() + " §fна: §aПриключение");
            return true;
          } 
        } 
      } 
    } 
    return true;
  }
}
