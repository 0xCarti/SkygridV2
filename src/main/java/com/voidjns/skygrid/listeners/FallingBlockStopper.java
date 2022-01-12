package com.voidjns.skygrid.listeners;

import com.voidjns.skygrid.Skygrid;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;


public class FallingBlockStopper implements Listener {
    public Skygrid plugin;

    public FallingBlockStopper(Skygrid plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockFall(BlockPhysicsEvent event){
        event.setCancelled(true);
    }
}
