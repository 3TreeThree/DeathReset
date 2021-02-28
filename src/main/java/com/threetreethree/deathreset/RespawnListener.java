package com.threetreethree.deathreset;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.List;
import java.util.Objects;

public class RespawnListener implements Listener {

  @EventHandler
  public void onPlayerRespawn(PlayerRespawnEvent event) {
    Bukkit.broadcastMessage("Starting teleport");
    Bukkit.getScheduler().scheduleSyncDelayedTask(Bukkit.getPluginManager().getPlugin("DeathReset"), () -> {
        List<Player> playersWrongWorld = Objects.requireNonNull(Bukkit.getWorld("world")).getPlayers();
        if (!playersWrongWorld.isEmpty()) {
            playersWrongWorld.forEach(
                player -> {
                    Bukkit.broadcastMessage(player.getName());
                    player.teleport(Objects.requireNonNull(Bukkit.getWorld("world_cur")).getSpawnLocation());
                });
        }
    }, 20);
  }
}
