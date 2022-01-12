## Skygrid
The Skygrid plugin modifies the generation of Minecraft worlds so as to mimic the 'Skygrid' game-mode.
This plugin is highly configurable to suit a broad range of needs. It was built using the Spigot API
@ https://spigotmc.org

### Features
The Skygrid plugin comes packed with various features such as:

- Chunk generators for the overworld, nether and end

- Block populators for the overworld, nether and end to customize mob spawner generation.

- Complete control over the chunk generators (i.e. you choose the blocks that spawn and the probability that said blocks will spawn)

- Chests can be filled with items based on probablility (items&probabilities selected through the configuration)

- An optional random teleport command /rtp that works in all dimensions and is extremely configurable

### Installation
The installation of the Skygrid plugin to your server is very straight forward.

Some prerequisites to this guide are that you have a spigot (or modified version of Spigot) server set-up already and that you have downloaded the Skygrid plugin from the SpigotMC forums.

I also HIGHLY suggest you use the server jar known as Tuinity, which is a fork of Paper which is a fork of Spigot. Why? Tuinity uses its own light-engine called Starlight which boasts MASSIVE improvements in chunk loading speed. Due to the nature of Skygrid, many thousands of light calculations must be performed when creating new chunks and this is very hard on server resources. Starlight leads to chunks loading 20-30 times faster than if you were to use normal Spigot or Paper. If you don't want to use Tuinity, then I recommend you pregenerate the worlds and then use them.

1) Delete the existing default worlds (optional, but only new chunks will use the Skygrid chunk generators).

2) Download the Skygrid plugin from here and drop it into your plugins folder.

3) Copy the text below, open your bukkit.yml file and then paste the copied text at the bottom:
```
worlds:
  world:
    generator: Skygrid
  world_nether:
    generator: Skygrid
  world_the_end:
    generator: Skygrid
```
If you do not want to use a Skygrid generator for a particular dimension, then simply exclude the dimension from the bukkit.yml file.

4) Start your server!

### Configuration
Inside the configuration file you can modify almost every aspect of the plugin. There are instructions inside it that explain what each value represents and the effect modifying it would have

### Additional Info
The plugin might be compatible with versions lower than 1.16, I haven't tested the plugin on those versions.​