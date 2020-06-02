package com.kylelovestoad.kylebot;


import com.kylelovestoad.kylebot.command.CommandManager;
import com.kylelovestoad.kylebot.command.PrefixManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;

public class Listener extends ListenerAdapter {

    private final CommandManager manager = new CommandManager();


    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onReady(ReadyEvent event) {
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());
        LOGGER.info("Default prefix is set to {}", Config.get("default_prefix"));
        LOGGER.info("The owner id is {}", Config.get("owner_id"));
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        User user = event.getAuthor();
        long guildId = event.getGuild().getIdLong();
        String prefix = PrefixManager.getInstance().getGuildPrefix(guildId);
        String raw = event.getMessage().getContentRaw();

        if (user.isBot() || event.isWebhookMessage()) {
            return;
        }


        if (raw.equalsIgnoreCase(prefix + "shutdown")
                && user.getId().equals(Config.get("owner_id"))) {
            LOGGER.info("Shutting down...");
            event.getJDA().shutdown();

            return;
        }

        if (raw.startsWith(prefix)) {
            LOGGER.info(event.getGuild().getName() + "/" + event.getChannel().getName() + " (" + event.getAuthor().getName() + ") " + event.getMessage().getContentRaw());
            manager.handle(event, prefix);
        }
    }
}
