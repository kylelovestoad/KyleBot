package com.kylelovestoad.kylebot.command;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.commands.fun.*;
import com.kylelovestoad.kylebot.command.commands.general.*;
import com.kylelovestoad.kylebot.command.commands.moderation.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandManager {


    /**
     * Adds commands to the manager
     */
    public CommandManager() {
        addCommand(new AsshoelCommand());
        addCommand(new BanCommand());
        addCommand(new ClearCommand());
        addCommand(new CreateChannelCommand());
        addCommand(new ExpandCommand());
        addCommand(new GithubCommand());
        addCommand(new HelpCommand(this));
        addCommand(new InfoCommand());
        addCommand(new KickCommand());
        addCommand(new NoSirCommand());
        addCommand(new OwoifyCommand());
        addCommand(new PingCommand());
        addCommand(new YesSirCommand());
    }

    private final List<ICommand> commands = new ArrayList<>();

    /**
     * Adds the command, also checks for duplicate commands.
     */
    private void addCommand(ICommand cmd) {
        boolean duplicateName = this.commands.stream().anyMatch((ICommand it) -> it.getName().equalsIgnoreCase(cmd.getName()));

        if (duplicateName) {
            throw new IllegalArgumentException("Duplicate command name found");
        }

        commands.add(cmd);
    }

    /**
     * @return A list of all commands
     */
    public List<ICommand> getCommands() {
        return commands;
    }

    /**
     * @param search The command name which is being searched
     * @return one command
     */
    public ICommand getCommand(String search) {
        String searchLowerCase = search.toLowerCase();
        for (ICommand cmd : this.commands) {
            if (cmd.getName().equals(searchLowerCase) || cmd.getAliases().contains(searchLowerCase)) {
                return cmd;
            }
        }

        return null;
    }

    /**
     * @param category The current category
     * @return A list of commands that are in the category
     */
    public List<ICommand> filterCommandsByCategory(CommandCategory category) {
        List<ICommand> filteredCommands = new ArrayList<>();
        this.commands.stream()
                .filter(cmd -> cmd.getCategory().equals(category))
                .forEach(filteredCommands::add);

        return filteredCommands;
    }

    /**
     * @param event The event which is being handled
     */
    public void handle(@NotNull GuildMessageReceivedEvent event) {

        // Creates a list that has all words in the message separated by whitespace. Also gets words that are surrounded by quotes.
        List<String> split = new ArrayList<>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(event.getMessage().getContentRaw()
                .replaceFirst("(?i)" + Pattern.quote(Objects.requireNonNull(Config.get("prefix"))), "").trim());
        while (m.find()) {
            split.add(m.group(1).replace("\"", ""));
        }

        String invoke = split.get(0).toLowerCase();
        ICommand cmd = this.getCommand(invoke);

        if (cmd != null) {

            event.getChannel().sendTyping().queue();
            List<String> args = split.subList(1, split.size());

            // If the command is an owner command, it checks if the user has the owner id
            if (cmd.isOwnerCommand() && !Objects.requireNonNull(event.getMember()).getId().equals(Config.get("owner_id"))) {
                return;
            }

            // If the command needs permissions, it checks if the user has the required permissions
            if (!Objects.requireNonNull(event.getMember()).hasPermission(cmd.getPermissions())) {
                event.getChannel().sendMessage("You can't do that shit.").queue();
                return;
            }

            // If the command needs permissions, it checks if the bot has the required permissions
            if (!event.getGuild().getSelfMember().hasPermission(cmd.getPermissions())) {
                event.getChannel().sendMessage("I can't do that shit.").queue();
                return;
            }

            // This executes the command
            cmd.handle(event, args);
        }
    }
}
