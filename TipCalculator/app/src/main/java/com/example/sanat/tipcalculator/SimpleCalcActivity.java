package com.example.sanat.tipcalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SimpleCalcActivity extends AppCompatActivity {

    EditText pre_tip;
    EditText tip;

    Button tip12;
    Button tip15; Button tip18;
    Button custom_tip;

    TextView result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calc);

        pre_tip = (EditText) findViewById(R.id.pre_tip_amount);
        tip = (EditText) findViewById(R.id.tip_amount);

        tip12 = (Button) findViewById(R.id.twelvepercent_button);
        tip15 = (Button) findViewById(R.id.fifteenpercent_button);
        tip18 = (Button) findViewById(R.id.eighteenpercent_button);
        custom_tip = (Button) findViewById(R.id.customtip_button);

        result = (TextView) findViewById(R.id.result);

        tip12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a = Float.parseFloat(pre_tip.getText().toString());
                float b = a*12/100;

                tip.setText("" + b);
                String res = String.format("%.2f", (a+b));
                result.setText("Final amount: $" + res);
            }
        });

        tip15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a = Float.parseFloat(pre_tip.getText().toString());
                float b = a*15/100;

                tip.setText("" + b);
                String res = String.format("%.2f", (a+b));
                result.setText("Final amount: $" + res);
            }
        });

        tip18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a = Float.parseFloat(pre_tip.getText().toString());
                float b = a*18/100;

                tip.setText("" + b);
                String res = String.format("%.2f", (a+b));

                result.setText("Final amount: $" + res);
            }
        });

        custom_tip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(tip.getText().toString()))
                    result.setText("Please enter tip amount");
                else
                {
                    float res;
                    float a = Float.parseFloat(pre_tip.getText().toString());
                    float b = Float.parseFloat(tip.getText().toString());
                    res = a+b;
                    String res2 = String.format("%.2f", res);
                    result.setText("Final amount: $" + res2 );

                }
            }
        });
/*
        tip
        {
            float res;
            float a = Float.parseFloat(pre_tip.getText().toString());
            float b = Float.parseFloat(tip.getText().toString());
            res = a+b;
            result.setText("Final amount: $" + res );
        }
        else
        {

        }
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simple_calc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
