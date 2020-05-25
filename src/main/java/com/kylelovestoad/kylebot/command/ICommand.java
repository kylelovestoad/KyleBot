package com.kylelovestoad.kylebot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.EnumSet;
import java.util.List;

public interface ICommand {

    void handle(GuildMessageReceivedEvent event, List<String> args);

    /**
     * @return A String representing the name of the command
     */
    String getName();

    /**
     * @return A String representing the help description of the command
     */
    String getHelp();

    /**
     *
     * @return A String representing the usage for the command
     */
    String getUsage();

    boolean isOwnerCommand();

    /**
     *
     * @return A CommandCategory representing the category that the command was placed in
     */
    CommandCategory getCategory();

    /**
     * @return A List of Strings which represent the alternate names for the command
     */
    default List<String> getAliases() {
        return List.of();
    }

    /**
     * @return A List of Permissions which represent the permissions needed to execute the command
     */
    default EnumSet<Permission> getPermissions() {
        return EnumSet.noneOf(Permission.class);
    }
}
