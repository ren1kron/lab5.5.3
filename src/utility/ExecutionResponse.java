package utility;

public class ExecutionResponse {
    private boolean exitCode;
    private String massage;

    public ExecutionResponse(boolean exitCode, String massage) {
        this.exitCode = exitCode;
        this.massage = massage;
    }

    public ExecutionResponse(String massage) {
        this(true, massage);
    }

    public boolean getExitCode() {
        return exitCode;
    }
    public String getMessage() {
        return massage;
    }

    @Override
    public String toString() {
        return exitCode+";"+massage;
    }
}
