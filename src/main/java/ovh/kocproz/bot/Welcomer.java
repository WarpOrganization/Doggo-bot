package ovh.kocproz.bot;

import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.server.member.ServerMemberJoinEvent;

/**
 * @author KocproZ
 * Created 2018-08-19 at 11:19
 */
class Welcomer {

    static void welcome(ServerMemberJoinEvent event) {
        Main.api.getChannelById("446443682124529687").get().asTextChannel().get().sendMessage(
                new EmbedBuilder()
                        .setTitle("Doggo Welcomes " + event.getUser().getName())
                        .setDescription("*bork bork bork bork bork*\n Welcome!!!! \n*bork bork bork bork bork*")
                        .setImage("https://static.kocproz.ovh/happydog.png"));
        event.getUser().addRole(Main.api.getRoleById("446465165156679683").get());
    }

}
