package commandRealization.commands;

import commandRealization.Command;
import exceptions.AskExitExecption;
import managers.CollectionManager;
import models.Worker;
import utility.Asker;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Command 'remove_lower'. Remove from collection all elements smaller than specified one.
 * @author ren1kron
 */
public class RemoveLowerCommand extends Command {
    private final CollectionManager collectionManager;
    private final Console console;
    public RemoveLowerCommand(Console console, CollectionManager collectionManager) {
        super("remove_lower {element}", "Remove from collection all elements smaller than specified one");
        this.collectionManager = collectionManager;
        this.console = console;
    }

    /**
     * Applies command
     * @param arguments Arguments for applying command
     * @return Command status
     */
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

        console.println("* Getting the worker to compare...");
        Worker worker;
        try {
            var key = Asker.askKey(console);
            worker = Asker.askWorker(console, key, collectionManager.getFreeId());
            if (worker == null) throw new NullPointerException();
        } catch (AskExitExecption e) {
            return new ExecutionResponse(false, "Abort the operation...");
        } catch (NullPointerException e) {
            return new ExecutionResponse(false, "This is not a worker! Abort the operation...");
        }
        for (var e : collectionManager.getKeyMap().values()) {
//            if (worker.compareTo(e) < 0) collectionManager.removeByKey(e.getKey());
            if (worker.getSalary() < e.getSalary()) collectionManager.removeByKey(e.getKey());
        }
//        return new ExecutionResponse("All workers with ID lower than specified was deleted!");
        return new ExecutionResponse("All worker's with salary lower than specified was deleted!");
    }
}
