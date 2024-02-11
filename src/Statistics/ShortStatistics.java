package src.Statistics;

public class ShortStatistics implements Statistics {
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
                System.out.println("Количество целых чисел " + count);
            } else if (statisticsType == StatisticsType.FLOAT) {
                System.out.println("Количество вещественных чисел " + count);
            } else {
                System.out.println("Количество строк " + count);
            }
        }
    }
}
