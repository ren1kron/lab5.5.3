package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

/**
 * Command 'clear'. This command clears collection
 * @author ren1kron
 */
public class ClearCommand extends Command {
    private final CollectionManager collectionManager;
    public ClearCommand(CollectionManager collectionManager) {
        super("clear", "Clears the collection");
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

        collectionManager.clear();
        return new ExecutionResponse("Collection was cleared!");
    }
}
