package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class OwoifyCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Nice job")
                    .setDescription("❌ Very awesome that you didn't put text to owoify")
                    .setFooter("...")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        final String s;

        int rand = (int) (Math.random()*3);

        switch (rand) {
            case 0 -> s = "Huohhh~";
            case 1 -> s = "OwO";
            case 2 -> s = "UwU";
            case 3 -> s = ">w<";
            case 4 -> s = "x3";
            case 5 -> s = ":3";
            default -> {
                return;
            }

        }

        channel.sendMessage(s + " " + String.join(" ", args.subList(0,args.size())).replaceAll("[rl]", "w")).queue();
    }

    @Override
    public String getName() {
        return "owoify";
    }

    @Override
    public String getHelp() {
        return "Owoifies text";
    }

    @Override
    public String getUsage() {
        return " <text>";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }
}
