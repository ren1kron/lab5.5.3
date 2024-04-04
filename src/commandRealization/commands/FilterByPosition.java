package commandRealization.commands;

import commandRealization.Command;
import exceptions.AskExitExecption;
import managers.CollectionManager;
import utility.Asker;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Command 'group_counting_by_creation_date'. Group elements by their creationDate value, display amount of elements in every group
 * @author ren1kron
 */
public class FilterByPosition extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public FilterByPosition(Console console, CollectionManager collectionManager) {
        super("filter_by_position position", "Display elements whose position equals to the specified one");
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
            if (!arguments[1].isEmpty())
                return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

            console.println("* Entering position to compare...");
            var position = Asker.askPosition(console);
            var stringBuilder = new StringBuilder();
            for (var e : collectionManager.getKeyMap().values()) if (e.getPosition().equals(position)) stringBuilder.append(e);
            return new ExecutionResponse(stringBuilder.toString());

        } catch (AskExitExecption e) {
            return new ExecutionResponse(false, "Abort the operation...");
        }
    }
}
