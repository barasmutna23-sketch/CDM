package me.imxtheus.CustomDeathMessagesAndCombat.combat;

public class TimeUtil {

    /**
     * Parses a string like "20s", "5m", "1h" into milliseconds.
     */
    public static long parseTime(String time) {
        time = time.toLowerCase().trim();

        if (time.endsWith("ms")) {
            return Long.parseLong(time.replace("ms", ""));
        } else if (time.endsWith("s")) {
            return Long.parseLong(time.replace("s", "")) * 1000;
        } else if (time.endsWith("m")) {
            return Long.parseLong(time.replace("m", "")) * 60 * 1000;
        } else if (time.endsWith("h")) {
            return Long.parseLong(time.replace("h", "")) * 60 * 60 * 1000;
        } else {
            return Long.parseLong(time); // default in ms
        }
    }
}
