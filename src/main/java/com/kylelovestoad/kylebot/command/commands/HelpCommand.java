package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.CommandManager;
import com.kylelovestoad.kylebot.command.ICommand;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(CommandContext ctx) {
        List<String> args = ctx.getArgs();
        TextChannel channel = ctx.getChannel();

        if (args.isEmpty()){

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Command List")
                    .setColor(Color.BLUE);

            for (ICommand commands : manager.getCommands()) {
                embed.addField("`" + Config.get("prefix") + commands.getName() + "`", commands.getHelp(), false);
            }
            channel.sendMessage(embed.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand cmd = manager.getCommand(search);

        if (cmd == null) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Actually, what the fuck")
                    .setDescription("❌ That command does not exist. Check your facts idiot.")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle(cmd.getName().substring(0, 1).toUpperCase() + cmd.getName().substring(1))
                .setDescription(cmd.getHelp())
                .setColor(Color.BLUE);

        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the help menu\n" +
                "Usage: `" + Config.get("prefix") + this.getName() + " [command]`";
    }
    @Override
    public List<String> getAliases() {
        return List.of("cmd", "cmdlist", "commandlist", "helpme");
    }
}
