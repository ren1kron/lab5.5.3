import commandRealization.commands.*;
import managers.CollectionManager;
import managers.CommandManager;
import managers.DumpManager;
import utility.Runner;
import utility.console.StandardConsole;


public class Main {
    private static final String ENV_KEY = "lab5";
    public static void main(String[] args) {
        var console = new StandardConsole();
//        String fileName = "Worker.csv";
        String fileName = System.getenv(ENV_KEY);
        DumpManager dumpManager = new DumpManager(fileName, console);
        CollectionManager collectionManager = new CollectionManager(dumpManager);
        if (!collectionManager.init()) {
            console.printError("Not valid csv-file: some keys and/or IDs was repeated");
            System.exit(1);
        }

        var commandManager = new CommandManager() {{
            register("help", new HelpCommand(this));
            register("info", new InfoCommand(collectionManager));
            register("show", new ShowCommand(collectionManager));
            register("insert", new InsertCommand(console, collectionManager));
            register("update", new UpdateCommand(console,collectionManager));
            register("remove_key", new RemoveKeyCommand(collectionManager));
            register("clear", new ClearCommand(collectionManager));
            register("save", new SaveCommand(collectionManager));
            register("execute_script", new ExecuteScriptCommand());
            register("exit", new ExitCommand());
            register("remove_lower", new RemoveLowerCommand(console, collectionManager));
            register("replace_if_greater", new ReplaceIfGreaterCommand(console, collectionManager));
            register("remove_greater_key", new ReplaceIfGreaterCommand(console, collectionManager));
            register("group_counting_by_creation_date", new GroupCountingByCreationDateCommand(collectionManager));
            register("filter_by_position", new FilterByPosition(console, collectionManager));
            register("print_field_descending_salary", new PrintFieldDescendingSalaryCommand(collectionManager));
        }};
//        help : вывести справку по доступным командам
//        info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
//        show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
//        insert null {element} : добавить новый элемент с заданным ключом
//        update id {element} : обновить значение элемента коллекции, id которого равен заданному
//        remove_key null : удалить элемент из коллекции по его ключу
//        clear : очистить коллекцию
//        save : сохранить коллекцию в файл
//        execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
//        exit : завершить программу (без сохранения в файл)
//        remove_lower {element} : удалить из коллекции все элементы, меньшие, чем заданный
//        replace_if_greater null {element} : заменить значение по ключу, если новое значение больше старого
//        remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный
//        group_counting_by_creation_date : сгруппировать элементы коллекции по значению поля creationDate, вывести количество элементов в каждой группе
//        filter_by_position position : вывести элементы, значение поля position которых равно заданному
//        print_field_descending_salary : вывести значения поля salary всех элементов в порядке убывания

        new Runner(console, commandManager).interactiveMode();

//        else console.println("Collection successfully downloaded!");

//        dumpManager.readCsv(map);
//        for (var e : collectionManager.getKeyMap().values()) {
//            System.out.println(e.toString());
//            var org = e.getOrganization();
//            System.out.println(org.getFullName() + "; " + org.getAnnualTurnover() + "; " + org.getEmployeesCount());
//        }
//        Worker worker = Asker.askWorker(console);
//        collectionManager.add(worker);
////        for (var e : map.values()) System.out.println(e.toString());
////        dumpManager.writeCsv(map);
//        collectionManager.saveMap();
    }
    // TODO if sth breaks in error add sleep for 20 in "printError" method
}
