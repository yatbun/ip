package duke.parser;

import java.util.regex.Pattern;

import duke.commands.Command;
import duke.commands.CreateDeadlineCommand;
import duke.commands.CreateEventCommand;
import duke.commands.CreateTodoCommand;
import duke.commands.DeleteCommand;
import duke.commands.DoneCommand;
import duke.commands.ExitCommand;
import duke.commands.ListCommand;
import duke.data.exception.DukeException;

/**
 * Parsers user input.
 */
public class Parser {
    private static final Pattern TODOS_FORMAT =
            Pattern.compile("^todo\\s.+");

    private static final Pattern DEADLINES_FORMAT =
            Pattern.compile("^deadline\\s.+\\s/by\\s.+");

    private static final Pattern EVENTS_FORMAT =
            Pattern.compile("^event\\s.+\\s/at\\s.+");

    /**
     * Parsers user input into command for execution.
     *
     * @param userInput Full user input string
     * @return The command based on the user input
     */
    public Command parseCommand(String userInput) {
        if (userInput.length() == 0) {
            throw new DukeException("Input cannot be blank");
        } else if (userInput.equals("bye")) {
            return new ExitCommand();
        } else if (userInput.equals("list")) {
            return new ListCommand();
        } else if (userInput.matches("done (\\d+)")) {
            return new DoneCommand(userInput);
        } else if (userInput.matches("delete (\\d+)")) {
            return new DeleteCommand(userInput);
        } else if (userInput.startsWith("todo")) {
            if (!TODOS_FORMAT.matcher(userInput).matches()) {
                throw new DukeException("ToDos format: todo [desc]");
            }
            return new CreateTodoCommand(userInput);
        } else if (userInput.startsWith("deadline")) {
            if (!DEADLINES_FORMAT.matcher(userInput).matches()) {
                throw new DukeException("Deadline format: deadline [desc] /by [date]");
            }
            return new CreateDeadlineCommand(userInput);
        } else if (userInput.startsWith("event")) {
            if (!EVENTS_FORMAT.matcher(userInput).matches()) {
                throw new DukeException("Event format: event [desc] /at [time]");
            }
            return new CreateEventCommand(userInput);
        } else {
            throw new DukeException("Wrong format: please use todo, deadline or event");
        }
    }
}
