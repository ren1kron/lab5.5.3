package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

/**
 * Command 'save'. Saves the collection changes that the user made in the last session
 * @author ren1kron
 */
public class SaveCommand extends Command {
    private final CollectionManager collectionManager;
    public SaveCommand(CollectionManager collectionManager) {
        super("save", "Saves collection to file");
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

        collectionManager.saveMap();
        return new ExecutionResponse("");
    }
}
