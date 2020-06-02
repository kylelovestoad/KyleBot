package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.EnumSet;
import java.util.List;

public class DeleteChannelCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();
        Guild guild = event.getGuild();

        if (args.isEmpty()) {
            channel.delete().queue();
            return;
        }

        String channelName = args.get(0);

        if (args.size() == 1) {
            guild.getChannels().stream().filter(c -> c.getName().equals(channelName)).forEach(c -> c.delete().queue());
            return;
        }

        String categoryName = args.get(1);

        guild.getCategoriesByName(categoryName, true).forEach(category -> category.getChannels().stream().filter(c -> c.getName().equals(args.get(0))).forEach(c -> c.delete().queue()));

    }

    @Override
    public String getName() {
        return "deletechannel";
    }

    @Override
    public String getHelp() {
        return "Deletes any channels with a specified name. Deletes the channel you send the command in if no arguments are present";
    }

    @Override
    public String getUsage() {
        return "<channel name> [category]";
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
    public List<String> getAliases() {
        return List.of("delchannel", "removechannel", "rmchannel");
    }

    @Override
    public EnumSet<Permission> getPermissions() {
        return EnumSet.of(Permission.MANAGE_CHANNEL);
    }
}
