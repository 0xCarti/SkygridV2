package com.voidjns.skygrid.chunkgenerators;

import com.voidjns.skygrid.Skygrid;
import com.voidjns.skygrid.listeners.FallingBlockStopper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Levelled;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

public class OverworldChunkGen implements ChunkGen {

    private List<Material> blocks = new ArrayList<>();
    private List<Material> grassBlockAccessories = new ArrayList<>();
    private List<Material> farmlandAccessories = new ArrayList<>();
    private List<Material> sandBlockAccessories = new ArrayList<>();
    private List<Material> gravelBlockAccessories = new ArrayList<>();
    private Material defaultBlock;
    private Random random = new Random();
    private boolean onePoint17Plus;
    private double seaPickleChance, amethystClusterChance;

    public OverworldChunkGen(FileConfiguration config, boolean onePointSeventeenPlus) {
        this.onePoint17Plus = onePointSeventeenPlus;
        defaultBlock = Material.getMaterial(config.getString("overworld-default"));
        seaPickleChance = config.getDouble("sea-pickle-chance");
        amethystClusterChance = config.getDouble("amethyst-cluster-chance");

        for(String key : config.getConfigurationSection("overworld").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("overworld." + key) * 100;
                for(int i = 0; i < prob; i++){
                    blocks.add(Material.getMaterial(key));
                }
            } else System.out.println("[Overworld Generator] " + key + " is not a valid block!");
        }

        if(blocks.size() > 10000){
            System.out.println("[Overworld Generator] Allocated " + (blocks.size() - 10000)/100 + " extra percentage. Some blocks won't be spawned.");
            blocks = blocks.subList(0, 10000);
        }else if (blocks.size() < 10000){
            System.out.println("[Overworld Generator] Allocated " + (10000 - blocks.size())/100 + " percentage less. Default block will be used.");
            for(int i = 0; i < (10000-blocks.size()); i++){
                blocks.add(defaultBlock);
            }
        }


