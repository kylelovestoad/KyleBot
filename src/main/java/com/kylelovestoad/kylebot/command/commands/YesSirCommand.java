package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class YesSirCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getChannel();

        int amount = (int) (Math.random() * 100 + 1);

        final EmbedBuilder embed = new EmbedBuilder()
                .setDescription("ye" + "s".repeat(amount) + "ir")
                .addField("Number of s's:", String.valueOf(amount), true)
                .setColor(0x0000FF);

        channel.sendMessage(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "yessir";
    }

    @Override
    public String getHelp() {
        return "YESSSSSSSSSSSSSSSSIRRRRRRRRRRR";
    }
}
