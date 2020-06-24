package com.kylelovestoad.kylebot.command;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.EnumSet;
import java.util.List;

/**
 * An interface used to build commands
 */
public interface Command {

    /**
     *
     * @param event The event which is being handled
     * @param args The arguments passed through
     */
    void handle(GuildMessageReceivedEvent event, List<String> args);

    /**
     *
     * @return A {@link String} representing the name of the {@link Command}
     */
    String getName();

    /**
     *
     * @return A {@link String} representing the help clause for the {@link Command}
     */
    String getHelp();

    /**
     *
     * @return A {@link String} representing the usage of the {@link Command}
     */
    String getUsage();

    /**
     *
     * @return A boolean representing if the {@link Command} is only allowed to be used by the owner of the bot
     */
    boolean isOwnerCommand();

    /**
     *
     * @return The type of the {@link Command}
     */
    CommandType getType();

    /**
     *
     * @return A {@link List} of {@link String} representing the alternate names for the {@link Command}
     */
    default List<String> getAliases() {
        return List.of();
    }

    /**
     *
     * @return An {@link EnumSet} of {@link Permission} representing the permissions required to use the {@link Command}
     */
    default EnumSet<Permission> getPermissions() {
        return EnumSet.noneOf(Permission.class);
    }


}
