package com.kylelovestoad.kylebot.command.commands;

import com.kylelovestoad.kylebot.Config;
import com.kylelovestoad.kylebot.command.CommandContext;
import com.kylelovestoad.kylebot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.List;

public class ClearCommand implements ICommand {


    @Override
    public void handle(CommandContext ctx) {

        final List<String> args = ctx.getArgs();

        final TextChannel channel = ctx.getChannel();

        try {

            List<Message> messages;

            if (args.size() < 1) {

                // Not putting any args
                final EmbedBuilder embed = new EmbedBuilder();
                embed.setTitle("Imagine not doing the command correctly")
                        .setDescription("Here's how you do it stupid: `" + Config.get("prefix") + this.getName() + " <amount>` ")
                        .setFooter("This is a bruh moment")
                        .setColor(Color.RED);

                channel.sendMessage(embed.build()).queue();

            } else {

                int amount = Integer.parseInt(args.get(0));

                if (amount + 1 > 100) {
                    // If message count is over 100
                    for (int i = amount / 100; i >= 0; i--) {
                        messages = channel.getHistory()
                                .retrievePast(100)
                                .complete();

                        channel.purgeMessages(messages);
                    }

                    messages = channel.getHistory()
                            .retrievePast(amount - ((amount / 100) * 100) + 1)
                            .complete();

                    channel.purgeMessages(messages);

                } else {
                    messages = channel.getHistory()
                            .retrievePast(amount + 1)
                            .complete();

                    channel.purgeMessages(messages);
                }
                channel.sendMessage("Successfully deleted `" + amount + "` messages").queue();
            }
        } catch (NumberFormatException e) {

            // Do Nothing

        } catch (IllegalArgumentException e) {

            if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval limit")) {
                // Too little messages
                final EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Bruh")
                        .setDescription("❌ You can't clear no messages, what are you thinking?!");

                channel.sendMessage(embed.build()).queue();

            } else {
                // Message is two weeks old
                final EmbedBuilder embed = new EmbedBuilder()
                        .setColor(Color.RED)
                        .setTitle("Lmao")
                        .setDescription("❌ Welp, your stuck with these messages, I can't delete messages 2 weeks or older");

                channel.sendMessage(embed.build()).queue();
            }
        }
    }

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public String getHelp() {
        return "Clears a specified number of messages\n" +
                "Usage: `" + Config.get("prefix") + this.getName() + " <amount>`";
    }

    @Override
    public List<String> getAliases() {
        return List.of("delete", "remove");
    }

    @Override
    public List<Permission> getPermissions() {
        return List.of(Permission.MESSAGE_MANAGE);
    }
}



