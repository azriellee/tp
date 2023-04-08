package seedu.rainyDay.command;

import org.junit.jupiter.api.Test;
import seedu.rainyDay.data.FinancialReport;
import seedu.rainyDay.data.FinancialStatement;
import seedu.rainyDay.data.MonthlyExpenditures;
import seedu.rainyDay.data.SavedData;
import seedu.rainyDay.data.UserData;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShortcutViewCommandTest {
    private static final String NO_SHORTCUTS = "You do not have any shortcuts configured.";
    private static final String TABLE_BORDER = "" +
            "+------------------------------------+---------------------------------------------------------------+";

    private static final String ACKNOWLEDGE_VIEW_SHORTCUT_COMMAND = "" +
            "|Here are your shortcuts!                                                                            |\n";
    private static final String HEADERS = "" +
            "+------------------------------------+---------------------------------------------------------------+\n" +
            "|Shortcut                            |Mapped Command                                                 |\n";

    private static final String KEY_VALUE_ENTRY = "|key                                 |value         " +
            "                                                 |" + System.lineSeparator();
    ArrayList<FinancialStatement> statements = new ArrayList<>();
    FinancialReport financialReport = new FinancialReport(statements);
    HashMap<Integer, Double> expenditures = new HashMap<>();
    MonthlyExpenditures monthlyExpenditures = new MonthlyExpenditures(expenditures);
    SavedData savedData = new SavedData(financialReport);
    UserData userData = new UserData(savedData, monthlyExpenditures);


    @Test
    public void execute_contentMatch() {
        HashMap<String, String> shortcutCommands = savedData.getShortcutCommands();
        shortcutCommands.put("key", "value");

        ShortcutViewCommand shortcutViewCommand = new ShortcutViewCommand();
        shortcutViewCommand.setData(userData);
        String expectedOutput = TABLE_BORDER + System.lineSeparator() +
                ACKNOWLEDGE_VIEW_SHORTCUT_COMMAND + HEADERS + KEY_VALUE_ENTRY + TABLE_BORDER;

        System.out.println(expectedOutput);
        System.out.println(shortcutViewCommand.execute().output);

        assertEquals(expectedOutput, shortcutViewCommand.execute().output);
    }

    @Test
    public void execute_noShortcuts_contentMatch() {
        ShortcutViewCommand shortcutViewCommand = new ShortcutViewCommand();
        shortcutViewCommand.setData(userData);

        assertEquals(NO_SHORTCUTS, shortcutViewCommand.execute().output);

    }


}
