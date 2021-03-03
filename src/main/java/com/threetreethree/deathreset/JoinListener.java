package com.threetreethree.deathreset;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Objects;

public class JoinListener implements Listener {

  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent event) {
    if (Bukkit.getWorld("world_curr") == null) {
      Bukkit.createWorld(WorldCreator.name("world_cur"));
    }
    if (Bukkit.getWorld("world_curr_nether") == null) {
      Bukkit.createWorld(
          new WorldCreator("world_cur_nether").environment(World.Environment.NETHER));
    }
    if (Bukkit.getWorld("world_cur_end") == null) {
      Bukkit.createWorld(new WorldCreator("world_cur_end").environment(World.Environment.THE_END));
    }
    List<Player> playersWrongWorld = Objects.requireNonNull(Bukkit.getWorld("world")).getPlayers();
    if (!playersWrongWorld.isEmpty()) {
      playersWrongWorld.forEach(
          player -> {
            player.teleport(
                Objects.requireNonNull(Bukkit.getWorld("world_cur")).getSpawnLocation());
          });
    }
  }
}
