package commandRealization.commands;

import commandRealization.Command;
import exceptions.AskExitExecption;
import managers.CollectionManager;
import models.Worker;
import utility.Asker;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Command 'replace_if_greater key {element}'. Replaces element with specified key if new element bigger than old one
 * @author ren1kron
 */
public class ReplaceIfGreaterCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public ReplaceIfGreaterCommand(Console console, CollectionManager collectionManager) {
        super("replace_if_greater key {element}", "Replaces element with specified key if new element bigger than old one");
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
            if (arguments[1].isEmpty()) return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");


            var key = Integer.parseInt(arguments[1]);

            var oldWorker = collectionManager.byKey(key);
            if (oldWorker == null || !collectionManager.isContain(oldWorker)) return new ExecutionResponse(false, "Worker with the specified key does not exist. If you want to add newWorker with this key anyway, use command 'insert key {element}");
            var id = oldWorker.getId();
            console.println("* Enter new Worker: ...");
            Worker newWorker = Asker.askWorker(console, key, id);

            if (newWorker != null && newWorker.validate() && (newWorker.getSalary() > oldWorker.getSalary())) {
                collectionManager.removeByKey(key);
                collectionManager.add(newWorker);
                return new ExecutionResponse("Worker with inserted id was successfully updated!");
            } else return new ExecutionResponse(false, "Fields of inserted oldWorker are invalid. Worker wasn't updated!");
        } catch (AskExitExecption e) {
            return new ExecutionResponse(false, "Abort the operation...");
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Invalid key value!");
        }
    }
}
