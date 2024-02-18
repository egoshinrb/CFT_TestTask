package src.Statistics;

public class StringsFullStatistics implements Statistics {
    private long count;
    private int min;
    private int max;

    public StringsFullStatistics() {
    }

    @Override
    public void add(Object object) {
        ++count;
        int length = ((String) object).length();
        if (count == 1) {
            min = max = length;
        } else {
            min = min > length ? length : min;
            max = max < length ? length : max;
        }
    }
    @Override
    public void show() {
        if (count > 0) {
            System.out.printf("The number of strings read is equal to %d, the maximum length if string is equal %d, the minimum length of string is equal %d%n", count, max, min);
        } else {
            System.out.println("There are no strings");
        }
    }
}
