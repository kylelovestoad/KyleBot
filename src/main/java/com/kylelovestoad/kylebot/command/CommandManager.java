package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Bot;
import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.commands.fun.*;
import com.kylelovestoad.kylebot.command.commands.general.GithubCommand;
import com.kylelovestoad.kylebot.command.commands.general.HelpCommand;
import com.kylelovestoad.kylebot.command.commands.general.InfoCommand;
import com.kylelovestoad.kylebot.command.commands.general.PingCommand;
import com.kylelovestoad.kylebot.command.commands.moderation.*;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Contains methods that manage commands
 */
public class CommandManager {


    /**
     * Adds commands to the manager
     */
    public CommandManager() {
        addCommand(new AsshoelCommand());
        addCommand(new BanCommand());
        addCommand(new ClearCommand());
        addCommand(new DeleteChannelCommand());
        addCommand(new CreateChannelCommand());
        addCommand(new ExpandCommand());
        addCommand(new GithubCommand());
        addCommand(new HelpCommand(this));
        addCommand(new InfoCommand());
        addCommand(new MuteCommand());
        addCommand(new KickCommand());
        addCommand(new NoSirCommand());
        addCommand(new OwoifyCommand());
        addCommand(new PingCommand());
        addCommand(new SetPrefixCommand());
        addCommand(new YesSirCommand());
    }

    private final List<Command> commands = new ArrayList<>();

    /**
     * Adds the command, also checks for duplicate commands.
     */
    private void addCommand(Command cmd) {
        boolean duplicateName = this.commands.stream().anyMatch((Command it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (duplicateName) {
            throw new IllegalArgumentException("Duplicate command name found");
        }

        commands.add(cmd);
    }

    /**
     * @return A list of all commands
     */
    public List<Command> getCommands() {
        return commands;
    }

    /**
     * @param search The command name which is being searched
     * @return one command
     */
    public Command getCommand(String search) {
        String searchLowerCase = search.toLowerCase();
        for (Command cmd : this.commands) {
            if (cmd.getName().equals(searchLowerCase) || cmd.getAliases().contains(searchLowerCase)) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * @param type The current category
     * @return A list of commands that are in the category
     */
    public List<Command> filterCommandsByType(CommandType type) {
        List<Command> filteredCommands = new ArrayList<>();
        this.commands.stream()
                .filter(cmd -> cmd.getType().equals(type))
                .forEach(filteredCommands::add);

        return filteredCommands;
    }

    /**
     * @param event The event which is being handled
     */
    public void handle(@NotNull GuildMessageReceivedEvent event, String prefix) {

        // Creates a list that has all words in the message separated by whitespace. Also gets words that are surrounded by quotes.
        String raw = event.getMessage().getContentRaw();
        List<String> split = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(raw.replaceFirst("(?i)" + Pattern.quote(Objects.requireNonNull(prefix)), "").trim());
        while (m.find()) {
            split.add(m.group(1).replace("\"", ""));
        }

        String invoke = split.get(0).toLowerCase();
        Command cmd = this.getCommand(invoke);

        // If the command exists
        if (cmd != null) {

            Guild guild = event.getGuild();
            TextChannel channel = event.getChannel();
            Member member = event.getMember();

            channel.sendTyping().queue();
            List<String> args = split.subList(1, split.size());

            // If the command is an owner command, it checks if the user has the owner id
            if (cmd.isOwnerCommand() && !Objects.requireNonNull(member).getId().equals(Config.get("owner_id"))) {
                return;
            }

            // If the command needs permissions, it checks if the user has the required permissions
            if (!Objects.requireNonNull(member).hasPermission(cmd.getPermissions())) {
                channel.sendMessage("You can't do that shit.").queue();
                return;
            }

            // If the command needs permissions, it checks if the bot has the required permissions
            if (!guild.getSelfMember().hasPermission(cmd.getPermissions())) {
                channel.sendMessage("I can't do that shit.").queue();
                return;
            }

            // This executes the command
            cmd.handle(event, args);
        }
    }
}
