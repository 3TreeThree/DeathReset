package com.threetreethree.deathreset;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Thread.sleep;

public class DeathListener implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent event) throws IOException, InterruptedException {
    String username = event.getEntity().getName();

    Bukkit.getScheduler()
        .scheduleSyncDelayedTask(
            Objects.requireNonNull(Bukkit.getPluginManager().getPlugin("DeathReset")),
            () ->
                Objects.requireNonNull(Bukkit.getWorld("world"))
                    .getPlayers()
                    .forEach(player -> player.kick(Component.text(username + " has passed away."))),
            20);

    Bukkit.getServer().shutdown();

    sleep(25);

    Runtime.getRuntime().exec("cmd /c start \"\" serverRestart.bat");
  }
}
