package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

/**
 * Command 'info'. This displays information about the collection
 * @author ren1kron
 */
public class InfoCommand extends Command {
    private final CollectionManager collectionManager;
    public InfoCommand(CollectionManager collectionManager) {
        super("info", "Display information about the collection");
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

        String initTime = (collectionManager.getLastInitTime() == null) ? "collection hasn't been initialized in this session yet" :
                "Date: " + collectionManager.getLastInitTime().toLocalDate().toString() + " | Time: " + collectionManager.getLastInitTime().toLocalTime().toString();

        String saveTime = (collectionManager.getLastSaveTime() == null) ? "collection hasn't been saved in this session yet" :
                "Date: " + collectionManager.getLastSaveTime().toLocalDate().toString() + " | Time: " + collectionManager.getLastSaveTime().toLocalTime().toString();

        var s = "Info about collection\n";
        s += "Type of collection: " + collectionManager.getKeyMap().getClass() + "\n";
        s += "Last initialization time: " + initTime + "\n";
        s += "Last save time: " + saveTime + "\n";
        s += "Amount of workers in collection: " + collectionManager.getKeyMap().size() + "\n";

        return new ExecutionResponse(s);
    }
}
