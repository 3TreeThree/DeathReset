package com.threetreethree.deathreset;

import org.bukkit.plugin.java.JavaPlugin;

public class DeathReset extends JavaPlugin {

  @Override
  public void onEnable() {
    getServer().getPluginManager().registerEvents(new DeathListener(), this);
  }
}
