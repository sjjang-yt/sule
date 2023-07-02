package com.sungjujjand.slue;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;

public class events implements org.bukkit.event.Listener {
    @EventHandler
    public void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        event.getPlayer().sendMessage("Welcome to the server!");
    }

    @EventHandler
    public void onPlayerLeave(org.bukkit.event.player.PlayerQuitEvent event) {
        event.getPlayer().sendMessage("Goodbye!");
    }

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        //event cancel
        event.setCancelled(true);
        //set player game mode spectator
        event.getEntity().setGameMode(org.bukkit.GameMode.SPECTATOR);
    }

    @EventHandler
    public void onPlayerEnterEnd(org.bukkit.event.player.PlayerPortalEvent event) {
        commands.isEnterTheEndEvent = true;
    }

    @EventHandler
    //right click nether star
    public void onPlayerRightClickNetherStar(org.bukkit.event.player.PlayerInteractEvent event) {
        if (event.getItem() != null && event.getItem().getType() == Material.DIAMOND) {
            //send all player's location
            for (org.bukkit.entity.Player player : event.getPlayer().getServer().getOnlinePlayers()) {
                event.getPlayer().sendMessage(player.getName() + " 는 " + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ()+"에 있습니다.");
                //all player get 발광 100s
                player.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.GLOWING, 2000, 1));
            }
            //delete diamond 1
            event.getItem().setAmount(event.getItem().getAmount() - 1);
        }
    }

}
