package com.example.myapplication;

class CalculatorUtills {
    static String limitOutputLength(String output, int length) {
        String newOutput;
        if (output.contains("E")) {
            int index = output.indexOf("E");
            int exponentLength = output.length() - index;
            newOutput = output.length() > length
                    ? output.substring(0, length - exponentLength) + output.substring(index)
                    : output.substring(0, output.length() - exponentLength) + output.substring(index);
        } else {
            newOutput = output.length() > length ? output.substring(0, length) : output;
        }
        return newOutput;
    }
}
