package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class BanCommand implements ICommand {

    @Override
    public void handle(CommandContext ctx) {

        final TextChannel channel = ctx.getChannel();

        final List<String> args = ctx.getArgs();

        final Message message = ctx.getMessage();

        if (args.size() < 1) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Imagine not doing the command correctly")
                    .setDescription("❌ Here's how you do it stupid: `" + Config.get("prefix") + this.getName() + " <user mention> [reason]`")
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

        if (args.size() == 1) {

            ctx.getGuild()
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

            ctx.getGuild()
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
        return "Bans a specified user from the server\n" +
                "Usage: `" + Config.get("prefix") + this.getName() + " <user mention> [reason]`";
    }
    public List<Permission> getPermissions() {
        return List.of(Permission.BAN_MEMBERS);
    }
}
