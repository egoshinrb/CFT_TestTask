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
        try {
            ArgumentsParser argumentsParser = ArgumentsParser.getInstance(args);
            System.out.println(argumentsParser.getFlags());
            System.out.println(argumentsParser.getOutputFilesPathWithPrefix());
            System.out.println(argumentsParser.getInputFileNames());


//            FileWriter fileWriter;
//            FileReader fileReader;
            byte params = argumentsParser.getFlags();
            String filesPathWithPrefix = argumentsParser.getOutputFilesPathWithPrefix();
            FileWriter integersFileWriter = null;
            FileWriter floatsFileWriter = null;
            FileWriter stringsFileWriter = null;

            for (String fileName : argumentsParser.getInputFileNames()) {
                try (Scanner scanner = new Scanner(new FileReader(fileName)).useDelimiter("\n")) {
                    while (scanner.hasNext()) {
                        if (scanner.hasNextInt()) {
                            if (integersFileWriter == null) {
                                integersFileWriter = new FileWriter(filesPathWithPrefix + INTEGERS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                            }
                            integersFileWriter.write(scanner.nextInt());
                        } else if (scanner.hasNextFloat()) {
                            if (floatsFileWriter == null) {
                                floatsFileWriter = new FileWriter(filesPathWithPrefix + FLOATS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                            }
                            floatsFileWriter.write(Float.valueOf(scanner.nextFloat()).toString()); // TODO возможно заменить на сканирование строки?
                        } else {
                            if (stringsFileWriter == null) {
                                stringsFileWriter = new FileWriter(filesPathWithPrefix + STRINGS_FILE_NAME, (params & FLAG_APPEND_MASK) > 0);
                            }
                        }
                    }

                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e); // TODO проверить, что мы ловим RuntimeException
                }
                finally {
                    if (integersFileWriter != null) {
                        integersFileWriter.close();
                    }

                    if (floatsFileWriter != null) {
                        floatsFileWriter.close();
                    }

                    if (stringsFileWriter != null) {
                        stringsFileWriter.close();
                    }
                }
            }


        } catch (MyIllegalArgumentException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
