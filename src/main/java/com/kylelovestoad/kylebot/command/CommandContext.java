package com.kylelovestoad.kylebot.command;

import me.duncte123.botcommons.commands.ICommandContext;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class CommandContext implements ICommandContext {

    private final GuildMessageReceivedEvent event;
    private final List<String> args;

    public CommandContext(GuildMessageReceivedEvent event, List<String> args) {
        this.event = event;
        this.args = args;
    }

    /**
     * @return The current Guild
     */
    @Override
    public Guild getGuild() {
        return this.getEvent().getGuild();
    }

    /**
     * @return The current GuildMessageReceivedEvent
     */
    @Override
    public GuildMessageReceivedEvent getEvent() {
        return this.event;
    }

    /**
     * @return The current arguments
     */
    public List<String> getArgs() {
        return this.args;
    }
}
