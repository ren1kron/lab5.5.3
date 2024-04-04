package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

/**
 * Command 'show'. Displays all elements of collection
 * @author ren1kron
 */
public class ShowCommand extends Command {
    private final CollectionManager collectionManager;
    public ShowCommand(CollectionManager collectionManager) {
        super("show", "Displays all elements of collection");
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        return new ExecutionResponse(collectionManager.toString());
    }
}
