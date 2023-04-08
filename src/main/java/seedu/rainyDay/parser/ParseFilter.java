package seedu.rainyDay.parser;

import seedu.rainyDay.command.Command;
import seedu.rainyDay.command.FilterCommand;
import seedu.rainyDay.exceptions.ErrorMessage;
import seedu.rainyDay.exceptions.RainyDayException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//@@author ChongQiRong
public class ParseFilter extends Parser {
    private static final Logger logger = Logger.getLogger(ParseFilter.class.getName());

    public Command filterStatement(String userInput) throws RainyDayException {
        String[] action = userInput.split("\\s+", 2);
        if (action.length == 1) {
            logger.warning("No flags in filter");
            throw new RainyDayException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
        }

        int sizeOfFilterFlagAndField = 0;
        String[] flagAndField = action[1].split("\\s");
        for (String s : flagAndField) {
            if (s.equals("-in")) {
                sizeOfFilterFlagAndField += 1;
            } else if (s.equals("-out")) {
                sizeOfFilterFlagAndField += 1;
            } else if (s.equals("-d")) {
                sizeOfFilterFlagAndField += 2;
            } else if (s.equals("-c")) {
                sizeOfFilterFlagAndField += 2;
            } else if (s.equals("-date")) {
                sizeOfFilterFlagAndField += 2;
            }
        }

        if (action[1].startsWith("-in") | action[1].startsWith("-out") | action[1].startsWith("-d")
                | action[1].startsWith("-c") | action[1].startsWith("-date")) {
            ArrayList<String> filterFlagAndField = parseFilterMultipleFlags(action[1], sizeOfFilterFlagAndField);
            return new FilterCommand(filterFlagAndField);
        } else {
            logger.warning("unrecognised input from user!");
            throw new RainyDayException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
        }
    }

    private ArrayList<String> parseFilterMultipleFlags(String input, int sizeOfFilterFlagAndField) throws
            RainyDayException {
        Pattern pattern = Pattern.compile("(?:(-in|-out)\\s*)?\\s*" +
                "(?:(-d)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(?:(-c)\\s+([^\\s-]+(?:\\s+[^\\s-]+)*)\\s*)?" +
                "(.*)$");
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> filterFlagAndField = new ArrayList<>();

        if (matcher.find()) {
            if (matcher.group(6).trim().indexOf("-date") != 0 && !matcher.group(6).equals("")) {
                throw new RainyDayException(ErrorMessage.WRONG_EDIT_FORMAT.toString());
            }
            for (int i = 1; i <= 5; i += 1) {
                if (matcher.group(i) == null) {
                    continue;
                }
                filterFlagAndField.add(matcher.group(i));
            }
            if (matcher.group(6).contains("-date")) {
                try {
                    Pattern patternTwoDate = Pattern.compile("(-date .*\\s)(\\d{1,2}\\/\\d{1,2}\\/\\d{4}.*)$");
                    Matcher matcherTwoDate = patternTwoDate.matcher(matcher.group(6));

                    if (matcherTwoDate.find()) {
                        sizeOfFilterFlagAndField += 1;
                        LocalDate date = setDate(matcherTwoDate.group(1));
                        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dateString = date.format(formatters);
                        filterFlagAndField.add("-date");
                        filterFlagAndField.add(dateString);

                        String secondDate = matcherTwoDate.group(2);
                        secondDate = "-date " + secondDate;
                        date = setDate(secondDate);
                        formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        dateString = date.format(formatters);
                        filterFlagAndField.add(dateString);
                    } else {
                        LocalDate date = setDate(matcher.group(6));
                        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String dateString = date.format(formatters);
                        filterFlagAndField.add("-date");
                        filterFlagAndField.add(dateString);
                    }
                } catch (RainyDayException e) {
                    throw new RainyDayException(e.getMessage() + ErrorMessage.FILTER_FORMAT);
                }
            }
            if (filterFlagAndField.size() != sizeOfFilterFlagAndField) {
                logger.warning("filter command given by user in the wrong format");
                throw new RainyDayException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
            }
            return filterFlagAndField;
        } else {
            logger.warning("filter command given by user in the wrong format");
            throw new RainyDayException(ErrorMessage.WRONG_FILTER_FORMAT.toString());
        }
    }
}
