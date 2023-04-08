package seedu.rainyDay.parser;

import seedu.rainyDay.RainyDay;
import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.IgnoreCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.util.logging.Logger;

//@@author azriellee
public class ParseIgnore extends Parser {
    private static final Logger logger = Logger.getLogger(ParseIgnore.class.getName());

    public Command ignoreStatement(String userInput) throws RainyDayException {
        String[] tokens = userInput.split("\\s+", 2);
        if (tokens.length < 2) {
            logger.warning("no ignore index from user");
            throw new RainyDayException(ErrorMessage.WRONG_IGNORE_FORMAT.toString());
        }
        if (RainyDay.savedData.getFinancialReport().getStatementCount() == 0) {
            throw new RainyDayException(ErrorMessage.EMPTY_FINANCIAL_REPORT.toString());
        }
        try {
            int index = Integer.parseInt(tokens[1]);
            if (index > RainyDay.savedData.getFinancialReport().getStatementCount()) {
                throw new IllegalArgumentException();
            }
            if (index <= 0) {
                throw new IllegalArgumentException();
            }
            return new IgnoreCommand(index, tokens[0]);
        } catch (Exception e) {
            logger.warning("ignore index provided incorrectly");
            throw new RainyDayException(String.format(ErrorMessage.WRONG_IGNORE_INDEX.toString(),
                    RainyDay.savedData.getFinancialReport().getStatementCount() + "!"));
        }
    }
}
