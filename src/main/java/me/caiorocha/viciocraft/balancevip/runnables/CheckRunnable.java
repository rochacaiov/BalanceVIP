package me.caiorocha.viciocraft.balancevip.runnables;

import me.caiorocha.viciocraft.balancevip.managers.BaltopManager;
import org.bukkit.scheduler.BukkitRunnable;

public class CheckRunnable extends BukkitRunnable {
    private BaltopManager baltopManager;

    public CheckRunnable(BaltopManager baltopManager){
        this.baltopManager = baltopManager;
    }

    @Override
    public void run() {
        baltopManager.checkBaltop();
    }
}
