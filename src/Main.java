package src;

import java.io.*;
import java.util.Scanner;

// -s -a -p sample- in1.txt in2.txt
// -s -f -p 111 -p 222 -o 1234 in1.txt in2.txt in3.txt in4.txt
// -s -f -p 111 -p 222 -o 1234\ -o 5678\ in1.txt in2.txt in3.txt in4.txt
// -a -s -f -p 111 -p 222 -o 1234\ -o 5678\ in1.txt
public class Main {
    private final static String INTEGERS_FILE_NAME = "integers.txt";
    private final static String FLOATS_FILE_NAME = "floats.txt";
    private final static String STRINGS_FILE_NAME = "strings.txt";

    private final static byte FLAG_APPEND_MASK = 1;
    private final static byte FLAG_SHORT_MASK = 2;
    private final static byte FLAG_FULL_MASK = 4;
    public static void main(String[] args) {
        FileWriter integersFileWriter = null;
        FileWriter floatsFileWriter = null;
        FileWriter stringsFileWriter = null;
        try {
            ArgumentsParser argumentsParser = ArgumentsParser.getInstance(args);
            System.out.println(argumentsParser.getFlags());
            System.out.println(argumentsParser.getOutputFilesPathWithPrefix());
            System.out.println(argumentsParser.getInputFileNames() + "\n" + "=====================" + "\n");

            byte params = argumentsParser.getFlags();
            String filesPathWithPrefix = argumentsParser.getOutputFilesPathWithPrefix();
            String tempStr;

            for (String fileName : argumentsParser.getInputFileNames()) {
                Scanner scanner = new Scanner(new FileReader(fileName)).useDelimiter("\n");
                while (scanner.hasNext()) {
                    tempStr = scanner.nextLine();
                    if (NumbersParser.isLong(tempStr)) {
                        if (integersFileWriter == null) {
                            integersFileWriter = new FileWriter(filesPathWithPrefix + INTEGERS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                        }
                        integersFileWriter.write(Long.parseLong(tempStr) + "\n");
                    } else if (NumbersParser.isDouble(tempStr)) {
                        if (floatsFileWriter == null) {
                            floatsFileWriter = new FileWriter(filesPathWithPrefix + FLOATS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                        }
                        floatsFileWriter.write(Double.parseDouble(tempStr) + "\n"); // TODO возможно заменить на сканирование строки?
                    } else {
                        if (stringsFileWriter == null) {
                            stringsFileWriter = new FileWriter(filesPathWithPrefix + STRINGS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                        }
                        stringsFileWriter.write(tempStr + "\n");
                    }
                }

            }
        } catch (MyIllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        } finally {
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
}
