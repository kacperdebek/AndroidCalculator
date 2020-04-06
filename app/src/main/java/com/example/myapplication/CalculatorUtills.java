package com.example.myapplication;

class CalculatorUtills {
    static String limitOutputLength(String output) {
        String newOutput;
        if (output.contains("E")) {
            int index = output.indexOf("E");
            int exponentLength = output.length() - index;
            newOutput = output.length() > 11
                    ? output.substring(0, 11 - exponentLength) + output.substring(index)
                    : output.substring(0, output.length() - exponentLength) + output.substring(index);
        } else {
            newOutput = output.length() > 11 ? output.substring(0, 11) : output;
        }
        return newOutput;
    }
}
