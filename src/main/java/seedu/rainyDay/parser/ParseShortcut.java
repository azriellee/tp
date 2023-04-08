package seedu.rainyDay.parser;

import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.ShortcutAddCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

//@@author KN-CY
public class ParseShortcut extends Parser {

    public Command generateShortcut(String userInput) throws RainyDayException {
        if (!userInput.contains(" -maps ")) {
            throw new RainyDayException(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }
        String[] tokens = userInput.split(" -maps ");

        // check for > 1 instance of " -maps "
        if (tokens.length > 2) { //
            throw new RainyDayException(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }
        String key = tokens[0];
        String value = tokens[1];

        // ensure that shortcut is a single word
        if (key.contains(" ")) {
            throw new RainyDayException(ErrorMessage.WRONG_SHORTCUT_FORMAT.toString());
        }
        return new ShortcutAddCommand(key, value);
    }
}
