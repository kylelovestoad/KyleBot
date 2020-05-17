package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class ExpandCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        if (args.isEmpty()) {
            return;
        }

        event.getChannel().sendMessage(String.join(" ", args.subList(0,args.size())).replace("", " ").trim()).queue();
    }

    @Override
    public String getName() {
        return "expand";
    }

    @Override
    public String getHelp() {
        return "expands text that you input";
    }

    @Override
    public String getUsage() {
        return " <text>";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.FUN;
    }
}
