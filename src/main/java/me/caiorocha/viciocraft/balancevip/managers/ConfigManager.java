package me.caiorocha.viciocraft.balancevip.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Objects;

public class ConfigManager {
    private File file;
    private FileConfiguration fileConfiguration;
    private JavaPlugin plugin;

    public ConfigManager(JavaPlugin plugin, String name){
        this.plugin = plugin;
        this.file = new File(plugin.getDataFolder(), name);

        reloadConfig();
    }


    public void reloadConfig() {
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
        Reader reader = new InputStreamReader(Objects.requireNonNull(this.plugin.getResource(this.file.getName())));
        YamlConfiguration imputConfig = YamlConfiguration.loadConfiguration(reader);
        getFileConfiguration().setDefaults(imputConfig);
    }


    public void saveConfig() {
        try {
            getFileConfiguration().save(this.file);
        } catch (IOException iOException) {}
    }

    public void saveDefault() {
        getFileConfiguration().options().copyDefaults(true);
        saveConfig();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public FileConfiguration getFileConfiguration() {
        return fileConfiguration;
    }

    public void setFileConfiguration(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(JavaPlugin plugin) {
        this.plugin = plugin;
    }
}
