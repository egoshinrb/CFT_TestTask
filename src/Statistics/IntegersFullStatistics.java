package src.Statistics;

public class IntegersFullStatistics implements Statistics {
    private long count;
    private long min;
    private long max;
    private long sum;
    private double average;

    public IntegersFullStatistics() {
    }

    @Override
    public void add (Object number) {
        ++count;
        Long temp = (Long) number;
        if (count == 1) {
            average = min = max = sum = temp;
        } else {
            min = min > temp ? temp : min;
            max = max < temp ? temp : max;
            sum += temp;
            average = sum / ((double) count);
        }
    }
    @Override
    public void show() {
        if (count > 0) {
            System.out.println("Количество целых чисел равно " + count +
                    ", максимальное целое число равно " + max +
                    ", минимальное целое число равно " + min +
                    ", сумма целых чисел равно " + sum +
                    ", среднее значение равно " + average);
        }
    }
}
