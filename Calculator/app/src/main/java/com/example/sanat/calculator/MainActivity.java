package com.example.sanat.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int MENU_RESET = 1;
    private final int MENU_QUIT = 2;
    EditText num1;
    EditText num2;

    Button add;
    Button sub;
    Button mult;
    Button div;

    TextView result;

    String oper = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);

        add = (Button) findViewById(R.id.add_button);
        sub = (Button) findViewById(R.id.sub_button);
        mult = (Button) findViewById(R.id.mult_button);
        div = (Button) findViewById(R.id.div_button);

        result = (TextView) findViewById(R.id.tvResult);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a =0;
                float b = 0;
                float floatResult = 0;
                //return if two numbers not entered
                if(TextUtils.isEmpty(num1.getText().toString()) || TextUtils.isEmpty(num2.getText().toString()))
                    return;

                a = Float.parseFloat(num1.getText().toString());
                b = Float.parseFloat(num2.getText().toString());
                floatResult = a+b;
                oper = "+";
                result.setText(a + " " + oper + " " + b + "= " + floatResult);
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float a =0;
                float b = 0;
                float floatResult = 0;
                //return if no numbers entered
                if(TextUtils.isEmpty(num1.getText().toString()) || TextUtils.isEmpty(num2.getText().toString()))
                    return;

                a = Float.parseFloat(num1.getText().toString());
                b = Float.parseFloat(num2.getText().toString());
                floatResult = a-b;
                oper = "-";
                result.setText(a + " " + oper + " " + b + "= " + floatResult);
            }
        });
        mult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a =0;
                float b = 0;
                float floatResult = 0;
                //return if no numbers entered
                if(TextUtils.isEmpty(num1.getText().toString()) || TextUtils.isEmpty(num2.getText().toString()))
                    return;

                a = Float.parseFloat(num1.getText().toString());
                b = Float.parseFloat(num2.getText().toString());
                floatResult = a*b;
                oper = "*";
                result.setText(a + " " + oper + " " + b + "= " + floatResult);
            }
        });
        div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float a =0;
                float b = 0;
                float floatResult = 0;
                //return if no numbers entered
                if(TextUtils.isEmpty(num1.getText().toString()) || TextUtils.isEmpty(num2.getText().toString()))
                    return;

                a = Float.parseFloat(num1.getText().toString());
                b = Float.parseFloat(num2.getText().toString());
                floatResult = a/b;
                oper = "/";
                result.setText(a + " " + oper + " " + b + "= " + floatResult);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        menu.add(0,MENU_RESET, 0, "Reset");
        menu.add(0,MENU_QUIT, 1, "Quit");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id)
        {
            case MENU_RESET: num1.setText("");
                num2.setText("");
                result.setText("");
                break;

            case MENU_QUIT: finish();
                break;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
