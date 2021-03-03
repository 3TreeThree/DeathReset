package com.threetreethree.deathreset;

import net.kyori.adventure.text.Component;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class DeathListener implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) throws IOException {
    String username = event.getEntity().getName();
    Bukkit.broadcastMessage(
        "Player: " + username + " has died. Your death has catastrophic effects.");
    Bukkit.getScheduler()
        .scheduleSyncDelayedTask(
            Bukkit.getPluginManager().getPlugin("DeathReset"),
            () -> {
              Objects.requireNonNull(Bukkit.getWorld("world_cur"))
                  .getPlayers()
                  .forEach(
                      player -> {
                        player.setHealth(0);
                        player.kick(Component.text("Blame " + username + "."));
                      });
              Objects.requireNonNull(Bukkit.getWorld("world_cur_nether"))
                  .getPlayers()
                  .forEach(
                      player -> {
                        player.setHealth(0);
                        player.kick(Component.text("Blame " + username + "."));
                      });
              Objects.requireNonNull(Bukkit.getWorld("world_cur_end"))
                  .getPlayers()
                  .forEach(
                      player -> {
                        player.setHealth(0);
                        player.kick(Component.text("Blame " + username + "."));
                      });
            },
            5);
    Bukkit.getScheduler()
        .scheduleSyncDelayedTask(
            Bukkit.getPluginManager().getPlugin("DeathReset"),
            () -> {
              Bukkit.unloadWorld("world_cur", true);
              Bukkit.unloadWorld("world_cur_nether", true);
              Bukkit.unloadWorld("world_cur_end", true);

              File worldContainer = Bukkit.getWorldContainer();
              for (File file : worldContainer.listFiles()) {
                if (file.isDirectory() && file.getName().contains("world_cur")) {
                  try {
                    FileUtils.deleteDirectory(file);
                  } catch (IOException e) {
                    e.printStackTrace();
                  }
                  Bukkit.broadcastMessage("Deleting: " + file.getName());
                }
              }
            },
            5);

    Bukkit.getScheduler()
        .scheduleSyncDelayedTask(
            Bukkit.getPluginManager().getPlugin("DeathReset"),
            () -> {
              Bukkit.createWorld(WorldCreator.name("world_cur")).setDifficulty(Difficulty.HARD);
              Bukkit.createWorld(
                      new WorldCreator("world_cur_nether").environment(World.Environment.NETHER))
                  .setDifficulty(Difficulty.HARD);
              Bukkit.createWorld(
                      new WorldCreator("world_cur_end").environment(World.Environment.THE_END))
                  .setDifficulty(Difficulty.HARD);
              Objects.requireNonNull(Bukkit.getWorld("world"))
                  .getPlayers()
                  .forEach(
                      player -> {
                        player.teleport(Bukkit.getWorld("world_cur").getSpawnLocation());
                        player.setBedSpawnLocation(Bukkit.getWorld("world_cur").getSpawnLocation());
                      });
            },
            20);
  }
}
