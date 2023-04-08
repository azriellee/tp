package seedu.rainyDay.modules;

import java.io.InputStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author BenjaminPoh
public class Ui {
    private static final String WELCOME_MESSAGE = "" +
            "Hello from rainyDay!\n" +
            "        __.|.__\n" +
            "    .-\"'..':`..`\"-.\n" +
            "  .' .' .  :  . `. `.\n" +
            " / .   .   :   .   . \\\n" +
            "/_ _._ _.._:_.._ _._ _\\\n" +
            "  '   '    |    '   '\n" +
            "           |\n" +
            "           |\n" +
            "           |\n" +
            "         `='";
    private static final String NO_FILE_DETECTED = "No valid save file detected. Starting with empty financial data.";
    private static Logger UILogger = Logger.getLogger("UILogger.log");

    private final Scanner in;

    //@@author lil1n
    public Ui() {
        this(System.in);
    }

    public Ui(InputStream in) {
        this.in = new Scanner(in);
    }

    public static void printShortCutConfirmation(String shortcut, String actualCommand) {
        System.out.println("Shortcut used: " + shortcut);
        System.out.println("rainyDay will now execute your mapped command: " + actualCommand);
    }

    //@@author BenjaminPoh
    public void printLogo() {
        System.out.println(WELCOME_MESSAGE);
    }

    public void greetUser(String username) {
        System.out.println("Welcome " + username);
    }

    public void noFileExist() {
        System.out.println(NO_FILE_DETECTED);
    }

    public String readUserName() {
        System.out.print("Welcome new user! What is your name?\n");
        System.out.print("> ");
        String username;
        username = in.nextLine().trim();
        while (username.isEmpty()) {
            System.out.print("Very funny, you should not have an empty name!\nWhat is your name?\n");
            System.out.print("> ");
            username = in.nextLine().trim();
        }
        greetUser(username);
        System.out.println("Get started by providing the command \"help\" to learn the functions offered by rainyDay!");
        return username;
    }

    public String readUserCommand() {
        System.out.print("> ");
        String userInput = in.nextLine();
        UILogger.log(Level.INFO, userInput);
        return userInput.trim();
    }

    public void printToUser(String toPrint) {
        System.out.println(toPrint);
    }
}
