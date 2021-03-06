package com.kylelovestoad.kylebot.command.commands.fun;

import com.kylelovestoad.kylebot.command.Command;
import com.kylelovestoad.kylebot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class NoSirCommand implements Command {

    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        int amount = (int) (Math.random() * 100 + 1);

        final EmbedBuilder embed = new EmbedBuilder()
                .setDescription("no" + "s".repeat(amount) + "ir")
                .addField("Number of s's:", String.valueOf(amount), true)
                .setColor(Color.BLUE);

        channel.sendMessage(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "nosir";
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
    public String getHelp() {
        return "NOSSSSSSSSSSSSSSSIRRRRRRRRRRR";
    }

    @Override
    public CommandType getType() {
        return CommandType.FUN;
    }
}
