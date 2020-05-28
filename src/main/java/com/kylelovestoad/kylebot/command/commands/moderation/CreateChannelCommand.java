package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class CreateChannelCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("What are you doing?")
                    .setDescription("❌ Imagine not putting any arguments")
                    .setFooter("This is a bruh moment")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();

        }

        if (args.size() == 1) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Wow")
                    .setDescription("❌ That's crazy that you didn't specify whether it was a `voice`, or a `text` channel")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        if (args.get(1).equalsIgnoreCase("text")) {
            Objects.requireNonNull(channel.getParent()).createTextChannel(args.get(0)).queue();
            return;
        }

        if (args.get(1).equalsIgnoreCase("voice")) {
            Objects.requireNonNull(channel.getParent()).createVoiceChannel(args.get(0)).queue();
        }

        final EmbedBuilder embed = new EmbedBuilder()
                .setTitle("I cannot believe this")
                .setDescription("❌ `" + args.get(1) + "` is not a valid channel type. Choose `voice` or `text`")
                .setColor(Color.RED);

        channel.sendMessage(embed.build()).queue();
    }

    @Override
    public String getName() {
        return "createchannel";
    }

    @Override
    public String getHelp() {
        return "Creates a voice or text channel";
    }

    @Override
    public String getUsage() {
        return "<name> <channel type>";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    @Override
    public EnumSet<Permission> getPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }

    @Override
    public List<String> getAliases() {
        return List.of("addchannel", "makechannel");
    }
}
