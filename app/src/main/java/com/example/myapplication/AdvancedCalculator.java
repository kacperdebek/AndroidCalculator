package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Math.cos;
import static java.lang.Math.log;
import static java.lang.Math.log10;
import static java.lang.Math.pow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static java.lang.Math.tan;

public class AdvancedCalculator extends Activity implements View.OnClickListener {
    private Operation currentOperation = Operation.NONE;
    private TextView onScreenValueView;
    private TextView onScreenSignView;
    private double firstOperand = 0.0;
    private double secondOperand = 0.0;
    private boolean secondNumInputStarted = false;

    @Override
    public void onClick(View v) {
        String resName = getResources().getResourceEntryName(v.getId());
        if (currentOperation != Operation.NONE && Character.isDigit(resName.charAt(resName.length() - 1)) && !secondNumInputStarted) {
            onScreenValueView.setText("");
            secondNumInputStarted = true;
        }
        if (onScreenValueView.getText().length() > 9 && Character.isDigit(resName.charAt(resName.length() - 1))) { //restrict input screen to 10 characters
            return;
        }
        if (onScreenValueView.getText().toString().equals("ERROR") && !resName.equals("button_clear")) {
            return;
        }
        try {
            switch (v.getId()) {
                case R.id.button_0:
                    onScreenValueView.append("0");
                    break;
                case R.id.button_1:
                    onScreenValueView.append("1");
                    break;
                case R.id.button_2:
                    onScreenValueView.append("2");
                    break;
                case R.id.button_3:
                    onScreenValueView.append("3");
                    break;
                case R.id.button_4:
                    onScreenValueView.append("4");
                    break;
                case R.id.button_5:
                    onScreenValueView.append("5");
                    break;
                case R.id.button_6:
                    onScreenValueView.append("6");
                    break;
                case R.id.button_7:
                    onScreenValueView.append("7");
                    break;
                case R.id.button_8:
                    onScreenValueView.append("8");
                    break;
                case R.id.button_9:
                    onScreenValueView.append("9");
                    break;
                case R.id.button_x:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.MULTIPLY;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText("x");
                    break;
                case R.id.button_plus:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.ADD;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText("+");
                    break;
                case R.id.button_minus:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.SUBTRACT;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText("-");
                    break;
                case R.id.button_division:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.DIVIDE;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText("/");
                    break;
                case R.id.button_dot:
                    if (!onScreenValueView.getText().toString().contains(".")) {        //only one dot at a time is allowed in an expression
                        onScreenValueView.append(".");
                    }
                    break;
                case R.id.button_clear:
                    onScreenValueView.setText("");
                    onScreenSignView.setText("");
                    secondNumInputStarted = false;
                    currentOperation = Operation.NONE;
                    break;
                case R.id.button_back:
                    String current = onScreenValueView.getText().toString();
                    if (current.length() > 0) {
                        onScreenValueView.setText(current.substring(0, current.length() - 1));
                    }
                    break;
                case R.id.button_sin:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.SINE;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.sin);
                    break;
                case R.id.button_cos:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.COSINE;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.cos);
                    break;
                case R.id.button_tan:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.TANGENT;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.tan);
                    break;
                case R.id.button_log:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.LOG;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.log);
                    break;
                case R.id.button_ln:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.LN;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.ln);
                    break;
                case R.id.button_xsquared:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.XPOW2;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.x_squared);
                    break;
                case R.id.button_xtothepower:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.XPOWY;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.x_power_y);
                    break;
                case R.id.button_sign:
                    if (onScreenValueView.getText().toString().length() <= 0
                            || (currentOperation != Operation.NONE && !secondNumInputStarted)) {
                        break;
                    }
                    if (onScreenValueView.getText().charAt(0) == '-') {
                        onScreenValueView.setText(onScreenValueView.getText().toString().substring(1));
                    } else if (onScreenValueView.getText().length() <= 10) {
                        String updated = "-" + onScreenValueView.getText().toString();
                        onScreenValueView.setText(updated);
                    }
                    break;
                case R.id.button_sqrt:
                    if (onScreenValueView.getText().length() == 0) {
                        break;
                    }
                    currentOperation = Operation.SQRT;
                    firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    onScreenSignView.setText(R.string.sqrt);
                    break;
                case R.id.button_equals:
                    if (!secondNumInputStarted
                            && currentOperation != Operation.SINE
                            && currentOperation != Operation.COSINE
                            && currentOperation != Operation.TANGENT
                            && currentOperation != Operation.LOG
                            && currentOperation != Operation.LN
                            && currentOperation != Operation.SQRT
                            && currentOperation != Operation.XPOW2) {
                        break;
                    }
                    if (currentOperation != Operation.NONE) {
                        secondOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    }
                    String result = "";
                    switch (currentOperation) {
                        case NONE:
                            break;
                        case ADD:
                            result = String.valueOf(firstOperand + secondOperand);
                            break;
                        case SUBTRACT:
                            result = String.valueOf(firstOperand - secondOperand);
                            break;
                        case MULTIPLY:
                            result = String.valueOf(firstOperand * secondOperand);
                            break;
                        case DIVIDE:
                            if (secondOperand != 0) {
                                result = String.valueOf(firstOperand / secondOperand);
                            } else
                                result = "ERROR";
                            break;
                        case SINE:
                            result = String.valueOf(sin(firstOperand));
                            break;
                        case COSINE:
                            result = String.valueOf(cos(firstOperand));
                            break;
                        case TANGENT:
                            result = String.valueOf(tan(firstOperand));
                            break;
                        case LOG:
                            result = String.valueOf(log10(firstOperand));
                            break;
                        case LN:
                            result = String.valueOf(log(firstOperand));
                            break;
                        case SQRT:
                            result = String.valueOf(sqrt(firstOperand));
                            break;
                        case XPOW2:
                            result = String.valueOf(pow(firstOperand, 2));
                            break;
                        case XPOWY:
                            result = String.valueOf(pow(firstOperand, secondOperand));
                            break;
                    }
                    result = result.contains(".") ? result.replaceAll("0*$", "").replaceAll("\\.$", "") : result; //remove trailing zeroes
                    result = CalculatorUtills.limitOutputLength(result);
                    onScreenValueView.setText(result);
                    onScreenSignView.setText("");
                    if (!onScreenValueView.getText().toString().equals("ERROR")) {
                        firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
                    }
                    secondNumInputStarted = false;
                    currentOperation = Operation.NONE;
                    break;
                default:
                    break;
            }
        } catch (NumberFormatException nfe) {
            onScreenValueView.setText(R.string._error);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.advancedcalc_layout);
        onScreenValueView = findViewById(R.id.onScreenValueView);
        onScreenSignView = findViewById(R.id.operationSign);

        ViewGroup group = findViewById(R.id.myrootlayout);
        View v;
        for (int i = 0; i < group.getChildCount(); i++) {
            v = group.getChildAt(i);
            if (v instanceof Button) v.setOnClickListener(this);
        }
    }
}
