package com.kylelovestoad.kylebot.command.commands.general;

import com.kylelovestoad.kylebot.command.Command;
import com.kylelovestoad.kylebot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class InfoCommand implements Command {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("General Information")
                .setDescription("KyleBot radiates massive amounts of chad energy")
                .addField("Created by:", "kylelovestoad#4851", false)
                .setColor(Color.BLUE);

        channel.sendMessage(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "info";
    }

    @Override
    public String getHelp() {
        return "Shows general bot information";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandType getType() {
        return CommandType.GENERAL;
    }

    @Override
    public List<String> getAliases() {
        return List.of("botinfo", "information");
    }


}