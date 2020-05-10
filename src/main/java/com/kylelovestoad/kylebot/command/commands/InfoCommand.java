package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class InfoCommand implements ICommand {

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
        return Config.get("prefix") + this.getName();
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

    @Override
    public List<String> getAliases() {
        return List.of("botinfo", "information");
    }


}