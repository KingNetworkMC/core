package ru.alexeylesin.kingcore.freedon;

import ru.alexeylesin.kingcore.MainClass;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DonateInPlaceSetPoint implements CommandExecutor {
  private MainClass plugin = (MainClass)MainClass.getPlugin(MainClass.class);
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
      sender.sendMessage("§cКоманду использовать можно только в игре!");
      return true;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (!player.isOp()) {
        player.sendMessage("§aKingCore §8| §fКоманду могут использовать только §cАдминистраторы§f сервера");
        return true;
      } 
      if (args.length == 0) {
        player.sendMessage("§aDonate in Place by §eKingCoreAPI");
        player.sendMessage("§7Установить точку - §a/dip point");
        return true;
      } 
      if (args[0].equalsIgnoreCase("point") && 
        args.length == 1) {
        if (!this.plugin.mode.containsKey(player)) {
          this.plugin.mode.put(player, Integer.valueOf(1));
          player.sendMessage("§aDonate in Place §8| §fВы вошли в режим установки точки. Вам необходимо взять блок, и установить его там, где должна быть точка. P.S Вы можете строить разными блоками. Чтобы выйти из режима, используйте команду еще раз. §a(/dip point)");
          return true;
        } 
        if (this.plugin.mode.containsKey(player)) {
          this.plugin.mode.remove(player);
          player.sendMessage("§aDonate in Place §8| §cВы вышли из режима установки точки.");
          return true;
        } 
      } 
    } 
    return true;
  }
}
