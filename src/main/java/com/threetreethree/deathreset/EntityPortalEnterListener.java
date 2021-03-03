package com.threetreethree.deathreset;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EntityPortalEnterListener implements Listener {

  @EventHandler
  public void onEntityPortal(EntityPortalEvent event) {
    Location destinationLocation;
    Location originalLocation = event.getFrom();
    PortalType type;
    if (originalLocation.getWorld().getName().equals("world_cur")
        && originalLocation.getBlock().getType().equals(Material.NETHER_PORTAL)) {
      type = PortalType.NETHER;
      destinationLocation = new Location(Bukkit.getWorld("world_cur_nether"), 0, 0, 0);
      scaleLocation(originalLocation, destinationLocation, 0.125, 1);
    } else if (originalLocation.getWorld().getName().equals("world_cur_nether")) {
      type = PortalType.NETHER;
      destinationLocation = new Location(Bukkit.getWorld("world_cur"), 0, 0, 0);
      scaleLocation(originalLocation, destinationLocation, 8, 1);
    } else if (originalLocation.getWorld().getName().equals("world_cur")
        && originalLocation.getBlock().getType().equals(Material.END_PORTAL)) {
      type = PortalType.ENDER;
      destinationLocation = new Location(Bukkit.getWorld("world_cur_end"), 100, 50, 0);
    } else {
      return;
    }

    if (!event.isCancelled()) {
      event.setTo(destinationLocation);
    }
  }

  @EventHandler
  public void onPlayerPortal(PlayerPortalEvent event) {
    Location destinationLocation;
    Location originalLocation = event.getFrom();
    PortalType type;
    if (originalLocation.getWorld().getName().equals("world_cur")
        && event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
      type = PortalType.NETHER;
      Bukkit.broadcastMessage("From Cur");
      destinationLocation = new Location(Bukkit.getWorld("world_cur_nether"), 0, 0, 0);
      scaleLocation(originalLocation, destinationLocation, 0.125, 1);
    } else if (originalLocation.getWorld().getName().equals("world_cur_nether")) {
      type = PortalType.NETHER;
      destinationLocation = new Location(Bukkit.getWorld("world_cur"), 0, 0, 0);
      scaleLocation(originalLocation, destinationLocation, 8, 1);
    } else if (originalLocation.getWorld().getName().equals("world_cur")
        && event.getCause().equals(PlayerTeleportEvent.TeleportCause.END_PORTAL)) {
      type = PortalType.ENDER;
      destinationLocation = new Location(Bukkit.getWorld("world_cur_end"), 100, 51, 0);
      Block block = destinationLocation.getBlock();
      for (int x = block.getX() - 2; x <= block.getX() + 2; x++) {
        for (int z = block.getZ() - 2; z <= block.getZ() + 2; z++) {
          Block platformBlock = destinationLocation.getWorld().getBlockAt(x, block.getY() - 1, z);
          if (platformBlock.getType() != Material.OBSIDIAN) {
            platformBlock.setType(Material.OBSIDIAN);
          }
          for (int yMod = 1; yMod <= 3; yMod++) {
            Block b = platformBlock.getRelative(BlockFace.UP, yMod);
            if (b.getType() != Material.AIR) {
              b.setType(Material.AIR);
            }
          }
        }
      }
    } else {
      return;
    }

    if (!event.isCancelled()) {
      event.setTo(destinationLocation);
      if (type == PortalType.NETHER) {
        event.setCanCreatePortal(true);
      } else {
        event.setCanCreatePortal(false);
      }
    }
  }

  private void scaleLocation(
      Location originalLocation, Location destination, double scaling, double yScaling) {
    destination.setX(originalLocation.getX() * scaling);
    destination.setY(originalLocation.getY() * yScaling);
    destination.setZ(originalLocation.getZ() * scaling);
  }
}
