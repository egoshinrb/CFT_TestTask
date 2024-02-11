package src.Controller;

import src.MyIllegalArgumentException;
import src.Parsers.ArgumentsParser;
import src.Parsers.NumbersParser;
import src.Statistics.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
    private final static String INTEGERS_FILE_NAME = "integers.txt";
    private final static String FLOATS_FILE_NAME = "floats.txt";
    private final static String STRINGS_FILE_NAME = "strings.txt";
    private final static int CACHE_SIZE = 1000;
    private final static byte FLAG_APPEND_MASK = 1;
    private final static byte FLAG_SHORT_MASK = 2;
    private final static byte FLAG_FULL_MASK = 4;

    private static FileWriter integersFileWriter;
    private static FileWriter floatsFileWriter;
    private static FileWriter stringsFileWriter;

    private static Statistics integersStatistics;
    private static Statistics floatsStatistics;
    private static Statistics stringsStatistics;

    private static byte params;

    private Controller() {
    }

    public static void process(String[] args) {
        try {
            ArgumentsParser argumentsParser = ArgumentsParser.getInstance(args);
            System.out.println(argumentsParser.getFlags()); // TODO убрать!!!
            System.out.println(argumentsParser.getOutputFilesPathWithPrefix()); // TODO убрать!!!
            System.out.println(argumentsParser.getInputFileNames() + "\n" + "=====================" + "\n"); // TODO убрать!!!

            params = argumentsParser.getFlags();
            initStatistics();

            String filesPathWithPrefix = argumentsParser.getOutputFilesPathWithPrefix();
            ArrayList<String> inputFileNames = argumentsParser.getInputFileNames();

            NumbersParser numbersParser = new NumbersParser();
            StringBuilder integersStringBuilder = new StringBuilder();
            StringBuilder floatsStringBuilder = new StringBuilder();
            StringBuilder stringsStringBuilder = new StringBuilder();

            int integersCacheSize = CACHE_SIZE;
            int floatsCacheSize = CACHE_SIZE;
            int stringsCacheSize = CACHE_SIZE;

            int i = inputFileNames.size();
            for (String fileReaderName : inputFileNames) {
                try (Scanner scanner = new Scanner(new FileReader(fileReaderName)).useDelimiter("\n")) {
                    while (scanner.hasNext()) {
                        numbersParser.setSource(scanner.nextLine());
                        if (numbersParser.isInteger().getIsNumber()) {
                            integersStringBuilder.append(numbersParser.getIntegerNumber()).append("\n");

                            if ((params & FLAG_FULL_MASK) > 0 || (params & FLAG_SHORT_MASK) > 0) {
                                integersStatistics.add(numbersParser.getIntegerNumber());
                            }
                            --integersCacheSize;
                        } else if (numbersParser.isFloat().getIsNumber()) {
                            floatsStringBuilder.append(numbersParser.getFloatNumber()).append("\n");

                            if ((params & FLAG_FULL_MASK) > 0 || (params & FLAG_SHORT_MASK) > 0) {
                                floatsStatistics.add(numbersParser.getFloatNumber());
                            }
                            --floatsCacheSize;
                        } else {
                            stringsStringBuilder.append(numbersParser.getSource()).append("\n");

                            if ((params & FLAG_FULL_MASK) > 0 || (params & FLAG_SHORT_MASK) > 0) {
                                stringsStatistics.add(numbersParser.getSource());
                            }
                            --stringsCacheSize;
                        }


                        if (integersCacheSize == 0 || (i == 1 && !scanner.hasNext())) {
                            integersCacheSize = CACHE_SIZE;
                            writeFile(integersFileWriter,filesPathWithPrefix + INTEGERS_FILE_NAME, integersStringBuilder);
                            integersStringBuilder.delete(0, integersStringBuilder.length());
                        }

                        if (floatsCacheSize == 0 || (i == 1 && !scanner.hasNext())) {
                            floatsCacheSize = CACHE_SIZE;
                            writeFile(floatsFileWriter,filesPathWithPrefix + FLOATS_FILE_NAME, floatsStringBuilder);
                            floatsStringBuilder.delete(0, floatsStringBuilder.length());
                        }

                        if (stringsCacheSize == 0 || (i == 1 && !scanner.hasNext())) {
                            stringsCacheSize = CACHE_SIZE;
                            writeFile(stringsFileWriter,filesPathWithPrefix + STRINGS_FILE_NAME, stringsStringBuilder);
                            stringsStringBuilder.delete(0, stringsStringBuilder.length());
                        }
                    }

                    --i;
                } catch (FileNotFoundException e) {
                    System.out.println("Не найден файл " + fileReaderName);
                    --i;
                }
            }

            showStatistics();
        } catch (MyIllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            closeWriterFiles();
        }
    }

    private static void initStatistics() {
        if ((params & FLAG_FULL_MASK) > 0) {
            integersStatistics = new IntegersFullStatistics();
            floatsStatistics = new FloatsFullStatistics();
            stringsStatistics = new StringsFullStatistics();
        } else if ((params & FLAG_SHORT_MASK) > 0) {
            integersStatistics = new ShortStatistics(StatisticsType.INTEGER);
            floatsStatistics = new ShortStatistics(StatisticsType.FLOAT);
            stringsStatistics = new ShortStatistics(StatisticsType.STRING);
        }
    }

    private static void showStatistics() {
        if ((params & FLAG_FULL_MASK) > 0 || (params & FLAG_SHORT_MASK) > 0) {
            integersStatistics.show();
            floatsStatistics.show();
            stringsStatistics.show();
        }
    }

    private static void writeFile(FileWriter fileWriter, String fileName, StringBuilder stringBuilder) {
            try {
                if (fileWriter == null) {
                    fileWriter = new FileWriter(fileName, (params & FLAG_APPEND_MASK) > 0);
                }
                fileWriter.write(stringBuilder.toString());
            } catch (IOException e) {
                System.out.println("Не удалось создать/открыть/записать в файл " + fileName);
            }
    }

    private static void closeWriterFiles() {
        if (integersFileWriter != null) {
            try {
                integersFileWriter.close();
            } catch (IOException e) {
                System.out.println("Не удалось закрыть файл" + INTEGERS_FILE_NAME);
            }
        }

        if (floatsFileWriter != null) {
            try {
                floatsFileWriter.close();
            } catch (IOException e) {
                System.out.println("Не удалось закрыть файл" + FLOATS_FILE_NAME);
            }
        }

        if (stringsFileWriter != null) {
            try {
                stringsFileWriter.close();
            } catch (IOException e) {
                System.out.println("Не удалось закрыть файл" + STRINGS_FILE_NAME);
            }
        }
    }
}