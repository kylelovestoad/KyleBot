package com.kylelovestoad.kylebot.command.commands.fun;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class ExpandCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ You have to actually put text to expand")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        channel.sendMessage(String.join(" ", args.subList(0,args.size())).replace("", " ").trim()).queue();
    }

    @Override
    public String getName() {
        return "expand";
    }

    @Override
    public String getHelp() {
        return "expands text that you input";
    }

    @Override
    public String getUsage() {
        return "<text>";
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
