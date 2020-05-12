package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.CommandManager;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.util.List;

public class HelpCommand implements ICommand {

    private final CommandManager manager;

    public HelpCommand(CommandManager manager) {
        this.manager = manager;
    }

    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        TextChannel channel = event.getChannel();

        if (args.isEmpty()) {

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("KyleBot Command List")
                    .setColor(Color.BLUE);

            for (CommandCategory categories : CommandCategory.getNonHiddenCategories()) {
                embed.addField(categories.name().substring(0, 1).toUpperCase()
                        + categories.name().substring(1).toLowerCase(), "`" + Config.get("prefix") + this.getName() + " " + categories.name().toLowerCase() + "`", true);
            }

            channel.sendMessage(embed.build()).queue();
            return;
        }

        String search = args.get(0);
        ICommand cmd = manager.getCommand(search);

        // If the command does not exist and the user isn't searching for a category, the bot will send a message
        // saying that the command doesn't exist
        if (cmd == null && !CommandCategory.getNonHiddenCategories().contains(CommandCategory.fromKey(search))) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Actually, what the fuck")
                    .setDescription("❌ That command does not exist. Check your facts idiot.")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();

            return;
        }

        if (args.size() >= 1) {

            EmbedBuilder embed = new EmbedBuilder();

            if (CommandCategory.getNonHiddenCategories().contains(CommandCategory.fromKey(search))) {

                embed.setTitle(search.substring(0, 1).toUpperCase() + search.substring(1))
                        .setColor(Color.BLUE);

                for (ICommand command : manager.filterCommandsByCategory(CommandCategory.fromKey(search))) {
                    embed.addField(Config.get("prefix") + command.getName(), command.getHelp(), false);
                }

            } else {

                assert cmd != null;
                embed.setTitle(cmd.getName().substring(0, 1).toUpperCase() + cmd.getName().substring(1))
                        .addField("Description:", cmd.getHelp(), false)
                        .setColor(Color.BLUE);

                if (cmd.getUsage() != null && !cmd.getAliases().isEmpty()) {
                    embed.addField("Aliases:", String.join(", ", cmd.getAliases()), false);
                }

                if (cmd.getUsage() != null && !cmd.getUsage().isEmpty()) {
                    embed.addField("Usage:", "`" + cmd.getUsage() + "`", false);
                }

                if (cmd.getUsage() != null && !cmd.getPermissions().isEmpty()) {
                    embed.addField("Required Permissions:", "`" + String.join(", ",cmd.getPermissions().toString().replaceAll("[\\[\\]]", "") + "`"), false);
                }
            }

            channel.sendMessage(embed.build()).queue();
        }
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "Shows the help menu";
    }

    @Override
    public String getUsage() {
        return Config.get("prefix") + this.getName() + " [command]";
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.GENERAL;
    }

    @Override
    public List<String> getAliases() {
        return List.of("cmd", "cmdlist", "commandlist", "helpme");
    }
}
