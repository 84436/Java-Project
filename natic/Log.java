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
    public static final Logger l = Logger.getLogger("");
    private static final Level DEFAULT_LEVEL = Level.INFO;

    // Customized datetime format
    // https://docs.oracle.com/javase/10/docs/api/java/text/SimpleDateFormat.html
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss");

    // Customized log format
    private static class CustomFormatter extends Formatter {
        public String format(LogRecord r) {
            return String.format(
                // https://docs.oracle.com/javase/10/docs/api/java/util/Formatter.html
                // 1$   : first argument (index from 1, not 0)
                //   -  : left-align output
                //    7 : width = 7
                "[%1$14s] [%2$7s] (%3$s:%4$s) %5$s\n",
                sdf.format(Date.from(r.getInstant())),
                r.getLevel(),
                r.getSourceClassName(),
                r.getSourceMethodName(),
                r.getMessage()
            );
        }
    }

    /**
     * Initialize the logger (setting up custom log format and level).
     */
    public static void initLogger() {
        l.getHandlers()[0].setFormatter(new CustomFormatter());
        l.setLevel(DEFAULT_LEVEL);
    }

    /**
     * Turns off logging everything below SEVERE
     */
    public static void off() { l.setLevel(Level.SEVERE); }

    /**
     * Turns on logging everything in range [DEFAULT_LEVEL .. SEVERE]
     */
    public static void on() { l.setLevel(DEFAULT_LEVEL); }

    /**
     * A quick reminder on log levels:
     * 500  = FINE
     * 700  = CONFIG
     * 800  = INFO
     * 900  = WARNING
     * 1000 = SEVERE
     */
}
