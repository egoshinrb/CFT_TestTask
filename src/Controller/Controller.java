package src.Controller;

import src.Exception.MyIllegalArgumentException;
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
    private static boolean isShowStatistics;

    private static byte params;

    private Controller() {
    }

    public static void process(String[] args) {
        System.out.println("Start processing");
        try {
            ArgumentsParser argumentsParser = ArgumentsParser.getInstance(args);
            params = argumentsParser.getFlags();
            String filesPathWithPrefix = argumentsParser.getOutputFilesPathWithPrefix();
            ArrayList<String> inputFileNames = argumentsParser.getInputFileNames();

            initStatistics();
            if ((params & FLAG_FULL_MASK) > 0 || (params & FLAG_SHORT_MASK) > 0) {
                isShowStatistics = true;
            }

            NumbersParser numbersParser = new NumbersParser();
            StringBuilder integersStringBuilder = new StringBuilder();
            StringBuilder floatsStringBuilder = new StringBuilder();
            StringBuilder stringsStringBuilder = new StringBuilder();

            int integersCacheSize = CACHE_SIZE;
            int floatsCacheSize = CACHE_SIZE;
            int stringsCacheSize = CACHE_SIZE;

            for (String fileReaderName : inputFileNames) {
                try (Scanner scanner = new Scanner(new FileReader(fileReaderName)).useDelimiter("\n")) {
                    System.out.println("Open file " + fileReaderName);
                    while (scanner.hasNext()) {
                        numbersParser.setSource(scanner.nextLine());
                        if (numbersParser.isInteger().getIsNumber()) {
                            integersStringBuilder.append(numbersParser.getIntegerNumber()).append("\n");

                            if (isShowStatistics) {
                                integersStatistics.add(numbersParser.getIntegerNumber());
                            }
                            --integersCacheSize;
                        } else if (numbersParser.isFloat().getIsNumber()) {
                            floatsStringBuilder.append(numbersParser.getFloatNumber()).append("\n");

                            if (isShowStatistics) {
                                floatsStatistics.add(numbersParser.getFloatNumber());
                            }
                            --floatsCacheSize;
                        } else {
                            stringsStringBuilder.append(numbersParser.getSource()).append("\n");

                            if (isShowStatistics) {
                                stringsStatistics.add(numbersParser.getSource());
                            }
                            --stringsCacheSize;
                        }

                        if (integersCacheSize == 0 || (!scanner.hasNext() && integersCacheSize != CACHE_SIZE)) {
                            integersFileWriter = writeFile(integersFileWriter, filesPathWithPrefix + INTEGERS_FILE_NAME, integersStringBuilder);

                            if (integersFileWriter != null || integersCacheSize == 0) {
                                integersCacheSize = CACHE_SIZE;
                                integersStringBuilder.delete(0, integersStringBuilder.length());
                            }
                        }

                        if (floatsCacheSize == 0 || (!scanner.hasNext() && floatsCacheSize != CACHE_SIZE)) {
                            floatsFileWriter = writeFile(floatsFileWriter, filesPathWithPrefix + FLOATS_FILE_NAME, floatsStringBuilder);

                            if (floatsFileWriter != null || floatsCacheSize == 0) {
                                floatsCacheSize = CACHE_SIZE;
                                floatsStringBuilder.delete(0, floatsStringBuilder.length());
                            }
                        }

                        if (stringsCacheSize == 0 || (!scanner.hasNext() && stringsCacheSize != CACHE_SIZE)) {
                            stringsFileWriter = writeFile(stringsFileWriter,filesPathWithPrefix + STRINGS_FILE_NAME, stringsStringBuilder);

                            if (stringsFileWriter != null || stringsCacheSize == 0) {
                                stringsCacheSize = CACHE_SIZE;
                                stringsStringBuilder.delete(0, stringsStringBuilder.length());
                            }
                        }
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("Not found file " + fileReaderName);
                }
            }

            if (isShowStatistics) {
                showStatistics();
            }
        } catch (MyIllegalArgumentException e) {
            System.out.println(e.getMessage());
        } finally {
            closeWriterFiles();
            System.out.println("End process");
        }
    }

    private static void initStatistics() {
        if ((params & FLAG_FULL_MASK) > 0) {
            integersStatistics = new IntegersFullStatistics();
            floatsStatistics = new FloatsFullStatistics();
            stringsStatistics = new StringsFullStatistics();
        } else if ((params & FLAG_SHORT_MASK) > 0) {
            integersStatistics = new ShortStatistics(ShortStatistics.StatisticsType.INTEGER);
            floatsStatistics = new ShortStatistics(ShortStatistics.StatisticsType.FLOAT);
            stringsStatistics = new ShortStatistics(ShortStatistics.StatisticsType.STRING);
        }
    }

    private static void showStatistics() {
        if (isShowStatistics) {
            integersStatistics.show();
            floatsStatistics.show();
            stringsStatistics.show();
        }
    }

    private static FileWriter writeFile(FileWriter fileWriter, String fileName, StringBuilder stringBuilder) {
        try {
            if (fileWriter == null) {
                fileWriter = new FileWriter(fileName, (params & FLAG_APPEND_MASK) > 0);
                System.out.println("Create file " + fileName);
            }
            fileWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            System.out.println("The system cannot find the specified path " + fileName + ". The file cannot be recorded");
        }

        return fileWriter;
    }

    private static void closeWriterFiles() {
        if (integersFileWriter != null) {
            try {
                integersFileWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to close the file" + INTEGERS_FILE_NAME);
            }
        }

        if (floatsFileWriter != null) {
            try {
                floatsFileWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to close the file" + FLOATS_FILE_NAME);
            }
        }

        if (stringsFileWriter != null) {
            try {
                stringsFileWriter.close();
            } catch (IOException e) {
                System.out.println("Failed to close the file" + STRINGS_FILE_NAME);
            }
        }
    }
}
