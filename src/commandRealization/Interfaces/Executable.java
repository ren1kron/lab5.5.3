package commandRealization.Interfaces;

import utility.ExecutionResponse;

/**
 * All executable commands implement this interface
 * @author ren1kron
 */
public interface Executable {
    /**
     * Apply command
     * @param arguments Arguments for applying command
     * @return result of executing command
     */
    ExecutionResponse apply(String[] arguments);
}
