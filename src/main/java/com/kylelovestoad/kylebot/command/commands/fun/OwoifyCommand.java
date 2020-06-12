package com.kylelovestoad.kylebot.command.commands.fun;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class OwoifyCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setDescription("❌ Very awesome that you didn't put text to owoify")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        final String p;
        final String s;

        int rand0 = (int) (Math.random()*6);
        int rand1 = (int) (Math.random()*7);

        switch (rand0) {
            case 0 -> p = "Huohhhh.";
            case 1 -> p = "OwO";
            case 2 -> p = "UwU";
            case 3 -> p = "Haiiii!";
            case 4 -> p = "Hiiiiii!";
            case 5 -> p = "H-Hewwo?";
            default -> {
                return;
            }

        }

        switch (rand1) {
            case 0 -> s = "XD";
            case 1 -> s = "=w=";
            case 2 -> s = "^-^";
            case 3 -> s = ";-;";
            case 4 -> s = "x3";
            case 5 -> s = ":3";
            case 6 -> s = ">w<";
            default -> {
                return;
            }

        }


        channel.sendMessage(p + " " + String.join(" ", args.subList(0,args.size())).replaceAll("[lr]", "w") + " " + s).queue();
    }

    @Override
    public String getName() {
        return "owoify";
    }

    @Override
    public String getHelp() {
        return "Owoifies text";
    }

    @Override
    public String getUsage() {
        return "<text>";
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
