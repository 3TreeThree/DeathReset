package com.threetreethree.deathreset;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

public class DeathReset extends JavaPlugin {

    @Override
    public void onEnable() {
        if(Bukkit.getWorld("world_cur") == null){
            Bukkit.createWorld(WorldCreator.name("world_cur"));
        }
        getServer().getPluginManager().registerEvents(new DeathListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
    }

}

