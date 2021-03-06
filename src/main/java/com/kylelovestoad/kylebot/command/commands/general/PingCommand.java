package com.kylelovestoad.kylebot.command.commands.general;

import com.kylelovestoad.kylebot.command.Command;
import com.kylelovestoad.kylebot.command.CommandType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class PingCommand implements Command {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        JDA jda = event.getJDA();

        jda.getRestPing().queue((ping) -> event.getChannel()
                        .sendMessageFormat("Reset ping: %sms\nWS ping: %sms", ping, jda.getGatewayPing()).queue());
    }

    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public String getHelp() {
        return "Shows the current ping from the bot to the discord server.";
    }

    @Override
    public CommandType getType() {
        return CommandType.GENERAL;
    }
}
