package utility;

public class ImportantChecks {
    public static boolean isNumeric(String string) throws NumberFormatException {
        try {
            Integer.parseInt(string);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}
