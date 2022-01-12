package com.voidjns.skygrid.chunkgenerators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.plugin.PluginLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EndChunkGen implements ChunkGen {

    private List<Material> blocks = new ArrayList<>();
    private Material defaultBlock;
    private Random random = new Random();
    private boolean onePoint17Plus;

    public EndChunkGen(FileConfiguration config, boolean onePoint17Plus) {
        this.onePoint17Plus = onePoint17Plus;
        defaultBlock = Material.getMaterial(config.getString("end-default"));

        for(String key : config.getConfigurationSection("end").getKeys(false)) {
            if (Material.getMaterial(key) != null && Material.getMaterial(key).isBlock()) {
                double prob = config.getDouble("end." + key) * 100;
                for(int i = 0; i < prob; i++){
                    blocks.add(Material.getMaterial(key));
                }
            } else System.out.println("[End Generator] " + key + " is not a valid block!");
        }

        if(blocks.size() > 10000){
            System.out.println("[End Generator] Allocated " + (blocks.size() - 10000)/100 + " extra percentage. Some blocks won't be spawned.");
            blocks = blocks.subList(0, 10000);
        }else if (blocks.size() < 10000){
            System.out.println("[End Generator] Allocated " + (10000 - blocks.size())/100 + " percentage less. Default block will be used.");
            for(int i = 0; i < (10000-blocks.size()); i++){
                blocks.add(defaultBlock);
            }
        }
    }

    @Override
    public ChunkData generateChunkData(ChunkData chunkData) {
        double rand;
        int minHeight = (onePoint17Plus ? chunkData.getMinHeight() : 0);
        //int maxHeight = (onePoint17Plus ? chunkData.getMaxHeight() : 256);
        int maxHeight = 60;

        for(int x=0; x<15; x+=4) {
            for(int y=minHeight; y<maxHeight; y+=4) {
                for(int z=0; z<15; z+=4) {
                    rand = random.nextInt(blocks.size());
                    Material block = blocks.get((int) rand);
                    chunkData.setBlock(x, y, z, block);
                }
            }
        }
        return chunkData;
    }


}
