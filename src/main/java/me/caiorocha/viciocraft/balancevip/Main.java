package me.caiorocha.viciocraft.balancevip;

import me.caiorocha.viciocraft.balancevip.hooks.VaultHook;
import me.caiorocha.viciocraft.balancevip.listeners.OnBaltopJoin;
import me.caiorocha.viciocraft.balancevip.managers.BaltopManager;
import me.caiorocha.viciocraft.balancevip.managers.ConfigManager;
import me.caiorocha.viciocraft.balancevip.utils.MessageView;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private ConfigManager config;
    private ConfigManager messages;

    @Override
    public void onEnable() {
        VaultHook.setupEconomy();

        this.config = new ConfigManager(this, "config.yml");
        this.messages = new ConfigManager(this, "messages.yml");
        setupFiles();

        new MessageView(this);
        new BaltopManager(this);

        getServer().getPluginManager().registerEvents(new OnBaltopJoin(), this);

        System.out.println("\n" +
                ChatColor.GOLD+" __"+ChatColor.GREEN+"          __  \n" +
                ChatColor.GOLD+"|__)"+ChatColor.GREEN+" \\  / | |__) §6Balance§aVIP §bv" + getDescription().getVersion() + "\n" +
                ChatColor.GOLD+"|__)"+ChatColor.GREEN+"  \\/  | |    §8Created By F1rmeza#9992" + "\n" +
                ChatColor.GOLD+"                 \n");
    }

    @Override
    public void onDisable() {

    }

    public ConfigManager getMessages() {
        return messages;
    }

    public ConfigManager getCustomConfig() {
        return config;
    }

    private void setupFiles() {
        if (!this.config.getFile().exists() && !this.messages.getFile().exists()) {
            this.config.saveDefault();
            this.messages.saveDefault();
        }
    }
}
