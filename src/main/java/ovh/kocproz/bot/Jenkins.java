package ovh.kocproz.bot;

import com.mashape.unirest.http.Unirest;
import org.javacord.api.event.message.MessageCreateEvent;

/**
 * @author KocproZ
 * Created 2018-08-19 at 11:41
 */
public class Jenkins {

    private static final String DOGGO_MESSAGE_OK = "*bork bork*  `Doggo happily passes note to Mr. Jenkins`";
    private static final String DOGGO_MESSAGE_NO_PERMISSION = "*bork?*  `doggo doesn't trust you enough`";
    private static final String JENKINS_BUILD_TRIGGER = "https://jenkins.b.snet.ovh/job/Warp_engine/build?token=doggoKnows&cause=discord%20request";

    static void checkForJenkinsActions(MessageCreateEvent event) {
        if (event.getMessage().getContent().equalsIgnoreCase("!build")) {
            if (event.getMessage().getUserAuthor().get().getRoles(Main.server).contains(Main.role_koder)) {
                event.getChannel().sendMessage(DOGGO_MESSAGE_OK);
                Unirest.get(JENKINS_BUILD_TRIGGER).asBinaryAsync();
            } else {
                event.getChannel().sendMessage(DOGGO_MESSAGE_NO_PERMISSION);
            }
        }
    }

}
