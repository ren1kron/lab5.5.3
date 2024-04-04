package commandRealization.commands;

import commandRealization.Command;
import utility.ExecutionResponse;

/**
 * Reads and runs script from specified file. Script must contain commands in the same way user enter them in command line
 * @author ren1kron
 */
public class ExecuteScriptCommand extends Command {

    public ExecuteScriptCommand() {
        super("execute_script file_name", "Reads and runs script from specified file");

    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        return new ExecutionResponse("Executing script '" + arguments[1] + "'...");
    }
}
