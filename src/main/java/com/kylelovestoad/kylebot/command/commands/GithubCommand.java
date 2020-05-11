package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.awt.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class GithubCommand implements ICommand {

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        String repoDescription;
        String repoHtmlUrl;
        Date repoUpdatedOn;
        String repoOwner = "kylelovestoad";
        String repoName = "KyleBot";
        RepositoryService service = new RepositoryService();
        try {
            Repository repo = service.getRepository(repoOwner, repoName);
            repoDescription = repo.getDescription();
            repoHtmlUrl = repo.getHtmlUrl();
            repoUpdatedOn = repo.getUpdatedAt();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        EmbedBuilder embed = new EmbedBuilder()
                .setTitle("KyleBot Repository")
                .addField("Description: ", repoDescription, false)
                .addField("URL: ", repoHtmlUrl, false)
                .addField("Updated On: ", repoUpdatedOn.toString(), false)
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
