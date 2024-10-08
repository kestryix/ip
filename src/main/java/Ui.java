import java.util.HashMap;
import java.util.Scanner;

/**
 * The Ui class handles user interactions by accepting input commands and managing the task list.
 * It provides methods for loading, saving, and modifying tasks, and facilitates communication between
 * the user and the task list.
 */
public class Ui {
    public TaskList taskList;

    public Ui() {
        taskList = new TaskList();

        try {
            taskList.loadTaskFromFile();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Matches the user's command with the corresponding task list operation.
     *
     * @param command The user's command as a string.
     * @param commandArguments A HashMap containing command arguments for specific tasks.
     * @return true if the program should continue accepting input, false if the user entered "bye".
     */
    public boolean matchCommand(String command, HashMap<String, String> commandArguments) {
        final String goodByeMessage = "--------------------------------------------\n" +
                "Bye! Hope to see you again soon :)\n" +
                "--------------------------------------------";

        final String unrecognizedCommand = "--------------------------------------------\n" +
                "Unrecognized command!\n" +
                "--------------------------------------------\n";

        switch (command) {
            case "list":
                taskList.listTasks();
                break;

            case "mark":
                try {
                    taskList.markTaskAsDone(commandArguments);
                } catch (TulipTaskException.InvalidTaskIndexException e) {
                    System.out.println("--------------------------------------------");
                    System.out.printf("Invalid task index! Please input a number between 1 and %d \n", taskList.taskList.size());
                    System.out.println("--------------------------------------------");
                }
                break;

            case "unmark":
                try {
                    taskList.markTaskAsNotDone(commandArguments);
                } catch (TulipTaskException.InvalidTaskIndexException e) {
                    System.out.println("--------------------------------------------");
                    System.out.printf("Invalid task index! Please input a number between 1 and %d \n", taskList.taskList.size());
                    System.out.println("--------------------------------------------");
                }
                break;

            case "todo":
                try {
                    taskList.addToDo(commandArguments);
                } catch (TulipTaskException.InvalidTaskDescriptionException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Task description was not given :(");
                    System.out.println("--------------------------------------------");
                }
                break;

            case "deadline":
                try {
                    taskList.addDeadline(commandArguments);
                } catch (TulipTaskException.InvalidTaskDescriptionException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Task description was not given :(");
                    System.out.println("--------------------------------------------");
                } catch (TulipTaskException.InvalidDeadlineException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Task deadline was not given, add a deadline by using /by to indicate task end date!");
                    System.out.println("--------------------------------------------");
                }
                break;

            case "event":
                try {
                    taskList.addEvent(commandArguments);
                } catch (TulipTaskException.InvalidTaskDescriptionException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Task description was not given :(");
                    System.out.println("--------------------------------------------");
                } catch (TulipTaskException.InvalidStartDateException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Event start date was not given, add a start date by using /from to indicate task start date!");
                    System.out.println("--------------------------------------------");
                } catch (TulipTaskException.InvalidEndDateException e) {
                    System.out.println("--------------------------------------------");
                    System.out.println("Event end date was not given, add a end date by using /to to indicate task end date!");
                    System.out.println("--------------------------------------------");
                }
                break;

            case "delete":
                taskList.deleteTask(commandArguments);
                break;

            case "save":
                taskList.saveTaskToFile();
                break;

            case "load":
                taskList.loadTaskFromFile();
                break;

            case "find":
                taskList.findTask(commandArguments);
                break;

            case "bye":
                taskList.saveTaskToFile();
                System.out.println(goodByeMessage);
                return false;

            default:
                System.out.println(unrecognizedCommand);
                break;
        }
        return true;
    }

    /**
     * Accepts and processes commands entered by the user in an interactive loop.
     * The loop continues until the user enters the "bye" command.
     */
    public void commandEntry() {
        HashMap<String, String> commandArguments;
        String input;
        Scanner scanner = new Scanner(System.in);

        boolean isAcceptingInput = true;

        while (isAcceptingInput) {
            input = scanner.nextLine();
            commandArguments = InputParser.parseCommands(input);
            String command = commandArguments.get(InputParser.COMMAND);

            try {
                isAcceptingInput = matchCommand(command, commandArguments);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Displays a welcome message to the user when the program starts.
     */
    public void displayWelcome() {
        String logo = "                                         \n" +
                "--.--     |    o     --.--          |    \n" +
                "  |  .   .|    .,---.  |  ,---.,---.|__/ \n" +
                "  |  |   ||    ||   |  |  ,---|`---.|  \\ \n" +
                "  `  `---'`---'`|---'  `  `---^`---'`   `\n" +
                "                |";

        final String welcomeMessage = "--------------------------------------------\n" +
                "Hello, I'm TulipTask\n" +
                "What can I do for you today?\n" +
                "--------------------------------------------";

        System.out.println(logo);
        System.out.println(welcomeMessage);
    }

    /**
     * Starts the user interface by displaying the welcome message and then accepting commands from the user.
     */
    public void run() {
        displayWelcome();
        commandEntry();
    }
}
