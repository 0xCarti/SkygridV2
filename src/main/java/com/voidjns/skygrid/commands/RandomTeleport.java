package com.voidjns.skygrid.commands;

import com.voidjns.skygrid.Skygrid;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomTeleport implements CommandExecutor {

    private Random random = new Random();
    private int distance;
    private double cooldown;
    private List<Player> cooldownList = new ArrayList<>();

    public RandomTeleport(FileConfiguration config) {

        distance = config.getInt("rtp-max-distance");
        cooldown = config.getDouble("rtp-cooldown") * 20;

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {

        if(commandSender instanceof Player) {

            Player player = ((Player) commandSender);

            if(cooldownList.contains(player)) {

                player.sendMessage(ChatColor.GRAY + "» The cooldown for this command has not yet expired.");
                return false;

            } else cooldownList.add(player);

            Bukkit.getScheduler().runTaskLaterAsynchronously(Skygrid.getProvidingPlugin(Skygrid.class), () -> {

                cooldownList.remove(player);

            }, (long) cooldown);

            double x = 4 * (random.nextInt(distance*2) - distance);
            double y = 225;
            double z = 4 * (random.nextInt(distance*2) - distance);

            if(x == 0 && z == 0) {

                x+=0.5;
                z+=0.5;

            } else if(x < 0 && z < 0) {

                x+=0.5;
                z+=0.5;

            } else if(x > 0 && z > 0) {

                x+=0.5;
                z+=0.5;

            } else if(x < 0 && z > 0) {

                x+=0.5;
                z+=0.5;

            } else if(x > 0 && z < 0) {

                x+=0.5;
                z+=0.5;

            }

            player.teleport(new Location(player.getWorld(), x, y, z));

            player.sendMessage(ChatColor.GRAY + "» You have successfully teleported!");

        }

        return false;

    }

}
