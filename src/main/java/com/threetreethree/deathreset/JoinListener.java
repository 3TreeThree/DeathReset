package com.threetreethree.deathreset;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.List;
import java.util.Objects;

public class JoinListener implements Listener {

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event){
        List<Player> playersWrongWorld = Objects.requireNonNull(Bukkit.getWorld("world")).getPlayers();
        if (!playersWrongWorld.isEmpty()) {
            playersWrongWorld.forEach(
                    player -> {
                        player.teleport(Objects.requireNonNull(Bukkit.getWorld("world_cur")).getSpawnLocation());
                    });
        }
    }
}
