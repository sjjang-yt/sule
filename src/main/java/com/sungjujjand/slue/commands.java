package com.sungjujjand.slue;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;



public class commands implements CommandExecutor {

    private String suleplayer;
    //make isEnterTheEndEvent
    public static boolean isEnterTheEndEvent = false;

    public void sendTitleToPlayer(Player player, String title, String subtitle, int fadeInTime, int stayTime, int fadeOutTime) {
        player.sendTitle(title, subtitle, fadeInTime, stayTime, fadeOutTime);
    }

    private int countSurvivalPlayers(Server server) {
        int survivalPlayers = 0;
        for (Player player : server.getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                survivalPlayers++;
            }
        }
        return survivalPlayers;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        //if command is /slue
        //get player list
        if (strings[0].equalsIgnoreCase("list")) {
            commandSender.sendMessage("List of players:");
            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                commandSender.sendMessage(commandSender.getServer().getOnlinePlayers().toArray()[i].toString());
            }
        }
        //get random player
        if (strings[0].equalsIgnoreCase("random")) {
            //broadcast all player
            Player player = (Player) commandSender;
            if (strings.length != 2) {
                player.sendMessage("잘못된 명령어 형식입니다. 사용법: /sule random <숫자>");
                return true;
            }
            commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "gamemode survival @a");
            //only print player's name
            String rp = commandSender.getServer().getOnlinePlayers().toArray()[(int) (Math.random() * commandSender.getServer().getOnlinePlayers().size())].toString();
            String[] rpa = commandSender.getServer().getOnlinePlayers().toArray()[(int) (Math.random() * commandSender.getServer().getOnlinePlayers().size())].toString().split("=", 2);
            String[] rpaf = rpa[1].split("}", 2); // finally got player's name
            //show title all player
            commandSender.getServer().broadcastMessage("술래 : " + rpaf[0]);
            //player == all player
            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                //send title to player
                sendTitleToPlayer((Player) commandSender.getServer().getOnlinePlayers().toArray()[i], " 술 래 ", rpaf[0], 10, 70, 20);
            }
            //give item to rpaf[0] sule minecraft:diamond_sword{Enchantments:[{id:"minecraft:sharpness",lvl:1000}]}
            commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "give " + rpaf[0] + " minecraft:diamond_sword{Enchantments:[{id:\"minecraft:sharpness\",lvl:1000}]}");
            commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + rpaf[0] + " add 술래");
            //make timer bossbar

            int number = Integer.parseInt(strings[1]);
            //give rpaf[0
            suleplayer = rpaf[0];
            //start 2min timer
            new BukkitRunnable() {
                int time = number;
                @Override
                public void run() {
                    //if time is 0
                    if (time == 0) {
                        //stop timer
                        cancel();
                        //delete all player's tag
                        for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                            //delete tag
                            commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + suleplayer + " remove 술래");
                            //delete item
                            commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "clear " + suleplayer + " minecraft:diamond_sword");
                            suleplayer = "";
                        }
                    }
                    //if time is not 0
                    else {
                        //show bossbar
                        //make bossbar
                        BossBar bossBar = Bukkit.createBossBar("남은 시간 : " + time + "초", BarColor.RED, BarStyle.SOLID);
                        //add player all player
                        for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                            bossBar.addPlayer((Player) commandSender.getServer().getOnlinePlayers().toArray()[i]);
                        }
                        //show bossbar
                        bossBar.setVisible(true);
                        //decrease time
                        time--;
                        if (countSurvivalPlayers(player.getServer()) == 1) {
                            //stop timer
                            cancel();
                            //delete all player's tag
                            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                                //delete tag
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + suleplayer + " remove 술래");
                                //delete item
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "clear " + suleplayer + " minecraft:diamond_sword");
                                //title
                                sendTitleToPlayer((Player) commandSender.getServer().getOnlinePlayers().toArray()[i], "술래 승리!", "축하합니다!", 10, 70, 20);
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "gamemode creative @a");
                                suleplayer = "";
                            }
                        }
                        if (time == 0) {
                            //stop timer
                            cancel();
                            //delete all player's tag
                            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                                //delete tag
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + suleplayer + " remove 술래");
                                //delete item
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "clear " + suleplayer + " minecraft:diamond_sword");
                                //title
                                sendTitleToPlayer((Player) commandSender.getServer().getOnlinePlayers().toArray()[i], "시민 승리!", "축하합니다!", 10, 70, 20);
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "gamemode creative @a");
                                suleplayer = "";
                            }
                        }
                        //if become enter the end event
                        if (isEnterTheEndEvent) {
                            //stop timer
                            cancel();
                            //delete all player's tag
                            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                                //delete tag
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + suleplayer + " remove 술래");
                                //delete item
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "clear " + suleplayer + " minecraft:diamond_sword");
                                //title
                                sendTitleToPlayer((Player) commandSender.getServer().getOnlinePlayers().toArray()[i], "시민 승리!", "축하합니다!", 10, 70, 20);
                                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "gamemode creative @a");
                                suleplayer = "";
                                isEnterTheEndEvent = false;
                            }
                        }
                        //delete bossbar after 1sec
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                bossBar.removeAll();
                            }
                        }.runTaskLater(commandSender.getServer().getPluginManager().getPlugin("Slue"), 20);
                    }
                }
            }.runTaskTimer(commandSender.getServer().getPluginManager().getPlugin("Slue"), 0, 20);
        }

        //if command is /slue delete
        if (strings[0].equalsIgnoreCase("delete")) {
            //delete all player's tag
            for (int i = 0; i < commandSender.getServer().getOnlinePlayers().size(); i++) {
                //delete tag
                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "tag " + suleplayer + " remove 술래");
                //delete item
                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "clear " + suleplayer + " minecraft:diamond_sword");
                suleplayer = "";
                //all player survival
                commandSender.getServer().dispatchCommand(commandSender.getServer().getConsoleSender(), "gamemode creative @a");
            }
        }
        return true;
    }
}
