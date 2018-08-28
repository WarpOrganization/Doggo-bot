package ovh.kocproz.bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.server.Server;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * @author KocproZ
 * Created 2018-08-18 at 21:56
 */
public class Main {

    static DiscordApi api;
    private static Queue<Message> toRemove = new LinkedList<>();
    static Role role_koder;
    static Role role_admin;
    static Server server;
    private static String token;

    public static void main(String... args) throws Exception {
        loadConfig();
        System.out.println("Loaded config");
        api = new DiscordApiBuilder().setToken(token).login().join();

        Borker.init();
        role_koder = api.getRoleById("446444349991944192").orElseThrow(RuntimeException::new);
        role_admin = api.getRoleById("446444051131138069").orElseThrow(RuntimeException::new);
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

            Biter.tryToBite(event);
            Borker.borkIfNecessary(event);
            Jenkins.checkForJenkinsActions(event);

        });
        System.out.println("Created listeners");

//        System.out.println("Invite: " + api.createBotInvite());

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
    }

    private static void loadConfig() throws FileNotFoundException {
        Scanner s = new Scanner(new File("./config"));
        token = s.nextLine().split(" ")[1];
        Jenkins.setUsername(s.nextLine().split(" ")[1]);
        Jenkins.setToken(s.nextLine().split(" ")[1]);
    }

}
