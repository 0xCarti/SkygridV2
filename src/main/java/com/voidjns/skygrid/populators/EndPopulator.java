package com.voidjns.skygrid.populators;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EndPopulator extends BlockPopulator {
    private List<EntityType> spawnerTypes = new ArrayList<>();
    private Random random = new Random();

    public EndPopulator(FileConfiguration config){
        for(String key : config.getConfigurationSection("end-mob-spawners").getKeys(false)) {
            try{
                double prob = config.getDouble("end-mob-spawners." + key) * 100;
                for(int i = 0; i < prob; i++){
                    spawnerTypes.add(EntityType.valueOf(key));
                }
            }catch (IllegalArgumentException e) {
                System.out.println("[End Populator] " + key + " is not a valid entity!");
            }
        }

        if(spawnerTypes.size() > 10000){
            System.out.println("[End Populator] Allocated " + (spawnerTypes.size() - 10000)/100 + " extra percentage. Some blocks won't be spawned.");
            spawnerTypes = spawnerTypes.subList(0, 10000);
        }else if (spawnerTypes.size() < 10000){
            System.out.println("[End Populator] Allocated " + (10000 - spawnerTypes.size())/100 + " percentage less. Enderman will be used.");
            for(int i = 0; i < (10000-spawnerTypes.size()); i++){
                spawnerTypes.add(EntityType.ENDERMAN);
            }
        }
    }

    @Override
    public void populate(WorldInfo worldInfo, Random random, int x, int z, LimitedRegion limitedRegion) {
        List<BlockState> entities = limitedRegion.getTileEntities();
        for(BlockState blockState : entities){
            if(blockState.getData().getItemType().equals(Material.LEGACY_MOB_SPAWNER)){
                CreatureSpawner spawner = (CreatureSpawner) blockState;
                spawner.setSpawnedType(spawnerTypes.get(random.nextInt(spawnerTypes.size())));
                spawner.update(true, false);
            }
        }
    }

    @Override
    public void populate(World world, Random random, Chunk source) {
        BlockState[] entities = source.getTileEntities();
        for(BlockState blockState : entities){
            if(blockState.getData().getItemType().equals(Material.LEGACY_MOB_SPAWNER)){
                CreatureSpawner spawner = (CreatureSpawner) blockState;
                spawner.setSpawnedType(spawnerTypes.get(random.nextInt(spawnerTypes.size())));
                spawner.update(true, false);
            }
        }
    }
}
