package com.kylelovestoad.kylebot.command.commands.general;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.CommandManager;
import com.kylelovestoad.kylebot.command.ICommand;
import com.kylelovestoad.kylebot.command.PrefixManager;
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

        Long guildId = event.getGuild().getIdLong();

        String prefix = PrefixManager.getInstance().getMap().computeIfAbsent(guildId, PrefixManager::getPrefix);

        if (args.isEmpty()) {

            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("KyleBot Command List")
                    .setColor(Color.BLUE);

            CommandCategory.getNonHiddenCategories().forEach(category ->
                    embed.addField(category.name().substring(0, 1).toUpperCase() + category.name().substring(1).toLowerCase(), "`" + prefix + this.getName() + " " + category.name().toLowerCase() + "`", true));

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

                manager.filterCommandsByCategory(CommandCategory.fromKey(search)).forEach(command ->
                        embed.addField(prefix + command.getName(), command.getHelp(), false));

            } else {

                assert cmd != null;
                embed.setTitle(cmd.getName().substring(0, 1).toUpperCase() + cmd.getName().substring(1))
                        .addField("Description:", cmd.getHelp(), false)
                        .setColor(Color.BLUE);

                if (cmd.getUsage() != null && !cmd.getAliases().isEmpty()) {
                    embed.addField("Aliases:", String.join(", ", cmd.getAliases()), false);
                }

                if (cmd.getUsage() != null) {
                    embed.addField("Usage:", "`" + prefix + cmd.getName() + " " + cmd.getUsage() + "`", false);
                }

                if (cmd.getPermissions() != null && !cmd.getPermissions().isEmpty()) {
                    embed.addField("Required Permissions:", "`" + String.join(", ", cmd.getPermissions().toString().replaceAll("[\\[\\]]", "") + "`"), false);
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
        return " [command]";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
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
