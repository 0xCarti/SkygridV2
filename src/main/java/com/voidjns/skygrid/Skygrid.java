package com.voidjns.skygrid;

import com.voidjns.skygrid.chunkgenerators.SkygridChunkGen;
import com.voidjns.skygrid.commands.RandomTeleport;
import com.voidjns.skygrid.commands.SkygridCommand;
import com.voidjns.skygrid.listeners.ChestPopulator;
import com.voidjns.skygrid.listeners.DragonSpawner;
import com.voidjns.skygrid.listeners.FallingBlockStopper;
import com.voidjns.skygrid.listeners.LeafBreak;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class Skygrid extends JavaPlugin {
    SkygridChunkGen skygridChunkGen;

    public void onEnable() {

        saveDefaultConfig();

        skygridChunkGen = new SkygridChunkGen(this, getConfig());

        if(getConfig().getBoolean("disable-natural-leaf-break")) {
            getServer().getPluginManager().registerEvents(new LeafBreak(), this);
        }

        if(getConfig().getBoolean("populate-chests")) {
            getServer().getPluginManager().registerEvents(new ChestPopulator(getConfig()), this);
        }

        getServer().getPluginManager().registerEvents(new DragonSpawner(), this);
        getServer().getPluginManager().registerEvents(new FallingBlockStopper(this), this);

        getCommand("rtp").setExecutor(new RandomTeleport(getConfig()));
        getCommand("skygrid").setExecutor(new SkygridCommand());

    }

    public void onDisable() {

    }

    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
        return skygridChunkGen;
    }
}
