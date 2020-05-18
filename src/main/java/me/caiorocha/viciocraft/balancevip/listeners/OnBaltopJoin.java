package me.caiorocha.viciocraft.balancevip.listeners;

import me.caiorocha.viciocraft.balancevip.cache.BaltopCache;
import me.caiorocha.viciocraft.balancevip.utils.MessageView;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnBaltopJoin implements Listener {

    @EventHandler
    public void onBaltopJoin(PlayerJoinEvent e){
        if(e.getPlayer().getUniqueId().equals(BaltopCache.getUuid())){
            MessageView.broadcastBaltopJoin(e.getPlayer());
        }
    }
}
