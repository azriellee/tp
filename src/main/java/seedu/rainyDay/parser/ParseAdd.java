package seedu.rainyDay.parser;

import seedu.rainyDay.command.AddCommand;
import seedu.rainyDay.command.Command;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.time.LocalDate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author azriellee
public class ParseAdd extends Parser {
    public static final double MAX_AMOUNT = 21474836.47;
    private static final Logger logger = Logger.getLogger(ParseAdd.class.getName());

    public String direction;
    public String description;
    public String category = "miscellaneous";
    public double amount = -1.0;
    public LocalDate date = LocalDate.now();

    public Command addStatement(String userInput) throws RainyDayException {
        try {
            String addInput = userInput.split("\\s+", 2)[1];
            String remainingInformation = returnRemainingInformation(addInput);
            logger.info("obtained mandatory information");
            if (remainingInformation.trim().isEmpty()) {
                logger.info("returning new AddCommand object");
                return new AddCommand(description.trim(), direction, amount, category, date);
            }
            if (!addInput.contains("-c") && !addInput.contains("-date")) {
                logger.info("returning new InvalidCommand object");
                throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
            }
            logger.info("checking for presence of -c");
            if (addInput.contains("-c")) {
                remainingInformation = setCategory(remainingInformation);
            }
            logger.info("checking for presence of -date");
            if (addInput.contains("-date")) {
                date = setDate(remainingInformation);
            }
            logger.info("returning new AddCommand object");
            return new AddCommand(description.trim(), direction, amount, category, date);
        } catch (IndexOutOfBoundsException e) {
            logger.warning("add command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
        } catch (RainyDayException e) {
            throw new RainyDayException(e.getMessage() + ErrorMessage.ADD_FORMAT);
        }
    }

    private String returnRemainingInformation(String input) throws RainyDayException {
        try {
            int flag = 0;
            Pattern pattern = Pattern.compile("-(in|out)\\s+(.+)\\$([\\d.]+)");
            Matcher matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                flag = 1;
                pattern = Pattern.compile("-(in|out)\\s+(.+)\\$([\\d.]+)\\s+(.*)");
                matcher = pattern.matcher(input);
                if (!matcher.matches()) {
                    logger.warning("add command given by user in the wrong format");
                    throw new RainyDayException(ErrorMessage.WRONG_ADD_FORMAT.toString());
                }
            }
            direction = matcher.group(1);
            description = matcher.group(2);
            if (description.contains("-")) {
                logger.warning("unsupported description name");
                throw new RainyDayException(ErrorMessage.UNSUPPORTED_DESCRIPTION_NAME.toString());
            }
            if (description.trim().isEmpty()) {
                logger.warning("unsupported description name");
                throw new RainyDayException(ErrorMessage.EMPTY_DESCRIPTION_NAME.toString());
            }

            double exactAmount = Double.parseDouble(matcher.group(3));
            if (exactAmount > MAX_AMOUNT) {
                throw new RainyDayException(ErrorMessage.INVALID_VALUE.toString());
            }
            exactAmount = (int) (exactAmount * 100);
            if (exactAmount == 0) {
                throw new RainyDayException(ErrorMessage.INVALID_VALUE.toString());
            }
            amount = exactAmount / 100;

            if (flag == 0) {
                logger.info("obtaining mandatory information");
                return "";
            }
            logger.info("obtaining mandatory information");
            return matcher.group(4);
        } catch (Exception e) {
            logger.warning("add command given by user in the wrong format");
            throw new RainyDayException(e.getMessage());
        }
    }

    //@@author lil1n
    private String setCategory(String input) throws RainyDayException {
        int flag = 0;
        Pattern pattern = Pattern.compile("-c\\s+(.+)\\s+-date\\s+(.*)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            flag = 1;
            pattern = Pattern.compile("-c\\s+(.+)");
            matcher = pattern.matcher(input);
            if (!matcher.matches()) {
                logger.warning("add command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.EMPTY_CATEGORY_NAME.toString());
            }
        }
        category = matcher.group(1);
        checkCategoryName(category);
        if (flag == 0) {
            logger.info("obtaining category");
            return "-date " + matcher.group(2);
        }
        logger.info("obtaining category");
        return "";
    }

    //@@author lil1n
    private void checkCategoryName(String category) throws RainyDayException {
        if (category.trim().indexOf("-date") == 0) {
            logger.warning("empty category name");
            throw new RainyDayException(ErrorMessage.EMPTY_CATEGORY_NAME.toString());
        }
        if (category.contains("-date") && category.length() - "-date".length() == category.indexOf("-date")) {
            logger.warning("-date flag provided but no date provided");
            throw new RainyDayException(ErrorMessage.NO_DATE_PROVIDED.toString());
        }
        if (category.contains("-")) {
            logger.warning("unsupported category name");
            throw new RainyDayException(ErrorMessage.UNSUPPORTED_CATEGORY_NAME.toString());
        }
    }
}
