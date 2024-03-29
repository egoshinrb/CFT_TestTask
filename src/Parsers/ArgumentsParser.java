package src.Parsers;

import src.Exception.MyIllegalArgumentException;
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
    private static byte flags; // 0 бит - a, 1 бит - s, 2 бит - f

    private ArgumentsParser(String[] args) throws MyIllegalArgumentException {
        if (args.length == 0) {
            throw new MyIllegalArgumentException("Command line parameters are not set, processing is not possible");
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
                    if (!outputFilesPathWithPrefix.endsWith("/")) {
                        outputFilesPathWithPrefix += "/";
                    }

                    if (!outputFilesPathWithPrefix.startsWith(".")) {
                        outputFilesPathWithPrefix = "." + outputFilesPathWithPrefix;
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
