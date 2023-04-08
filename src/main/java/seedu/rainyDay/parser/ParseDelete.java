package seedu.rainyDay.parser;

import seedu.rainyDay.RainyDay;
import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.DeleteCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.util.logging.Logger;

//@@author azriellee
public class ParseDelete extends Parser {
    private static final Logger logger = Logger.getLogger(ParseDelete.class.getName());

    public Command parseDeleteStatement(String userInput) throws RainyDayException {
        String[] tokens = userInput.split("\\s+");
        if (tokens.length != 2) {
            logger.warning("invalid delete index from user");
            throw new RainyDayException(ErrorMessage.NO_DELETE_INDEX.toString());
        }
        int index;
        try {
            index = Integer.parseInt(tokens[1]);
            if (index <= 0) {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            logger.warning("delete index not a valid number");
            throw new RainyDayException(String.format(ErrorMessage.WRONG_DELETE_INDEX.toString(),
                    RainyDay.savedData.getFinancialReport().getStatementCount() + "!"));
        }
        if (RainyDay.savedData.getFinancialReport().getStatementCount() == 0) {
            throw new RainyDayException(ErrorMessage.EMPTY_FINANCIAL_REPORT.toString());
        }
        try {
            if (index > RainyDay.savedData.getFinancialReport().getStatementCount()) {
                throw new IllegalArgumentException();
            }
            return new DeleteCommand(index);
        } catch (Exception e) {
            logger.warning("delete index not a valid number");
            throw new RainyDayException(String.format(ErrorMessage.WRONG_DELETE_INDEX.toString(),
                    RainyDay.savedData.getFinancialReport().getStatementCount() + "!"));
        }
    }
}
