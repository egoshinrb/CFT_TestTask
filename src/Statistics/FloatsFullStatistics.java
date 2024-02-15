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
            min = min > temp ? temp : min; // TODO считаем неверные значения минимума и максимума
            max = max < temp ? temp : max;
            sum += temp;
            average = sum / count;
        }
    }
    @Override
    public void show() {
        if (count > 0) {
            System.out.printf("Количество вещественных чисел равно %d, максимальное вещественное число равно %f, минимальное вещественное число равно %f, " +
                    "сумма вещественных чисел равна %f, среднее значение равно %f%n", count, max, min, sum, average);
        } else {
            System.out.println("Вещественные числа отсутствуют");
        }
    }
}
