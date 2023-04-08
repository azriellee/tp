package seedu.rainyDay.command;

import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

//@@author KN-CY
public class ShortcutViewCommand extends ShortcutCommand {
    private static final Logger logger = Logger.getLogger(ShortcutAddCommand.class.getName());
    private static final String TABLE_BORDER = "" +
            "+------------------------------------+---------------------------------------------------------------+";

    private static final String ACKNOWLEDGE_VIEW_SHORTCUT_COMMAND = "" +
            "|Here are your shortcuts!                                                                            |\n";
    private static final String HEADERS = "" +
            "+------------------------------------+---------------------------------------------------------------+\n" +
            "|Shortcut                            |Mapped Command                                                 |\n";
    private static final String NO_SHORTCUTS = "You do not have any shortcuts configured.";

    public ShortcutViewCommand() {
    }

    private static String getShortcutMapping(String key, String value) {
        String formattedKey;
        if (key.length() > 36) {
            formattedKey = key.substring(0, 33) + "...";
        } else {
            formattedKey = String.format("%s                                    ", key);
            formattedKey = formattedKey.substring(0, 36);
        }

        String formattedValue;
        if (value.length() > 63) {
            formattedValue = key.substring(0, 60) + "...";

        } else {
            formattedValue = String.format("%s                                                               ",
                    value);
            formattedValue = formattedValue.substring(0, 63);
        }

        return '|' + formattedKey + '|' + formattedValue + '|' + System.lineSeparator();
    }

    private static String getCommandsTable(HashMap<String, String> shortcutCommands) {
        String result = "";
        result += TABLE_BORDER + System.lineSeparator() + ACKNOWLEDGE_VIEW_SHORTCUT_COMMAND + HEADERS;
        for (String key : shortcutCommands.keySet()) {
            String value = shortcutCommands.get(key);
            result += getShortcutMapping(key, value);
        }
        result += TABLE_BORDER;
        return result;
    }

    @Override
    public CommandResult execute() {
        setupLogger();
        logger.log(Level.INFO, "starting ShortcutViewCommand.execute()");
        shortcutCommands = savedData.getShortcutCommands();
        if (shortcutCommands.size() == 0) {
            logger.log(Level.INFO, "ShortcutViewCommand.execute() will not print a table as there is no configured"
                    + " shortcuts");
            return new CommandResult(NO_SHORTCUTS);
        }
        String result = getCommandsTable(shortcutCommands);
        logger.log(Level.INFO, "ShortcutViewCommand.execute() has printed the table of shortcuts");
        return new CommandResult(result);
    }

    /**
     * Sets up logger for logging
     */
    @Override
    protected void setupLogger() {
        LogManager.getLogManager().reset();
        logger.setLevel(Level.INFO);
        try {
            FileHandler fileHandler = new FileHandler("./logs/ShortcutViewCommand.log", true);
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            System.out.println("unable to log ShortcutViewCommand class");
            logger.log(Level.SEVERE, "File logger not working.", e);
        }
    }
}
