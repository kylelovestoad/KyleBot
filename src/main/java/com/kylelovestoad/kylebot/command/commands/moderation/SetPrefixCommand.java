package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import com.kylelovestoad.kylebot.command.PrefixManager;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.EnumSet;
import java.util.List;

public class SetPrefixCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        Long guildId = event.getGuild().getIdLong();

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {
            return;
        }

        String newPrefix = args.get(0);

        channel.sendMessageFormat("Set prefix to `%s`", newPrefix).queue();

        PrefixManager.updatePrefix(guildId, newPrefix);
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
