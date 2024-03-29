package me.caiorocha.viciocraft.balancevip.managers;

import me.caiorocha.viciocraft.balancevip.Main;
import me.caiorocha.viciocraft.balancevip.cache.BaltopCache;
import me.caiorocha.viciocraft.balancevip.hooks.VaultHook;
import me.caiorocha.viciocraft.balancevip.runnables.CheckRunnable;
import me.caiorocha.viciocraft.balancevip.utils.MessageView;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class BaltopManager {
    private Main plugin;
    private FileConfiguration config;

    public BaltopManager(Main plugin) {
        this.plugin = plugin;
        this.config = plugin.getCustomConfig().getFileConfiguration();

        getLastBaltop();

        new CheckRunnable(this).runTaskTimerAsynchronously(
                this.plugin, 20L, 20L * this.config.getInt("tempo-checagem")
        );
    }

    public void checkBaltop() {
        BaltopCache.formatCache();

        Arrays.stream(plugin.getServer().getOfflinePlayers())
                .filter(offlinePlayer -> BaltopCache.getBalance() < VaultHook.getEconomy().getBalance(offlinePlayer))
                .forEach(offlinePlayer -> {
                    BaltopCache.setBalance(VaultHook.getEconomy().getBalance(offlinePlayer));
                    BaltopCache.setUuid(offlinePlayer.getUniqueId());
                });

        if (BaltopCache.getUuid() != null) {
            if (!this.config.contains("player-atual")) {
                OfflinePlayer firstPlayer = Bukkit.getOfflinePlayer(BaltopCache.getUuid());
                updateBaltop(firstPlayer, null);
                saveBaltop(BaltopCache.getUuid(), BaltopCache.getBalance());

                MessageView.messageToConsole(firstPlayer, MessageView.NEW_BALTOP);
                return;
            }

            if (!UUID.fromString(Objects.requireNonNull(this.config.getString("player-atual"))).equals(BaltopCache.getUuid())) {
                if (hasDifference(this.config.getDouble("saldo-atual"))) {
                    updateBaltop(
                            Bukkit.getOfflinePlayer(BaltopCache.getUuid()),
                            Bukkit.getOfflinePlayer(UUID.fromString(
                                    Objects.requireNonNull(this.config.getString("player-atual"))
                                    )
                            )
                    );

                    saveBaltop(BaltopCache.getUuid(), BaltopCache.getBalance());
                }
            } else {
                saveBaltop(BaltopCache.getUuid(), BaltopCache.getBalance());
            }
        }
    }

    private void updateBaltop(OfflinePlayer newTopPlayer, OfflinePlayer oldTopPlayer) {
        MessageView.broadcastNewBaltop(newTopPlayer);
        plugin.getServer().getScheduler().callSyncMethod(
                plugin,
                () -> Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        "lp user " + newTopPlayer.getName() + " parent add vip"
                )
        );

        if (oldTopPlayer != null) {
            MessageView.broadcastOldBaltop(oldTopPlayer);
            plugin.getServer().getScheduler().callSyncMethod(
                    plugin,
                    () -> Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(),
                            "lp user " + oldTopPlayer.getName() + " parent remove vip"
                    )
            );
            plugin.getServer().getScheduler().callSyncMethod(plugin,
                    () -> Bukkit.dispatchCommand(
                            Bukkit.getConsoleSender(), "nick " + oldTopPlayer.getName() + "off"
                    )
            );
        }
    }

    private void saveBaltop(UUID uuid, double balance) {
        this.config.set("player-atual", uuid.toString());
        this.config.set("saldo-atual", balance);
        this.plugin.getCustomConfig().saveConfig();
    }

    private void getLastBaltop() {
        if (!this.config.contains("player-atual")) {
            //NO_BALTOP
            return;
        }
        BaltopCache.setUuid(UUID.fromString(Objects.requireNonNull(this.config.getString("player-atual"))));
        BaltopCache.setBalance(this.config.getDouble("saldo-atual"));
    }

    private boolean hasDifference(double oldBalance) {
        double difference = this.config.getDouble("valor-minimo");
        return ((BaltopCache.getBalance() - oldBalance) >= difference);
    }
}
