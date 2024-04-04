package commandRealization.commands;

import commandRealization.Command;
import managers.CommandManager;
import utility.ExecutionResponse;

import java.util.stream.Collectors;

/**
 * Command 'help'. This command displays help for available commands
 * @author ren1kron
 */
public class HelpCommand extends Command {
    private final CommandManager commandManager;
    public HelpCommand(CommandManager commandManager) {
        super("help", "Displays help for available commands");
        this.commandManager = commandManager;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        return new ExecutionResponse(commandManager.getCommands().values().stream().map(command -> String.format(" %-35s%-1s%n", command.getName(), command.getDescription())).collect(Collectors.joining("\n")));
    }
}
