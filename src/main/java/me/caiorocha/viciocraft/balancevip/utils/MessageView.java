package me.caiorocha.viciocraft.balancevip.utils;

import me.caiorocha.viciocraft.balancevip.Main;
import me.caiorocha.viciocraft.balancevip.cache.BaltopCache;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class MessageView {
    private static final String TAG = "§8[§eBalance§aVIP§8] ";
    public static final String NEW_BALTOP = "§3{player} §6é o mais novo BALTOP!";
    private static String new_baltop;
    private static String old_baltop;
    private static String baltop_join;

    public MessageView(Main plugin){
        FileConfiguration messages = plugin.getMessages().getFileConfiguration();
        new_baltop = messages.getString("novo-baltop");
        old_baltop = messages.getString("antigo-baltop");
        baltop_join = messages.getString("baltop-login");
    }

    public static void broadcastBaltopJoin(OfflinePlayer baltopPlayer){
        Bukkit.broadcastMessage(format(baltopPlayer, baltop_join));
    }

    public static void broadcastNewBaltop(OfflinePlayer newTopPlayer){
        Bukkit.broadcastMessage(format(newTopPlayer, new_baltop));
    }

    public static void broadcastOldBaltop(OfflinePlayer oldTopPlayer){
        Bukkit.broadcastMessage(format(oldTopPlayer, old_baltop));
    }

    public static void messageToConsole(OfflinePlayer player, String message){
        Bukkit.getLogger().info(TAG + format(player, message));
    }

    private static String color(String linha){
        return ChatColor.translateAlternateColorCodes('&', linha);
    }

    private static String format(OfflinePlayer player, String message){
        return color(message.replace("{player}", Objects.requireNonNull(player.getName())));
    }
}
