package src;

import src.Controller.Controller;

// -s -a -p sample- in1.txt in2.txt
// -s -f -p 111 -p 222 -o 1234 in1.txt in2.txt in3.txt in4.txt
// -s -f -p 111 -p 222 -o 1234\ -o 5678\ in1.txt in2.txt in3.txt in4.txt
// -a -s -f -p 111 -p 222 -o 1234\ -o 5678\ in1.txt
// -f -a -y floats.txt integers.txt strings.txt
// -f -a -p file1_ file11.txt
// -f -o /src/Parsers -p test_ in1.txt in2.txt
public class Main {
    public static void main(String[] args) {
        Controller.process(args);
    }
}
