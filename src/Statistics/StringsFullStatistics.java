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
        int lenght = ((String) object).length();
        if (count == 1) {
            min = max = lenght;
        } else {
            min = min > lenght ? lenght : min;
            max = max < lenght ? lenght : max;
        }
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void show() {
        if (count > 0) {
            System.out.println("Количество строк равно " + count + ", максимальная длина строки равна " + max + ", минимальная длина строки равна " + min);
        } else {
            System.out.println("Строки отсутствуют");
        }
    }
}
