package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
        if (onScreenValueView.getText().length() > 10 && Character.isDigit(resName.charAt(resName.length() - 1))) { //restrict input screen to 10 characters
            return;
        }
        if (onScreenValueView.getText().toString().equals("ERROR")) {
            if ((resName.equals("button_clear") || Character.isDigit(resName.charAt(resName.length() - 1)))) {
                secondNumInputStarted = false;
                currentOperation = Operation.NONE;
                onScreenValueView.setText("");
            } else {
                return;
            }
        }
        if ((onScreenValueView.getText().toString().equals("NaN") || onScreenValueView.getText().toString().contains("Infinity"))
                && (resName.equals("button_back"))) {
            return;
        }
        try {
            if (Character.isDigit(resName.charAt(resName.length() - 1))) {
                onScreenValueView.append(String.valueOf(resName.charAt(resName.length() - 1)));
            } else {
                switch (v.getId()) {
                    case R.id.button_x:
                        if (onScreenValueView.getText().length() == 0) break;
                        setOnScreenValues(Operation.MULTIPLY);
                        onScreenSignView.setText("x");
                        break;
                    case R.id.button_plus:
                        if (onScreenValueView.getText().length() == 0) break;
                        setOnScreenValues(Operation.ADD);
                        onScreenSignView.setText("+");
                        break;
                    case R.id.button_minus:
                        if (onScreenValueView.getText().length() == 0) break;
                        setOnScreenValues(Operation.SUBTRACT);
                        onScreenSignView.setText("-");
                        break;
                    case R.id.button_division:
                        if (onScreenValueView.getText().length() == 0) break;
                        setOnScreenValues(Operation.DIVIDE);
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
                        } else if (onScreenValueView.getText().length() <= 10) {
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
                                } else {
                                    result = "ERROR";
                                    createShortToastPopup("Division by zero");
                                }
                                break;
                        }
                        result = result.contains(".") ?
                                result.contains("E") ? result.replaceAll("0*E", "E").replaceAll("\\.$", "")
                                        : result.replaceAll("0*$", "").replaceAll("\\.$", "") : result; //remove trailing zeroes
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
        } catch (NumberFormatException nfe) {
            createShortToastPopup("Illegal argument");
            onScreenValueView.setText(R.string._error);
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

        ViewGroup group = findViewById(R.id.myrootlayout);
        View v;
        for (int i = 0; i < group.getChildCount(); i++) {
            v = group.getChildAt(i);
            if (v instanceof Button) v.setOnClickListener(this);
        }

        if (savedInstanceState != null) {
            secondNumInputStarted = savedInstanceState.getBoolean("secondInputState");
            firstOperand = savedInstanceState.getDouble("firstOperandState");
            currentOperation = (Operation) savedInstanceState.getSerializable("currentOperation");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("secondInputState", secondNumInputStarted);
        outState.putDouble("firstOperandState", firstOperand);
        outState.putSerializable("currentOperation", currentOperation);
        super.onSaveInstanceState(outState);
    }

    private void setOnScreenValues(Operation operation) {
        currentOperation = operation;
        firstOperand = Double.parseDouble(onScreenValueView.getText().toString());
    }

    private void createShortToastPopup(String message) {
        Context context = getApplicationContext();
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
