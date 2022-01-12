package com.voidjns.skygrid.chunkgenerators;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.ChunkData;

public interface ChunkGen {

    public ChunkData generateChunkData(ChunkData chunkData);
}
