package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class GithubCommand implements ICommand {
    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("KyleBot GitHub")
                .setDescription(Config.get("github"))
                .setThumbnail("https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png")
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

    @Override
    public String getUsage() {
        return Config.get("prefix") + this.getName();
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

    public List<String> getAliases() {
        return List.of("src", "source", "sourcecode");
    }
}
