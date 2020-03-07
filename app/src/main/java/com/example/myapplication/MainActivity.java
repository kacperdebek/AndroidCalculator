package com.example.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView onScreenValueView;
    private boolean blockOperations = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        onScreenValueView = findViewById(R.id.onScreenValueView);

        Button zero = findViewById(R.id.button_0);
        zero.setOnClickListener(this);

        Button one = findViewById(R.id.button_1);
        one.setOnClickListener(this);

        Button two = findViewById(R.id.button_2);
        two.setOnClickListener(this);

        Button three = findViewById(R.id.button_3);
        three.setOnClickListener(this);

        Button four = findViewById(R.id.button_4);
        four.setOnClickListener(this);

        Button five = findViewById(R.id.button_5);
        five.setOnClickListener(this);

        Button six = findViewById(R.id.button_6);
        six.setOnClickListener(this);

        Button seven = findViewById(R.id.button_7);
        seven.setOnClickListener(this);

        Button eight = findViewById(R.id.button_8);
        eight.setOnClickListener(this);

        Button nine = findViewById(R.id.button_9);
        nine.setOnClickListener(this);

        Button plus = findViewById(R.id.button_plus);
        plus.setOnClickListener(this);

        Button minus = findViewById(R.id.button_minus);
        minus.setOnClickListener(this);

        Button divide = findViewById(R.id.button_division);
        divide.setOnClickListener(this);

        Button dot = findViewById(R.id.button_dot);
        dot.setOnClickListener(this);

        Button equals = findViewById(R.id.button_equals);
        equals.setOnClickListener(this);

        Button multi = findViewById(R.id.button_x);
        multi.setOnClickListener(this);

        Button clear = findViewById(R.id.button_clear);
        clear.setOnClickListener(this);

        Button back = findViewById(R.id.button_back);
        back.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        String resName = getResources().getResourceEntryName(v.getId());
        if (onScreenValueView.getText().length() > 9 && Character.isDigit(resName.charAt(resName.length() - 1))) {
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
                //onScreenValueView.append("x");
                break;
            case R.id.button_plus:
                //onScreenValueView.append("+");
                break;
            case R.id.button_minus:
                //onScreenValueView.append("-");
                break;
            case R.id.button_division:
                //onScreenValueView.append("÷");
                break;
            case R.id.button_dot:
                onScreenValueView.append(".");
                break;
            case R.id.button_clear:
                onScreenValueView.setText("");
                break;
            case R.id.button_back:
                String current = onScreenValueView.getText().toString();
                if (current.length() > 0) {
                    onScreenValueView.setText(current.substring(0, current.length() - 1));
                }
                break;
            case R.id.button_equals:
                //onScreenValueView.setText("");
                break;
            default:
                break;
        }
    }
}
