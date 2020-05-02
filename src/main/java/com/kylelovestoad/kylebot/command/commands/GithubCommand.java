package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.util.List;

import java.awt.*;

public class GithubCommand implements ICommand {
    @Override
    public void handle(CommandContext ctx) {

        TextChannel channel = ctx.getChannel();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("KyleBot GitHub")
                .setDescription("https://github.com/kylelovestoad/KyleBot")
                .setImage("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
                .setColor(Color.BLUE);

        channel.sendMessage(embed.build()).queue();

    }

    @Override
    public String getName() {
        return "github";
    }

    @Override
    public String getHelp() {
        return "Displays the KyleBot GitHub link";
    }

    public List<String> getAliases() {
        return List.of("src", "source", "sourcecode");
    }
}
