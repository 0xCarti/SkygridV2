package com.voidjns.skygrid.chunkgenerators;

import com.voidjns.skygrid.Skygrid;
import com.voidjns.skygrid.populators.EndPopulator;
import com.voidjns.skygrid.populators.NetherPopulator;
import com.voidjns.skygrid.populators.OverworldPopulator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkygridChunkGen extends ChunkGenerator {
    Skygrid plugin;
    ChunkGen overworldChunkGen, netherChunkGen, endChunkGen;
    FileConfiguration config;

    public SkygridChunkGen(Skygrid plugin, FileConfiguration config) {
        this.plugin = plugin;
        boolean onePoint17Plus = true;
        this.config = config;

        try {

            ChunkData.class.getMethod("getMaxHeight");
            ChunkData.class.getMethod("getMinHeight");

        } catch (NoSuchMethodException e) {

            onePoint17Plus = false;

        }

        overworldChunkGen = new OverworldChunkGen(config, onePoint17Plus);
        netherChunkGen = new NetherChunkGen(config, onePoint17Plus);
        endChunkGen = new EndChunkGen(config, onePoint17Plus);

    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int x, int z, ChunkGenerator.BiomeGrid biome) {

        ChunkData chunkData = Bukkit.createChunkData(world);

        chunkData = switch (world.getEnvironment()) {
            case NORMAL, CUSTOM -> chunkData = overworldChunkGen.generateChunkData(chunkData);
            case NETHER -> chunkData = netherChunkGen.generateChunkData(chunkData);
            case THE_END -> chunkData = endChunkGen.generateChunkData(chunkData);

        };

        return chunkData;

    }

    @Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        List<BlockPopulator> populators = new ArrayList<>();
        switch (world.getEnvironment()){
            case NORMAL, CUSTOM -> populators.add(new OverworldPopulator(plugin, config));
            case NETHER -> populators.add(new NetherPopulator(config));
            case THE_END -> populators.add(new EndPopulator(config));
        }
        return populators;
    }
}
