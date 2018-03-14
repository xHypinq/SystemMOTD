package me.xhypinq.systmotd;
 
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.plugin.java.JavaPlugin;
 
public class main extends JavaPlugin implements Listener {
 
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent e) {
                Player p = e.getPlayer();
                String motd = getConfig().getString("motd.ingame");
                motd = motd.replaceAll("&", "§");
                p.sendMessage(motd);
        }
       
        @EventHandler
        public void onServerPing(ServerListPingEvent e) {
                String motd = getConfig().getString("motd.system");
                motd = motd.replaceAll("&", "\u00A7");
                e.setMotd(motd);
        }
       
        public void onEnable() {
                getConfig().options().copyDefaults(true);
                saveConfig();
                Bukkit.getServer().getPluginManager().registerEvents(this, this);
        }
       
        public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
                if (cmd.getName().equalsIgnoreCase("motd")) {
                        if (!sender.hasPermission("motd.check")) {
                                sender.sendMessage("§cYou are not permitted to do this!");
                                return true;
                        }
                        String motd = getConfig().getString("motd.ingame");
                        motd = motd.replaceAll("&", "§");
                        String system = getConfig().getString("motd.system");
                        system = system.replaceAll("&", "§");
                        sender.sendMessage("§aIn-Game §c§lMOTD: " + motd);
                        sender.sendMessage("§aSystem §c§lMOTD: " + system);
                        return true;
                }
                if (cmd.getName().equalsIgnoreCase("setmotd")) {
                        if (!sender.hasPermission("motd.set")) {
                                sender.sendMessage("§cYou are not permitted to do this!");
                                return true;
                        }
                        if (args.length == 0) {
                                sender.sendMessage("§cPlease specify a message!");
                                return true;
                        }
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                                str.append(args[i] + " ");
                        }
                        String motd = str.toString();
                        getConfig().set("motd.ingame", motd);
                        saveConfig();
                        String newmotd = getConfig().getString("motd.ingame");
                        motd = motd.replaceAll("&", "§");
                        sender.sendMessage("§c§lMOTD §aset to: " + newmotd);
                        return true;
                }
                if (cmd.getName().equalsIgnoreCase("setsystemmotd")) {
                        if (!sender.hasPermission("motd.set")) {
                                sender.sendMessage("§cYou are not permitted to do this!");
                                return true;
                        }
                        if (args.length == 0) {
                                sender.sendMessage("§cPlease specify a message!");
                                return true;
                        }
                        StringBuilder str = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                                str.append(args[i] + " ");
                        }
                        String motd = str.toString();
                        getConfig().set("motd.system", motd);
                        saveConfig();
                        String system = getConfig().getString("motd.system");
                        system = system.replaceAll("&", "§");
                        sender.sendMessage("§c§lMOTD §aset to: " + system);
                        return true;
                }
                return true;
        }
}