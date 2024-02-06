package src;

public class NumbersParser {
    private NumbersParser() {}

    public static boolean isLong(String s) {
        try {
            Long.parseLong(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }


    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }

        return true;
    }
}
