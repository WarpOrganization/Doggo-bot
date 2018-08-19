package ovh.kocproz.bot;

import org.javacord.api.event.message.MessageCreateEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * @author KocproZ
 * Created 2018-08-19 at 11:32
 */
class Borker {

    private static List<String> triggers = new LinkedList<>();

    static void init() {
        triggers.add("good boi");
        triggers.add("good boy");
        triggers.add("good dog");
        triggers.add("good doggo");
    }

    static void borkIfNecessary(MessageCreateEvent event) {
        if (shouldBork(event.getMessage().getContent())) {
            event.getChannel().sendMessage("*bork bork*");
        }
    }

    private static boolean shouldBork(String msg) {
        for (String s : triggers) {
            if (msg.equalsIgnoreCase(s))
                return true;
        }
        return false;
    }

}