        for(String key : config.getConfigurationSection("grassblock-accessories").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("grassblock-accessories." + key) * 100;
                for(int i = 0; i < prob; i++){
                    grassBlockAccessories.add(Material.getMaterial(key));
                }
            } else System.out.println("[Overworld Generator (Grass Block Accessories)] " + key + " is not a valid block!");

        }

        if(grassBlockAccessories.size() > 10000){
            System.out.println("[Overworld Generator - Grass_Block] Allocated " + (grassBlockAccessories.size() - 10000)/100 + " extra percentage. Some grass accessories won't be spawned.");
            grassBlockAccessories = grassBlockAccessories.subList(0, 10000);
        }else if (grassBlockAccessories.size() < 10000){
            System.out.println("[Overworld Generator - Grass_Block] Allocated " + (10000 - grassBlockAccessories.size())/100 + " percentage less. Air will be used.");
            for(int i = 0; i < (10000-grassBlockAccessories.size()); i++){
                grassBlockAccessories.add(Material.AIR);
            }
        }

        for(String key : config.getConfigurationSection("farmland-accessories").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("farmland-accessories." + key) * 100;
                for(int i = 0; i < prob; i++){
                    farmlandAccessories.add(Material.getMaterial(key));
                }
            } else System.out.println("[Overworld Generator (Farmland Accessories)] " + key + " is not a valid block!");

        }

        if(farmlandAccessories.size() > 10000){
            System.out.println("[Overworld Generator - Farm_Block] Allocated " + (farmlandAccessories.size() - 10000)/100 + " extra percentage. Some farm accessories won't be spawned.");
            farmlandAccessories = farmlandAccessories.subList(0, 10000);
        }else if (farmlandAccessories.size() < 10000){
            System.out.println("[Overworld Generator - Farm_Block] Allocated " + (10000 - farmlandAccessories.size())/100 + " percentage less. Air will be used.");
            for(int i = 0; i < (10000-farmlandAccessories.size()); i++){
                farmlandAccessories.add(Material.AIR);
            }
        }

        for(String key : config.getConfigurationSection("sand-accessories").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("sand-accessories." + key) * 100;
                for(int i = 0; i < prob; i++){
                    sandBlockAccessories.add(Material.getMaterial(key));
                }
            } else System.out.println("[Overworld Generator (Sand Accessories)] " + key + " is not a valid block!");

        }

        if(sandBlockAccessories.size() > 10000){
            System.out.println("[Overworld Generator - Sand_Block] Allocated " + (sandBlockAccessories.size() - 10000)/100 + " extra percentage. Some farm accessories won't be spawned.");
            sandBlockAccessories = sandBlockAccessories.subList(0, 10000);
        }else if (sandBlockAccessories.size() < 10000){
            System.out.println("[Overworld Generator - Sand_Block] Allocated " + (10000 - sandBlockAccessories.size())/100 + " percentage less. Air will be used.");
            for(int i = 0; i < (10000-sandBlockAccessories.size()); i++){
                sandBlockAccessories.add(Material.AIR);
            }
        }

        for(String key : config.getConfigurationSection("gravel-accessories").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("gravel-accessories." + key) * 100;
                for(int i = 0; i < prob; i++){
                    gravelBlockAccessories.add(Material.getMaterial(key));
                }
            } else System.out.println("[Overworld Generator (Gravel Accessories)] " + key + " is not a valid block!");

        }

        if(gravelBlockAccessories.size() > 10000){
            System.out.println("[Overworld Generator - Gravel_Block] Allocated " + (gravelBlockAccessories.size() - 10000)/100 + " extra percentage. Some farm accessories won't be spawned.");
            gravelBlockAccessories = gravelBlockAccessories.subList(0, 10000);
        }else if (gravelBlockAccessories.size() < 10000){
            System.out.println("[Overworld Generator - Gravel_Block] Allocated " + (10000 - gravelBlockAccessories.size())/100 + " percentage less. Air will be used.");
            for(int i = 0; i < (10000-gravelBlockAccessories.size()); i++){
                gravelBlockAccessories.add(Material.AIR);
            }
        }
    }

    @Override
    public ChunkData generateChunkData(ChunkData chunkData) {
        double rand;
        int minHeight = (onePoint17Plus ? chunkData.getMinHeight() : 0);
        //int maxHeight = (onePoint17Plus ? chunkData.getMaxHeight() : 256);
        int maxHeight = 192;

        for(int x=0; x<15; x+=4) {
            for(int y=minHeight; y<maxHeight; y+=4) {
                for(int z=0; z<15; z+=4) {
                    rand = random.nextInt(blocks.size());
                    Material block = blocks.get((int) rand);
                    chunkData.setBlock(x, y, z, block);

                    if(block.equals(Material.GRASS_BLOCK)) handleGrassBlock(x, y, z, chunkData);
                    else if(block.equals(Material.FARMLAND)) handleFarmland(x, y, z, chunkData);
                    else if(block.equals(Material.SAND)) handleSandBlock(x, y, z, chunkData);
                    else if(block.equals(Material.GRAVEL)) handleGravelBlock(x, y, z, chunkData);
                    else if(block.equals(Material.BUDDING_AMETHYST)) handleAmethystBlock(x, y, z, chunkData);
                    else if(block.name().toLowerCase().contains(("coral"))) handleCoralBlock(x, y, z, chunkData);

                    if(block.equals(Material.MOSS_BLOCK)){
                        chunkData.setBlock(x, y+1, z, Material.AZALEA);
                    }else if(block.equals(Material.MYCELIUM)) {
                        chunkData.setBlock(x, y + 1, z, Material.RED_MUSHROOM);
                    }
                }

            }

        }
        return chunkData;
    }

    private void handleAmethystBlock(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().equals(Material.BUDDING_AMETHYST)){
            return;
        }
        if(random.nextDouble() <= seaPickleChance){
            chunkData.setBlock(x, y + 1, z, Material.AMETHYST_CLUSTER);
        }
    }

    private void handleCoralBlock(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().name().toLowerCase().contains(("coral"))){
            return;
        }
        if(random.nextDouble() <= seaPickleChance){
            chunkData.setBlock(x, y + 1, z, Material.SEA_PICKLE);
        }
    }

    private void handleGravelBlock(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().equals(Material.GRAVEL)){
            return;
        }
        double rand = random.nextInt(gravelBlockAccessories.size());
        chunkData.setBlock(x, y+1, z, gravelBlockAccessories.get((int) rand));
    }

    private void handleSandBlock(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().equals(Material.SAND)){
            return;
        }
        double rand = random.nextInt(sandBlockAccessories.size());
        Material block = sandBlockAccessories.get((int) rand);
        if(block == Material.SUGAR_CANE){
            chunkData.setBlock(x+1, y, z, Material.WATER);
        }
        chunkData.setBlock(x, y+1, z, block);
    }

    private void handleGrassBlock(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().equals(Material.GRASS_BLOCK)){
            return;
        }
        if(random.nextBoolean()){
            double rand = random.nextInt(grassBlockAccessories.size());
            chunkData.setBlock(x, y+1, z, grassBlockAccessories.get((int) rand));
        }
    }

    private void handleFarmland(int x, int y, int z, ChunkData chunkData) {
        if(!chunkData.getBlockData(x, y, z).getMaterial().equals(Material.FARMLAND)){
            return;
        }
        if(random.nextBoolean()){
            double rand = random.nextInt(farmlandAccessories.size());
            Material block = farmlandAccessories.get((int) rand);
            chunkData.setBlock(x, y+1, z, block);
            if(block.equals(Material.SUGAR_CANE)){
                chunkData.setBlock(x, y, z+1, Material.WATER);
            }
        }
    }
}
