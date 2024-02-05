package src;


// integers.txt, floats.txt, strings.txt
// -o = путь для результатов
// -p = префикс имен выходных файлов
// -a = режим добавления в существующие файлы
// -s = краткая статистика - количество записанных элементов
// -f = полная статистика - для чисел дополнительно содержит минимальное и максимальное значения, сумма и среднее.
//                          для строк содержит также размер самой короткой строки и самой длинной.

import java.util.ArrayList;

public class ArgumentsParser {
    private final static String FLAG_OUTPUT = "-o";
    private final static String FLAG_PREFIX = "-p";
    private final static String FLAG_APPEND = "-a";
    private final static String FLAG_SHORT = "-s";
    private final static String FLAG_FULL = "-f";

    private static ArgumentsParser instance;

    private static ArrayList<String> inputFileNames;
    private static String outputFilesPathWithPrefix;

    private static byte flags; // 0 бит - a, 1 - s, 2 - f

    private ArgumentsParser(String[] args) throws MyIllegalArgumentException {
        if (args.length == 0) {
            throw new MyIllegalArgumentException("Не заданы параметры командной строки, дальнейшая обработка невозможна.");
        } else {
            outputFilesPathWithPrefix = "";
            ArgumentsParser.init(args);
        }
    }

    public static ArgumentsParser getInstance(String[] args) throws MyIllegalArgumentException {
        if (instance == null) {
            instance = new ArgumentsParser(args);
        }

        return instance;
    }

    public ArrayList<String> getInputFileNames() {
        return inputFileNames;
    }

    public String getOutputFilesPathWithPrefix() {
        return outputFilesPathWithPrefix;
    }

    public byte getFlags() {
        return flags;
    }

    private static void init(String[] args) {
        String prefix = "";
        for (int i = 0; i < args.length; ++i) {
            if (args[i].equalsIgnoreCase(FLAG_OUTPUT)) {
                if (i + 1 < args.length && !ArgumentsParser.isFlag(args[i + 1])) {
                    outputFilesPathWithPrefix = args[i + 1];
                    if ((outputFilesPathWithPrefix.contains("\\") && !outputFilesPathWithPrefix.endsWith("\\"))) {
                        outputFilesPathWithPrefix += "\\";
                    } else if (outputFilesPathWithPrefix.contains("/") && !outputFilesPathWithPrefix.endsWith("/")) {
                        outputFilesPathWithPrefix += "/";
                    } else if (!outputFilesPathWithPrefix.endsWith("\\")) {
                        outputFilesPathWithPrefix += "\\";
                    }
                    ++i;
                }
            } else if (args[i].equalsIgnoreCase(FLAG_PREFIX)) {
                if (i + 1 < args.length && !ArgumentsParser.isFlag(args[i + 1])) {
                    prefix = args[i + 1];
                    ++i;
                }
            } else if (args[i].equalsIgnoreCase(FLAG_APPEND)) {
                flags |= 1;
            } else if (args[i].equalsIgnoreCase(FLAG_SHORT)) {
                flags |= 2;
            } else if (args[i].equalsIgnoreCase(FLAG_FULL)) {
                flags |= 4;
            } else {
                if (inputFileNames == null) {
                    inputFileNames = new ArrayList<>();
                }
                inputFileNames.add(args[i]);
            }
        }

        outputFilesPathWithPrefix += prefix;
    }

    private static boolean isFlag(String str) {
        return str.equalsIgnoreCase(FLAG_OUTPUT) || str.equalsIgnoreCase(FLAG_PREFIX) ||
                str.equalsIgnoreCase(FLAG_APPEND) || str.equalsIgnoreCase(FLAG_SHORT) || str.equalsIgnoreCase(FLAG_FULL);
    }
}
