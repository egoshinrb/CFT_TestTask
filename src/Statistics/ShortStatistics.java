package src.Statistics;

public class ShortStatistics implements Statistics {

    public enum StatisticsType {
        INTEGER, FLOAT, STRING
    }
    protected long count;
    private final StatisticsType statisticsType;

    public ShortStatistics(StatisticsType statisticsType) {
        this.statisticsType = statisticsType;
    }

    @Override
    public void add(Object object) {
        ++count;
    }

    @Override
    public void show() {
        if (count > 0) {
            if (statisticsType == StatisticsType.INTEGER) {
                System.out.printf("The number of integers read is equal to %d%n", count);
            } else if (statisticsType == StatisticsType.FLOAT) {
                System.out.printf("The number of float numbers read is equal to %d%n", count);
            } else {
                System.out.printf("The number of strings read is equal to %d%n", count);
            }
        } else {
            if (statisticsType == StatisticsType.INTEGER) {
                System.out.println("There are no integers");
            } else if (statisticsType == StatisticsType.FLOAT) {
                System.out.println("There are no float numbers");
            } else {
                System.out.println("There are no strings");
            }
        }
    }
}
