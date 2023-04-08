package seedu.rainyDay.parser;

import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.ViewCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author BenjaminPoh
public class ParseView extends Parser {
    private static final Logger logger = Logger.getLogger(ParseView.class.getName());

    public Command generateReport(String input) throws RainyDayException {
        input = input.substring(4).trim();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now();
        if (input.equals("")) {
            startDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);
            endDate = startDate.plusMonths(1);
            endDate = endDate.minusDays(1);
            return new ViewCommand(startDate, endDate, false, false);
        }
        if (input.equals("-sort")) {
            startDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);
            endDate = startDate.plusMonths(1);
            endDate = endDate.minusDays(1);
            return new ViewCommand(startDate, endDate, true, false);
        }
        Pattern pattern = Pattern.compile("^(-all)?\\s*((-sort)?)\\s*$");
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            try {
                boolean sortRequired = matcher.group(2).equals("-sort");
                startDate = LocalDate.of(1, 1, 1);
                endDate = LocalDate.of(9999, 12, 31);
                return new ViewCommand(startDate, endDate, sortRequired, true);
            } catch (Exception e) {
                logger.warning("view command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            }
        }

        pattern = Pattern.compile("^(\\d{1,2})([dwmy])\\s*((-sort)?)\\s*$");
        matcher = pattern.matcher(input);
        if (matcher.matches()) {
            try {
                logger.info("obtaining relevant data");
                int minusAmount = Integer.parseInt(matcher.group(1));
                String dateType = matcher.group(2);
                boolean sortRequired = matcher.group(3).equals("-sort");
                if (dateType.equals("d") && minusAmount < 32) {
                    startDate = startDate.minusDays(minusAmount);
                    return new ViewCommand(startDate, endDate, sortRequired, false);
                }
                if (dateType.equals("w") && minusAmount < 5) {
                    startDate = startDate.minusWeeks(minusAmount);
                    return new ViewCommand(startDate, endDate, sortRequired, false);
                }
                if (dateType.equals("m") && minusAmount < 13) {
                    startDate = startDate.minusMonths(minusAmount);
                    return new ViewCommand(startDate, endDate, sortRequired, false);
                }
                if (dateType.equals("y") && minusAmount < 11) {
                    startDate = startDate.minusYears(minusAmount);
                    return new ViewCommand(startDate, endDate, sortRequired, false);
                }
                logger.warning("view command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            } catch (Exception e) {
                logger.warning("view command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.WRONG_VIEW_FORMAT.toString());
            }
        } else {
            logger.warning("view command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_VIEW_FORMAT.toString());
        }
    }
}
