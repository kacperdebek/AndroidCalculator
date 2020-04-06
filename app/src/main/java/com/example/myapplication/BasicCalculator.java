package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class BasicCalculator extends Activity implements View.OnClickListener {
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
            case R.id.button_sign:
                if (onScreenValueView.getText().toString().length() <= 0
                        || (currentOperation != Operation.NONE && !secondNumInputStarted)) {
                    break;
                }
                if (onScreenValueView.getText().charAt(0) == '-') {
                    onScreenValueView.setText(onScreenValueView.getText().toString().substring(1));
                } else if (onScreenValueView.getText().length() <= 9) {
                    String updated = "-" + onScreenValueView.getText().toString();
                    onScreenValueView.setText(updated);
                }
                break;
            case R.id.button_equals:
                if (!secondNumInputStarted) {
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
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.basiccalc_layout);

        onScreenValueView = findViewById(R.id.onScreenValueView);
        onScreenSignView = findViewById(R.id.operationSign);

        findViewById(R.id.button_0).setOnClickListener(this);
        findViewById(R.id.button_1).setOnClickListener(this);
        findViewById(R.id.button_2).setOnClickListener(this);
        findViewById(R.id.button_3).setOnClickListener(this);
        findViewById(R.id.button_4).setOnClickListener(this);
        findViewById(R.id.button_5).setOnClickListener(this);
        findViewById(R.id.button_6).setOnClickListener(this);
        findViewById(R.id.button_7).setOnClickListener(this);
        findViewById(R.id.button_8).setOnClickListener(this);
        findViewById(R.id.button_9).setOnClickListener(this);

        findViewById(R.id.button_plus).setOnClickListener(this);
        findViewById(R.id.button_minus).setOnClickListener(this);
        findViewById(R.id.button_division).setOnClickListener(this);
        findViewById(R.id.button_x).setOnClickListener(this);

        findViewById(R.id.button_clear).setOnClickListener(this);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_sign).setOnClickListener(this);
        findViewById(R.id.button_dot).setOnClickListener(this);
        findViewById(R.id.button_equals).setOnClickListener(this);
    }
}
