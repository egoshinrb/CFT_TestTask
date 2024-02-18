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
    public void add(Object number) {
        ++count;
        Double num = (Double) number;
        if (count == 1) {
            average = min = max = sum = num;
        } else {
            min = min > num ? num : min;
            max = max < num ? num : max;
            sum += num;
            average = sum / count;
        }
    }
    @Override
    public void show() {
        if (count > 0) {
            System.out.printf("The number of float numbers read is equal to %d, the maximum float number is equal %.4f, the minimum float number is equal %.4f, " +
                    "the total of float numbers is equals %.4f, the average value of float numbers is equals %.4f%n", count, max, min, sum, average);
        } else {
            System.out.println("There are no float numbers");
        }
    }
}
