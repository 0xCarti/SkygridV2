package com.voidjns.skygrid.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class LeafBreak implements Listener {

    @EventHandler
    public void onLeafBreak(LeavesDecayEvent e) {

        e.setCancelled(true);

    }

}
