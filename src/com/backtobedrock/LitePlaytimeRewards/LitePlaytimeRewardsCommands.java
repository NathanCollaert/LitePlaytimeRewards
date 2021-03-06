package com.backtobedrock.LitePlaytimeRewards;

import com.backtobedrock.LitePlaytimeRewards.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class LitePlaytimeRewardsCommands implements TabCompleter {

    private final LitePlaytimeRewards plugin;

    public LitePlaytimeRewardsCommands() {
        this.plugin = JavaPlugin.getPlugin(LitePlaytimeRewards.class);
        Bukkit.getServer().getPluginCommand("givereward").setTabCompleter(this);
        Bukkit.getServer().getPluginCommand("lpr").setTabCompleter(this);
    }

    public boolean onCommand(CommandSender cs, Command cmnd, String alias, String[] args) {
        switch (cmnd.getName().toLowerCase()) {
            case "playtime":
                new PlaytimeCommand(cs, args).run();
                break;
            case "afktime":
                new AfktimeCommand(cs, args).run();
                break;
            case "givereward":
                new GiverewardCommand(cs, args).run();
                break;
            case "lpr":
                new LPRCommand(cs, args).run();
                break;
            case "rewards":
                new RewardsCommand(cs, args).run();
                break;

        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmnd, String alias, String[] args) {
        //create new array
        final List<String> completions = new ArrayList<>();

        switch (cmnd.getName().toLowerCase()) {
            case "givereward":
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0].toLowerCase(), this.plugin.getRewards().getAll().keySet(), completions);
                    Collections.sort(completions);
                }
                if (args.length == 2) {
                    StringUtil.copyPartialMatches(args[1].toLowerCase(), this.plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()), completions);
                    Collections.sort(completions);
                }
                if (args.length == 4) {
                    StringUtil.copyPartialMatches(args[3].toLowerCase(), Arrays.asList("true", "false"), completions);
                    Collections.sort(completions);
                }
                break;
            case "lpr":
                if (args.length == 1) {
                    StringUtil.copyPartialMatches(args[0].toLowerCase(), Arrays.asList("help", "reload", "reset"), completions);
                    Collections.sort(completions);
                }
                switch (args[0]) {
                    case "reset":
                        if (args.length == 2) {
                            StringUtil.copyPartialMatches(args[1].toLowerCase(), this.plugin.getRewards().getAll().keySet(), completions);
                            Collections.sort(completions);
                        }
                        if (args.length == 3) {
                            StringUtil.copyPartialMatches(args[2].toLowerCase(), this.plugin.getServer().getOnlinePlayers().stream().map(HumanEntity::getName).collect(Collectors.toList()), completions);
                            Collections.sort(completions);
                        }
                        break;
                }
        }
        return completions;
    }
}
