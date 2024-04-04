package commandRealization.commands;

import commandRealization.Command;
import exceptions.AskExitExecption;
import managers.CollectionManager;
import models.Worker;
import utility.Asker;
import utility.ExecutionResponse;
import utility.console.Console;

/**
 * Command 'update id {element}'. This command updates element with inserted id
 * @author ren1kron
 */
public class UpdateCommand extends Command {
    private final Console console;
    private final CollectionManager collectionManager;
    public UpdateCommand(Console console, CollectionManager collectionManager) {
        super("update id {element}", "Update element of collection with inserted id");
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


            var id = Integer.parseInt(arguments[1]);

            var oldWorker = collectionManager.byId(id);
            if (oldWorker == null || !collectionManager.isContain(oldWorker)) return new ExecutionResponse(false, "Worker with the specified ID does not exist!");
//            if ((collectionManager.byId(id)) == null) return new ExecutionResponse(false, "Worker with the specified ID does not exist!");
            var key = oldWorker.getKey();
            console.println("* Enter new Worker: ...");
            Worker worker = Asker.askWorker(console, key, id);

            if (worker != null && worker.validate()) {
                collectionManager.removeByKey(key);
                collectionManager.add(worker);
                return new ExecutionResponse("Worker with inserted id was successfully updated!");
            } else return new ExecutionResponse(false, "Fields of inserted oldWorker are invalid. Worker wasn't updated!");
        } catch (AskExitExecption e) {
            return new ExecutionResponse(false, "Abort the operation...");
        } catch (NumberFormatException e) {
            return new ExecutionResponse(false, "Invalid ID value!");
        }
    }
}
