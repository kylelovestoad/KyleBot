package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class BanCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        final TextChannel channel = event.getChannel();

        final Message message = event.getMessage();

        final Member selfMember = event.getGuild().getSelfMember();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Bruh")
                    .setDescription("❌ Imagine not putting any arguments")
                    .setFooter("Nice cock tho")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();

            return;

        }

        if (message.getMentionedMembers().isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("bro")
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

        if (args.size() == 1) {

            event.getGuild()
                    .ban(target, 0)
                    .queue();

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("SUCCESS")
                    .setDescription("Banned " + target.getEffectiveName() + " from the server indefinitely")
                    .setFooter("Goodbye")
                    .setColor(Color.BLUE);

            channel.sendMessage(embed.build()).queue();

            return;

        }

        String reason = String.join(" ", args.subList(1, args.size()));

        if (args.size() >= 2) {

            event.getGuild()
                    .ban(target, 0, reason)
                    .reason(reason)
                    .queue();

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("SUCCESS")
                    .setDescription("Banned " + target.getEffectiveName() + " from the server permanently for " + reason)
                    .setFooter("Goodbye")
                    .setColor(Color.BLUE);

            channel.sendMessage(embed.build()).queue();
        }
    }


    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public String getHelp() {
        return "Bans a specified user from the server";
    }

    @Override
    public String getUsage() {
        return "<user mention> <reason>";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    public EnumSet<Permission> getPermissions() {
        return EnumSet.of(Permission.BAN_MEMBERS);
    }
}
