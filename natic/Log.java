package natic;

import java.util.logging.Logger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Level;

/**
 * A basic logger that is meant to be used as an alternative/shorthand for System.out.println.
 * This overrides the default "root" logger at runtime by using custom format and suppress everything below level 800 (INFO).
 */
public class Log {
    // Grab the default "root" logger
    private static final Logger L = Logger.getLogger("");
    private static final Level DEFAULT_LEVEL = Level.INFO;

    // Customized datetime format
    // https://docs.oracle.com/javase/10/docs/api/java/text/SimpleDateFormat.html
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    // Customized log format
    private static class CustomFormatter extends Formatter {
        public String format(LogRecord r) {
            return String.format(
                // https://docs.oracle.com/javase/10/docs/api/java/util/Formatter.html
                // 1$   : first argument (index from 1, not 0)
                //   -  : left-align output
                //    7 : width = 7
                "%1$s [%2$7s] %3$s\n",
                sdf.format(Date.from(r.getInstant())),
                r.getLevel(),
                r.getMessage()
                // getSource*() does not work as expected when wrapped in this Log class. Skip that for now.
            );
        }
    }

    /**
     * Initialize the logger (setting up custom log format and level).
     */
    public static void initLogger() {
        L.getHandlers()[0].setFormatter(new CustomFormatter());
        L.setLevel(DEFAULT_LEVEL);
        L.info("Logger configured");
    }

    /**
     * Turns off logging
     */
    public static void off() { L.setLevel(Level.OFF); }

    /**
     * Turns on logging
     */
    public static void on() { L.setLevel(DEFAULT_LEVEL); }

    /**
     * Log with level = INFO (800).
     * @param message Message to be logged.
     */
    public static void i(String message) { L.info(message); }

    /**
     * Log with level = WARNING (900).
     * @param message Message to be logged.
     */
    public static void w(String message) { L.warning(message); }

    /**
     * Log with level = SEVERE (1000, aka. Error).
     * @param message Message to be logged.
     */
    public static void s(String message) { L.severe(message); }
}
