package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.Command;
import com.kylelovestoad.kylebot.command.CommandType;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class KickCommand implements Command {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        final TextChannel channel = event.getChannel();

        final Message message = event.getMessage();

        final Member selfMember = event.getGuild().getSelfMember();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ Imagine not putting any arguments")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();

            return;

        }


        if (message.getMentionedMembers().isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ You've gotta mention someone")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();

            return;
        }

        final Member target = message.getMentionedMembers().get(0);

        if (!selfMember.canInteract(target)) {
            channel.sendMessage("I can't do that shit because that user has a role higher than or the same as my role.").queue();
            return;
        }



        if (args.size() >= 2) {

            String reason = String.join(" ", args.subList(1, args.size()));

            event.getGuild()
                    .kick(target, reason)
                    .reason(reason)
                    .queue();

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("SUCCESS")
                    .setDescription("Kicked " + target.getEffectiveName() + " from the server for " + reason)
                    .setFooter("Goodbye")
                    .setColor(Color.BLUE);

            channel.sendMessage(embed.build()).queue();

            return;
        }

        if (args.size() == 1) {

            event.getGuild()
                    .kick(target)
                    .queue();

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("SUCCESS")
                    .setDescription("Kicked " + target.getEffectiveName() + " from the server")
                    .setFooter("Goodbye")
                    .setColor(Color.BLUE);

            channel.sendMessage(embed.build()).queue();



        }
    }


    @Override
    public String getName() {
        return "kick";
    }


    @Override
    public String getHelp() {
        return "Kicks a specified user from the server";
    }

    @Override
    public String getUsage() {
        return "<user mention> [reason]";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandType getType() {
        return CommandType.MODERATION;
    }

    @Override
    public EnumSet<Permission> getPermissions() {
        return EnumSet.of(Permission.KICK_MEMBERS);
    }
}

