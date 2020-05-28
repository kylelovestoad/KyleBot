package com.kylelovestoad.kylebot.command.commands.fun;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class AsshoelCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        final TextChannel channel = event.getChannel();

        final Message message = event.getMessage();

        final Member selfMember = event.getGuild().getSelfMember();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Bad")
                    .setDescription("❌ Imagine not putting any arguments")
                    .setFooter("Could not be me")
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

        if (message.getMentionedMembers().contains(selfMember)) {
            channel.sendMessage("very funny :rolling_eyes:").queue();
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
        return "Calls the user you pinged an asshoel";
    }

    @Override
    public String getUsage() {
        return "<user mention>";
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