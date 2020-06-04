package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import com.kylelovestoad.kylebot.command.PrefixManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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

        String channelName = args.get(0);

        if (args.size() == 1) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ That's crazy that you didn't specify whether it was a `voice`, or a `text` channel")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        String channelType = args.get(1);

        if (channelType.equalsIgnoreCase("text")) {
            Objects.requireNonNull(channel.getParent()).createTextChannel(channelName).queue();
            channel.sendMessageFormat("Created a `text` channel called `%s`", channelName).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            return;
        }

        if (channelType.equalsIgnoreCase("voice")) {
            Objects.requireNonNull(channel.getParent()).createVoiceChannel(args.get(0)).queue();
            channel.sendMessageFormat("Created a `voice` channel called `%s`", channelName).queue(message -> message.delete().queueAfter(10, TimeUnit.SECONDS));
            return;
        }

        final EmbedBuilder embed = new EmbedBuilder()
                .setTitle("I cannot believe this")
                .setDescription("❌ `" + channelType + "` is not a valid channel type. Choose `voice` or `text`")
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
