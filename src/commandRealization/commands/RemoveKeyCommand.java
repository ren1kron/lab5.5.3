package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

/**
 * Command 'remove_key'. Removes element from collection by its key.
 * @author ren1kron
 */
public class RemoveKeyCommand extends Command {
    private final CollectionManager collectionManager;
    public RemoveKeyCommand(CollectionManager collectionManager) {
        super("remove_key key", "Removes element from collection by key");
        this.collectionManager = collectionManager;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty())
                return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

            var key = Integer.parseInt(arguments[1].trim());
            if (!collectionManager.getKeyMap().containsKey(key))
                return new ExecutionResponse(false, "Worker with this key already doesn't exist!");
            collectionManager.removeByKey(key);
            return new ExecutionResponse("Worker successfully removed");
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Invalid key value!");
        }
    }
}
