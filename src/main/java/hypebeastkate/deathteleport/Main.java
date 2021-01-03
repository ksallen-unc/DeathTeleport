package hypebeastkate.deathteleport;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public final class Main extends JavaPlugin {
  // CONSTRUCTOR. Nothing needs to be taken in OR set here, will set stuff when enabled -- so in onEnable
  public Main() {

  }

  // @Override
  public void onEnable() {
    // What we want to do when enabled:
    // 1) Start logging death events (methof of JavaPlugin class)
    this.getServer().getPluginManager().registerEvents(new ListenAtDeath(this), this);
    this.getConfig();  // 2) run getConfig from parent class (JavaPlugin)
    this.saveConfig(); // 3) Save the config that's been set up

    getCommand("dback").setExecutor(new ListenAtDeath(this));
    getCommand("teleporttodeath").setExecutor(new ListenAtDeath(this));

  }

  public void onDisable() {}
}
