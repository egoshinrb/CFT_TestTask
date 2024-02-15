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
            System.out.printf("Количество целых чисел равно %d, максимальное целое число равно %d, минимальное целое число равно %d" +
                    ", сумма целых чисел равна %d, среднее значение равно %.2f%n", count, max, min, sum, average);
        } else {
            System.out.println("Целые числа отсутствуют");
        }
    }
}
