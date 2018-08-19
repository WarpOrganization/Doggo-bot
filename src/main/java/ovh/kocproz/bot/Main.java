package ovh.kocproz.bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author KocproZ
 * Created 2018-08-18 at 21:56
 */
public class Main {

    static DiscordApi api;
    static Queue<Message> toRemove = new LinkedList<>();
    static Role role_koder;
    static Server server;

    public static void main(String... args) {
        String token = "NDgwNDY1NDM3MjY0NTc2NTMz.DloNpA.lx4u1Ch0TRv7bJGkZ8vRF5x-RuY";

        api = new DiscordApiBuilder().setToken(token).login().join();

        Borker.init();
        role_koder = api.getRoleById("446444349991944192").orElseThrow(RuntimeException::new);
        server = api.getServerById("446443682124529685").orElseThrow(RuntimeException::new);

        api.addServerMemberJoinListener(Welcomer::welcome);

        api.addMessageCreateListener(event -> {
            if (event.getChannel().getIdAsString().equals("467665249042956289")
                    && event.getMessage().getAuthor().isUser()
                    && !event.getMessage().getAuthor().isYourself()) {
                toRemove.add(event.getMessage());
                event.getChannel().sendMessage(
                        new EmbedBuilder()
                                .setTitle("Doggo Angry")
                                .setDescription("*grrrrrrrr* \n" +
                                        "`You should not do that, it makes doggo angry` \n" +
                                        "*woof! woof!*")
                                .setImage("https://static.kocproz.ovh/dogangry.jpg")
                                .setColor(Color.RED)
                ).thenAccept(m -> toRemove.add(m));
                return;
            }

            Borker.borkIfNecessary(event);
            Jenkins.checkForJenkinsActions(event);

        });

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(5000);
                    if (!toRemove.isEmpty()) {
                        Thread.sleep(5000);
                        toRemove.forEach(Message::delete);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).run();

//        System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());

    }

}
