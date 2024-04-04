package commandRealization.commands;

import commandRealization.Command;
import managers.CollectionManager;
import utility.ExecutionResponse;

import java.util.*;

/**
 * Command 'print_field_descending_salary'. Displays salary values of all elements in descending order
 * @author ren1kron
 */
public class PrintFieldDescendingSalaryCommand extends Command {
    private final CollectionManager collectionManager;
    public PrintFieldDescendingSalaryCommand(CollectionManager collectionManager) {
        super("print_field_descending_salary", "Displays salary values of all elements in descending order");
        this.collectionManager = collectionManager;
    }
    @Override
    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty())
            return new ExecutionResponse(false, "Wrong amount of arguments!\nYou suppose to write: '" + getName() + "'");

//        var stringBuilder = new StringBuilder("* Salaries of workers in descending order:\n");
        var stringBuilder = new StringBuilder("* Salaries of workers in descending order: ");
        collectionManager.getKeyMap().entrySet().stream()
                .sorted(Map.Entry.comparingByValue((w1, w2) -> Float.compare(w2.getSalary(), w1.getSalary())))
                .forEach(entry -> stringBuilder.append(entry.getValue().getSalary())
//                        .append("\n"));
                        .append("; "));
        return new ExecutionResponse(stringBuilder.toString());
//        LinkedList<Worker> workers = (LinkedList<Worker>) collectionManager.getKeyMap().values();
//        workers.sort((o1, o2) -> (int) (o1.getSalary() - o2.getSalary()));
//        return new ExecutionResponse(workers.stream().toString());
//        for (var e : collectionManager.getKeyMap().values()) {
//
//        }
    }
}
