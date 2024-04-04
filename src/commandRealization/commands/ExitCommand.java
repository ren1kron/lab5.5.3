package commandRealization.commands;

import commandRealization.Command;
import utility.ExecutionResponse;

/**
 * Command 'exit'. Closes the application without saving the collection to csv-file.
 * @author ren1kron
 */
public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "Closes the application without saving the collection to csv-file");
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        return new ExecutionResponse("exit");
    }
}
