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
    private static final String JENKINS_PROJECT_URL = "https://jenkins.b.snet.ovh/job/Warp%20Organization/job/warp/";
    private static String username;
    private static String token;

    static void checkForJenkinsActions(MessageCreateEvent event) {
        String message = event.getMessage().getContent();
        String branch;
        if (message.startsWith("!build")) {
            if (message.split(" ").length > 1) {
                branch = message.split(" ")[1];
            } else {
                branch = "master";
            }
            if (event.getMessage().getUserAuthor().get().getRoles(Main.server).contains(Main.role_koder)) {
                event.getChannel().sendMessage(DOGGO_MESSAGE_OK);
//                System.out.println(JENKINS_PROJECT_URL + "job/" + branch + "/build?cause=Discord%20request");
                Unirest.post(JENKINS_PROJECT_URL + "job/" + branch + "/build?cause=Discord%20request")
                        .basicAuth("kocproz", token)
                        .asBinaryAsync();
            } else {
                event.getChannel().sendMessage(DOGGO_MESSAGE_NO_PERMISSION);
            }
        }
    }

    static void setUsername(String username) {
        Jenkins.username = username;
    }

    static void setToken(String token) {
        Jenkins.token = token;
    }

}
