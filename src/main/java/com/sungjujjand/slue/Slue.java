package com.sungjujjand.slue;

import org.bukkit.plugin.java.JavaPlugin;

public final class Slue extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.getCommand("slue").setExecutor(new commands());
        this.getServer().getPluginManager().registerEvents(new events(), this);
        //start pl messege
        getLogger().info("Slue plugin started");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        //stop pl messege
        getLogger().info("Slue plugin stopped");
    }
}
