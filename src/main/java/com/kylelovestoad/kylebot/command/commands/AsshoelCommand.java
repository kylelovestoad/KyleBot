package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class AsshoelCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        final List<String> args = ctx.getArgs();

        final TextChannel channel = ctx.getChannel();

        final Message message = ctx.getMessage();

        if (args.size() < 1) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Imagine not doing the command correctly")
                    .setDescription("Here's how you do it stupid: `" + Config.get("prefix") + this.getName() + " <user mention>` ")
                    .setFooter("could not be me")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;

        }

        if (message.getMentionedMembers().isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("gUh")
                    .setDescription("You've gotta mention someone, it cant just be any text")
                    .setFooter("Oh no")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }


        if (args.size() >= 1) {

            channel.sendMessage(args.get(0) + " fuck you\n" + args.get(0) + "  fuck you asshoel fuck you").queue();
        }
    }

    @Override
    public String getName() {
        return "asshoel";
    }

    @Override
    public String getHelp() {
        return "Calls the user you pinged an asshoel\n" +
                "Usage: `" + Config.get("prefix") + this.getName() + " <user>`";
    }
}