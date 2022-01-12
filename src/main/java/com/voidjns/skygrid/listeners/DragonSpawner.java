package com.voidjns.skygrid.listeners;

import org.bukkit.World;
import org.bukkit.entity.EnderDragon;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class DragonSpawner implements Listener {
    public DragonSpawner(){}

    @EventHandler
    public void onVoidJoin(PlayerChangedWorldEvent event){
        if(event.getPlayer().getWorld().getEnvironment().equals(World.Environment.THE_END)){
            event.getPlayer().getWorld().spawn(event.getPlayer().getWorld().getSpawnLocation(), EnderDragon.class);
        }
    }
}
