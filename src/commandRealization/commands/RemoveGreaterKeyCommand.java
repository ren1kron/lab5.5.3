package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Command 'remove_greater_key key'. Removes all elements with key greater than the specified one from collection.
 * @author ren1kron
 */
public class RemoveGreaterKeyCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public RemoveGreaterKeyCommand(Console console, CollectionManager collectionManager) {
        super("remove_greater_key key", "Removes all elements with key greater than the specified one from collection");
        this.console = console;
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
            for (var e : collectionManager.getKeyMap().keySet()) {
                if (e > key) collectionManager.removeByKey(e);
            }
            return new ExecutionResponse("All elements with key greater than specified one has been successfully removed from collection!");
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Invalid key value!");
        }
    }
}
