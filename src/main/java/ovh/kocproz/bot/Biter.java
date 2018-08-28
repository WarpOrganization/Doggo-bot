package ovh.kocproz.bot;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * @author KocproZ
 * Created 2018-08-28 at 00:14
 */
class Biter {

    private static final String MESSAGE_NO_PREMISSION = "`Doggo doesn't take orders from you.`";
    private static final String MESSAGE_TARGET_NOT_FOUND = "`Doggo doesn't recognise this person.`";

    static void tryToBite(MessageCreateEvent event) {
        String messageContent = event.getMessage().getContent();
        if (!messageContent.startsWith("!bite")) return;
        if (!event.getMessage().getAuthor().isUser() || event.getMessage().getAuthor().isYourself()) return;
        User author = event.getMessage().getUserAuthor().get();
        if (!author.getRoles(Main.server).contains(Main.role_admin)) {
            event.getChannel().sendMessage(MESSAGE_NO_PREMISSION);
            return;
        }
        User target;
        try {
            target = Main.api.getUserById(messageContent.split(" ")[1]
                    .replace("<@", "").replace(">", "")).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            event.getChannel().sendMessage(MESSAGE_TARGET_NOT_FOUND);
            return;
        }

        bite(event, target, messageContent.replaceFirst("!bite <@\\d+>", ""));
    }

    private static void bite(MessageCreateEvent event, User target, String reason) {
        if (reason.trim().equals("")) {
            event.getChannel().sendMessage("`Doggo needs a reason to do such thing.`");
        } else {
            target.sendMessage(new EmbedBuilder()
                    .setImage("https://static.kocproz.ovh/doggo_bite.jpg")
                    .setColor(Color.RED)
                    .setDescription("`Doggo bites you. Not hard, but still.`\nReason: " + reason));
            event.getChannel().sendMessage("`Doggo came back. It is done.`");
        }
    }

}
