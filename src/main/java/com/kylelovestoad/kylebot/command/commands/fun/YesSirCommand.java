package com.kylelovestoad.kylebot.command.commands.fun;

import com.kylelovestoad.kylebot.command.Command;
import com.kylelovestoad.kylebot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class YesSirCommand implements Command {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        final TextChannel channel = event.getChannel();

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
    public String getUsage() {
        return "";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public String getHelp() {
        return "YESSSSSSSSSSSSSSSSIRRRRRRRRRRR";
    }

    @Override
    public CommandType getType() {
        return CommandType.FUN;
    }

}
