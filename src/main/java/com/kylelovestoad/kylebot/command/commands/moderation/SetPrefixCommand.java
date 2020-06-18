package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import com.kylelovestoad.kylebot.command.SettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.EnumSet;
import java.util.List;

public class SetPrefixCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        long guildId = event.getGuild().getIdLong();

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ You have to actually put a prefix that you want to set")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        String newPrefix = args.get(0);

        channel.sendMessageFormat("Set prefix to `%s`", newPrefix).queue();

        SettingsManager.getInstance().set("prefix", newPrefix ,"guild_id", guildId);
    }

    @Override
    public String getName() {
        return "setprefix";
    }

    @Override
    public String getHelp() {
        return "Sets the prefix for the server";
    }

    @Override
    public String getUsage() {
        return "<prefix>";
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
        return EnumSet.of(Permission.MANAGE_SERVER);
    }
}
