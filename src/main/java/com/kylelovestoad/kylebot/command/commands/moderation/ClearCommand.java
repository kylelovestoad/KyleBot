package com.kylelovestoad.kylebot.command.commands.moderation;

import com.kylelovestoad.kylebot.command.CommandCategory;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ClearCommand implements ICommand {


    @Override
    public void handle(GuildMessageReceivedEvent event, List<String> args) {

        final TextChannel channel = event.getChannel();

        List<Message> messages;

        if (args.isEmpty()) {

            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("What are you doing?")
                    .setDescription("❌ Imagine not putting any arguments")
                    .setFooter("This is a bruh moment")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args.get(0));
        } catch (NumberFormatException e) {
            return;
        }

        if (amount + 1 <= 0) {
            final EmbedBuilder embed = new EmbedBuilder()
                    .setTitle("Hey asshole")
                    .setDescription("❌ You can't clear less than 0 messages")
                    .setFooter("rarted")
                    .setColor(Color.RED);

            channel.sendMessage(embed.build()).queue();
            return;
        }

        if (amount + 1 < 100) {

            messages = channel.getHistory()
                    .retrievePast(amount + 1)
                    .complete();

            List<Message> deletableMessages = messages.stream()
                    .filter(m -> (!m.getTimeCreated().isAfter(
                            OffsetDateTime.now().plus(2, ChronoUnit.WEEKS)
                    )))
                    .collect(Collectors.toList());
            channel.purgeMessages(deletableMessages);

        } else {
            // If message count is over 100
            for (int i = amount / 100; i >= 0; i--) {
                messages = channel.getHistory()
                        .retrievePast(100)
                        .complete();

                List<Message> deletableMessages = messages.stream()
                        .filter(m -> (!m.getTimeCreated().isAfter(
                                OffsetDateTime.now().plus(2, ChronoUnit.WEEKS)
                        )))
                        .collect(Collectors.toList());

                channel.purgeMessages(deletableMessages);


            }

            messages = channel.getHistory()
                    .retrievePast(amount - ((amount / 100) * 100) + 1)
                    .complete();

            List<Message> deletableMessages = messages.stream()
                    .filter(m -> (m.getTimeCreated().isAfter(
                            OffsetDateTime.now().plus(2, ChronoUnit.WEEKS)
                    )))
                    .collect(Collectors.toList());
            channel.purgeMessages(deletableMessages);

        }

        channel.sendMessageFormat("Successfully deleted `%d` messages", amount).queue((message -> message.delete().queueAfter(5, TimeUnit.SECONDS)));
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Clears a specified number of messages";
    }

    @Override
    public String getUsage() {
        return "<amount>";
    }

    @Override
    public boolean isOwnerCommand() {
        return false;
    }

    @Override
    public CommandCategory getCategory() {
        return CommandCategory.MODERATION;
    }

    @Override
    public List<String> getAliases() {
        return List.of("delete", "remove", "purge");
    }

    @Override
    public EnumSet<Permission> getPermissions() {
        return EnumSet.of(Permission.MESSAGE_MANAGE);
    }
}



