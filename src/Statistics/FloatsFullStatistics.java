package src.Statistics;

public class FloatsFullStatistics implements Statistics {
    private long count;
    private double min;
    private double max;
    private double sum;
    private double average;

    public FloatsFullStatistics() {
    }

    @Override
    public void add (Object number) {
        ++count;
        Double temp = (Double) number;
        if (count == 1) {
            average = min = max = sum = temp;
        } else {
            min = min > temp ? temp : min;
            max = max < temp ? temp : max;
            sum += temp;
            average = sum / count;
        }
    }
    @Override
    public void show() {
        if (count > 0) {
            System.out.println("Количество вещественных чисел равно " + count +
                    ", максимальное вещественное число равно " + max +
                    ", минимальное вещественное число равно " + min +
                    ", сумма вещественных чисел равно " + sum +
                    ", среднее значение равно " + average);
        }
    }
}
