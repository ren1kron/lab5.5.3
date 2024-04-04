package utility;

import managers.CommandManager;
import utility.console.Console;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Runner {
    private final Console console;
    private final CommandManager commandManager;
    private final List<String> scriptStack = new ArrayList<>();
    private int lengthRecursion = -1;

    public Runner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }
    /**
     * Interactive mode. Used for direct input.
     */
    public void interactiveMode() {
        try {
            ExecutionResponse commandStatus;
            String[] userCommand = {"",""};

            while (true) {
                console.prompt();
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();

                commandStatus = runCommand(userCommand);

                if (commandStatus.getMessage().equals("exit")) break;
                console.println(commandStatus.getMessage());
            }
        } catch (NoSuchElementException e) {
            try (Scanner scanner = new Scanner(System.in)) {
                console.printError("User input was not found! Attempt to create a new input stream...");
                scanner.nextLine();
            } catch (NoSuchElementException emergensyExit) {
                console.printError("Attempt failed!");
//                console.printError("Saving data to file...");
                console.print("Closing application...");
//                runCommand(new String[]{"save", ""}); // we can add it if we want to
                runCommand(new String[]{"exit", ""});
                return;
            }
            interactiveMode();
        } catch (IllegalStateException e) {
            console.printError("Unexpected error!");
        }
    }

    /**
     * Script mode. Used for applying commands from file.
     * @param argument Name of inserted script
     * @return can script be applied or not
     */
    public ExecutionResponse scriptMode(String argument) {
        String[] userCommand = {"", ""};
        StringBuilder executionOutput = new StringBuilder();

        if (!new File(argument).exists()) return new ExecutionResponse(false, "This file does not exist!");
        if (!Files.isReadable(Paths.get(argument))) return new ExecutionResponse(false, "You don't have access to this file!");

        scriptStack.add(argument);
        try (Scanner scriptScanner = new Scanner(new File(argument))) {

            ExecutionResponse commandStatus;

            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                userCommand = (console.readln().trim() + " ").split(" ", 2);
                userCommand[1] = userCommand[1].trim();
                while (console.isCanReadln() && userCommand[0].isEmpty()) {
                    userCommand = (console.readln().trim() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                }
                executionOutput.append(console.getPrompt())
                        .append(String.join(" ", userCommand))
                        .append("\n");
                var needLaunch = true;
                if (userCommand[0].equals("execute_script")) {
                    needLaunch = checkRecursion(userCommand[1], scriptScanner);
                }

                commandStatus = needLaunch ? runCommand(userCommand) : new ExecutionResponse("The max size of recursion has been exceeded");
                if (userCommand[0].equals("execute_script")) console.selectFileScanner(scriptScanner);
                executionOutput.append(commandStatus.getMessage()).append("\n");
            } while (commandStatus.getExitCode() && !commandStatus.getMessage().equals("exit") && console.isCanReadln());

            console.selectConsoleScanner();
            if (!commandStatus.getExitCode() && !(userCommand[0].equals("execute_script") && !userCommand[1].isEmpty())) {
                executionOutput.append("Inspect script for the correctness of data entry!\n");
            }

            return new ExecutionResponse(commandStatus.getExitCode(), executionOutput.toString());
        } catch (FileNotFoundException exception) {
            return new ExecutionResponse(false, "Script file was not found!");
        } catch (NoSuchElementException exception) {
            return new ExecutionResponse(false, "Script file is empty!");
        } catch (IllegalStateException exception) {
            console.printError("Unexpected error!");
            System.exit(0);
        } finally {
            scriptStack.remove(scriptStack.size() - 1);
        }
        return new ExecutionResponse("");
    }

    /**
     * Inspect whether the script creates recursion
     * @param argument Name of applied script
     * @return can script be applied or not
     */
    private boolean checkRecursion(String argument, Scanner scriptScanner) {
        var recStart = -1;
        var i = 0;
        for (String script : scriptStack) {
            i++;
            if (argument.equals(script)) {
                if (recStart < 0) recStart = i;
                if (lengthRecursion < 0) {
                    console.selectConsoleScanner();
                    console.println("Recursion was detected! Enter max size of recursion (0..500)");
                    while (lengthRecursion < 0 || lengthRecursion > 500) {
                        try { console.print("> "); lengthRecursion = Integer.parseInt(console.readln().trim()); } catch (NumberFormatException e) { console.println("длина не распознана"); }
                    }
                    console.selectFileScanner(scriptScanner);
                }
                if (i > recStart + lengthRecursion || i > 500)
                    return false;
            }
        }
        return true;
    }

    /**
     * Runs the command.
     * @param userCommand Running command
     * @return running code
     */
    private ExecutionResponse runCommand(String[] userCommand) {
        if (userCommand[0].isEmpty()) return new ExecutionResponse("");
        var command = commandManager.getCommands().get(userCommand[0]);

        if (command == null) return new ExecutionResponse(false, "Command " + userCommand[0] + " was not found. Enter 'help' for help");

        if (userCommand[0].equals("execute_script")) {
            ExecutionResponse tmp = commandManager.getCommands().get("execute_script").apply(userCommand);
            if (!tmp.getExitCode()) return tmp;
            ExecutionResponse tmp2 = scriptMode(userCommand[1]);
            return new ExecutionResponse(tmp2.getExitCode(), tmp.getMessage() + "\n" + tmp2.getMessage().trim());
        }
        return command.apply(userCommand);
    }
}
