package com.example.sanat.tipcalculator;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

public class ComplexCalcActivity extends AppCompatActivity {

    private static final String TOTAL_BILL = "TOTAL_BILL";
    private static final String CURRENT_TIP = "CURRENT_TIP";
    private static final String BILL_WITHOUT_TIP = "BILL_WITHOUT_TIP";

    private double tipAmount;
    private double finalBill;
    private double billBeforeTip;

    EditText billBeforeTipET;
    EditText finalBillET;
    EditText tipAmountET;

    private int[] checkListValues = new int[12];

    CheckBox friendlyCheckBox;
    CheckBox promptSeatingCheckBox;
    CheckBox opinionCheckBox;

    RadioGroup availableRadioGroup;
    RadioButton availableBadRadio;
    RadioButton availableOKRadio;
    RadioButton availableGoodRadio;

    Spinner problemSpinner;

    Button startChronometerButton;
    Button pauseChronometerButton;
    Button resetChronometerButton;

    Chronometer timeWaitingChronometer;

    long minutesWaited =0;

    TextView timeWaiting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complex_calc);

        //check if app is being restored or if this is the fort time it is being used
        if(savedInstanceState == null) {

            //started first time
            billBeforeTip = 0.0;
            tipAmount = 0.15;
            finalBill = 0.0;
        }
        else{
            billBeforeTip = savedInstanceState.getDouble(BILL_WITHOUT_TIP);
            tipAmount = savedInstanceState.getDouble(CURRENT_TIP);
            finalBill = savedInstanceState.getDouble(TOTAL_BILL);
        }

        // Initialize the EditTexts

        billBeforeTipET = (EditText) findViewById(R.id.billEditText); // Users bill before tip
        tipAmountET = (EditText) findViewById(R.id.tipEditText); // Tip amount
        finalBillET = (EditText) findViewById(R.id.finalBillEditText); // Bill plus tip

        // Initialize the SeekBar and add a ChangeListener

        tipSeekBar = (SeekBar) findViewById(R.id.changeTipSeekBar);

        tipSeekBar.setOnSeekBarChangeListener(tipSeekBarListener);


        // Add change listener for when the bill before tip is changed

        billBeforeTipET.addTextChangedListener(billBeforeTipListener);

        // Initialize CheckBoxs
        friendlyCheckBox = (CheckBox) findViewById(R.id.friendlyCheckBox);
        promptSeatingCheckBox = (CheckBox) findViewById(R.id.seatingCheckBox);
        opinionCheckBox = (CheckBox) findViewById(R.id.opinionCheckBox);

        setUpIntroCheckBoxes(); // Add change listeners to check boxes

        // Initialize RadioButtons

        availableBadRadio= (RadioButton) findViewById(R.id.availableBadRadio);
        availableOKRadio = (RadioButton) findViewById(R.id.availableOKRadio);
        availableGoodRadio = (RadioButton) findViewById(R.id.availableGoodRadio);

        // Initialize RadioGroups

        availableRadioGroup = (RadioGroup) findViewById(R.id.availableRadioGroup);

        // Add ChangeListener To Radio buttons
        addChangeListenerToRadios();

        // Initialize the Spinner

        problemSpinner = (Spinner) findViewById(R.id.problemsSpinner);

        problemSpinner.setPrompt("Problem Solving");

        // Add ItemSelectedListener To Spinner

        addItemSelectedListenerToSpinner();

        // Initialize Buttons

        startChronometerButton = (Button) findViewById(R.id.startChronometerButton);
        pauseChronometerButton = (Button) findViewById(R.id.pauseChronometerButton);
        resetChronometerButton = (Button) findViewById(R.id.resetChronometerButton);

        // Add setOnClickListeners for buttons
        setButtonOnClickListeners();

        // Initialize Chronometer

        timeWaitingChronometer = (Chronometer) findViewById(R.id.timeWaitingChronometer);

        // TextView for Chronometer

        timeWaiting = (TextView) findViewById(R.id.timeWaitingTextView);

    }

    //called if billBeforeTipET editText is changed
    private TextWatcher billBeforeTipListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try{
                //change to new input
                billBeforeTip = Double.parseDouble(s.toString());
            }
            catch (NumberFormatException e){
                billBeforeTip = 0.0;
            }

            updateTipAndFinalBill();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private void updateTipAndFinalBill() {
        double tipAmt = Double.parseDouble(tipAmountET.getText().toString());

        double finalBill = billBeforeTip + (.1*tipAmt);

        finalBillET.setText(String.format("%.2f", finalBill));
    }

    // Called when a device changes in some way. For example when a keyboard is popped out, or when the device is
    // rotated. Used to save state information that you'd like to be made available.
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putDouble(TOTAL_BILL, finalBill);
        outState.putDouble(CURRENT_TIP, tipAmount);
        outState.putDouble(BILL_WITHOUT_TIP,billBeforeTip);
    }

    //Seekbar can be used to make a custom tip
    //This is to make that happen
    private SeekBar tipSeekBar;

    private SeekBar.OnSeekBarChangeListener tipSeekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //get the value set on the seekbar
            tipAmount = (tipSeekBar.getProgress()) * .1;

            //update tipAmountET
            tipAmountET.setText(String.format("%.2f",tipAmount));

            //update all other edit texts
            updateTipAndFinalBill();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    private void setUpIntroCheckBoxes(){
        //add ChangeListener to the friendly check box
        friendlyCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkListValues[0] = (friendlyCheckBox.isChecked()) ? 4 : 0;

                //set tip using checklist options
                setTip();

                updateTipAndFinalBill();
            }
        });
    }

    private void setTip(){
        double checklistTip = 0;

        for(int i: checkListValues)
            checklistTip+=i;

        tipAmountET.setText(String.format("%.2f", checklistTip * .1));
    }

    private void addChangeListenerToRadios(){

        //setting the listeners on the RadioGroups
        availableRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                checkListValues[3] = (availableBadRadio.isChecked()) ? -1 : 0;
                checkListValues[4] = (availableOKRadio.isChecked()) ? 2 : 0;
                checkListValues[5] = (availableGoodRadio.isChecked()) ? 4 : 0;

                setTip();

                updateTipAndFinalBill();
            }
        });
    }

    //adding spinner itemSelectedListener
    private void addItemSelectedListenerToSpinner(){
        problemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    checkListValues[6] = (problemSpinner.getSelectedItem().equals("Bad")) ? -1 : 0;
                    checkListValues[7] = (problemSpinner.getSelectedItem().equals("OK")) ? 2 : 0;
                    checkListValues[8] = (problemSpinner.getSelectedItem().equals("Good")) ? 4 : 0;

                    setTip();

                    updateTipAndFinalBill();
                }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    //adding OnClickListeners for the chronometer buttons
    private void setButtonOnClickListeners(){
        startChronometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int stoppedMilliseconds = 0;

                String chronoText = timeWaitingChronometer.getText().toString();
                String array[]= chronoText.split(":");
                if(array.length==2){
                    //means we need to count seconds
                    stoppedMilliseconds = Integer.parseInt(array[0])*1000*60 + Integer.parseInt(array[1])*1000;
                }
                else if(array.length==3){
                    //means we need to count minutes
                    stoppedMilliseconds= Integer.parseInt(array[0])*1000*60*60 + Integer.parseInt(array[1])*1000*60
                            + Integer.parseInt(array[2])*1000;
                }

                //Now to calculate time elapsed since start button pressed
                timeWaitingChronometer.setBase(SystemClock.elapsedRealtime()-stoppedMilliseconds);

                minutesWaited = Long.parseLong(array[2]);

                updateTipBasedOnTimeWaited(minutesWaited);

                timeWaitingChronometer.start();
            }
        });

        pauseChronometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWaitingChronometer.stop();
            }
        });

        resetChronometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeWaitingChronometer.setBase(SystemClock.elapsedRealtime());
                minutesWaited = 0;
            }
        });
    }

    private void updateTipBasedOnTimeWaited(long minutesWaited){
        //if spent more than 20 minutes waiting for food, subtract 2 to the tip.
        //if between 15 and 20, add 0
        //if below 15, add 2
        if(minutesWaited>=20)
            checkListValues[9] = -2;
        if(15< minutesWaited && minutesWaited<20)
            checkListValues[9] = 0;
        else
            checkListValues[9] = 2;


        setTip();

        updateTipAndFinalBill();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complex_calc, menu);
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
