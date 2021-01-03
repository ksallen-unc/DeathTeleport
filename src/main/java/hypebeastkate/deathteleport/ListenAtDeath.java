package hypebeastkate.deathteleport;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class ListenAtDeath implements Listener, CommandExecutor {
  // class varialbes
  private Main plugin;

  // constructor
  public ListenAtDeath(Main instance) {
    plugin = instance;
  }

  @EventHandler
  public void restrictBlockBreaks(PlayerDeathEvent event) {
    Player player = event.getEntity();
    Location deathLoc = player.getLocation(); // gets location at time of death
    // stores world died at/in
    plugin.getConfig().set("deathPts." + player.getName() + ".world", deathLoc.getWorld().getName());
    // stores x, y, z coordinates player's death was at by setting config
    plugin.getConfig().set("deathPts." + player.getName() + ".x", deathLoc.getX());
    plugin.getConfig().set("deathPts." + player.getName() + ".y", deathLoc.getY());
    plugin.getConfig().set("deathPts." + player.getName() + ".z", deathLoc.getZ());
    // saves changes made to config
    plugin.saveConfig();
  }

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    Player player;
    if (sender.isOp() && command.getName().equalsIgnoreCase("teleporttodeath")) {
      // SETS UP PLUGIN
      player = (Player) command;
      player.sendMessage(ChatColor.BLUE + "[TeleportToDeath] " + ChatColor.WHITE + "Configuring plugin...");
      plugin.reloadConfig(); // make sure plugin is configured properly
      if (command instanceof Player) { // if a player actually sent the cmd
        player.sendMessage(ChatColor.BLUE + "[TeleportToDeath] " + ChatColor.WHITE + "plugin config reloaded");
      }
      plugin.getLogger().info(ChatColor.BLUE + "[DeathBack] " + ChatColor.WHITE + "plugin config reloaded");
    } else if (command.getName().equalsIgnoreCase("dback") && (sender instanceof Player)) {
      // GOES TO DEATH LOCATION
      player = (Player) sender; // bc checked to make sure sender was Player before this
      String worldStr = plugin.getConfig().getString("deathPts." + player.getName() + ".world");
      World wrld = plugin.getServer().getWorld(worldStr); // returns world w that String/worldStr
      double x = plugin.getConfig().getDouble("deathPts." + player.getName() + ".x");
      double y = plugin.getConfig().getDouble("deathPts." + player.getName() + ".y");
      double z = plugin.getConfig().getDouble("deathPts." + player.getName() + ".z");
      Location deathLoc = new Location(wrld, x, y, z);
      player.teleport(deathLoc); // transports player to the loc of their death
      player.sendMessage(ChatColor.BLUE + "[TeleportToDeath] " + ChatColor.WHITE + "teleported");
    }
    return true;
  }
}
