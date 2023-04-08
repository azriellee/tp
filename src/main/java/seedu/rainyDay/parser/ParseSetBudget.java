package seedu.rainyDay.parser;

import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.SetBudgetCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.util.logging.Logger;

//@@author BenjaminPoh
public class ParseSetBudget extends Parser {
    private static final Logger logger = Logger.getLogger(ParseSetBudget.class.getName());

    public Command setUserBudgetGoal(String userInput) throws RainyDayException {
        try {
            double amount = Double.parseDouble(userInput);
            amount = (int) (amount * 100);
            amount /= 100;
            if (amount < 0) {
                throw new IllegalArgumentException();
            }
            return new SetBudgetCommand(amount);
        } catch (Exception e) {
            logger.warning("set budget details provided incorrectly");
            throw new RainyDayException(ErrorMessage.WRONG_SET_BUDGET_FORMAT.toString());
        }
    }
}
