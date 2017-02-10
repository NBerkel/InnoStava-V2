package com.aware.plugin.InnoStaVa;

import android.app.Activity;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aware.Aware;
import com.aware.Aware_Preferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static com.aware.Aware.TAG;

/**
 * Created by niels on 22/11/16.
 */

public class InnoStaVaESM extends Activity {
    public static final String FREE_COMMENT_ONLY = "FREE_COMMENT_ONLY";

    CharSequence warning = "Muista täyttää kaikki kysymykset!";
    long start_time;
    HashMap<String, String> answers = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        start_time = System.currentTimeMillis();
        Log.d("Niels", "start time : " + start_time);
        Log.d("Niels", "start time String : " + String.valueOf(start_time));

        if (getIntent().hasExtra(FREE_COMMENT_ONLY)) prepare_v7();
        else prepare_v1();

        Log.d("Niels", "InnoStaVa ESM onCreate called!");
    }

    private void prepare_v1() {
        setContentView(R.layout.v_1);

        final RadioGroup v1_radio_group = (RadioGroup) findViewById(R.id.v1_radio_group);

        final Button v1_next = (Button) findViewById(R.id.v1_next);
        v1_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("Niels", "v1 next clicked");

                int radio_button_1 = v1_radio_group.getCheckedRadioButtonId(); //if -1, no radio button was checked

                if (radio_button_1 != -1) {
                    RadioButton RB_radio_button_1 = (RadioButton) findViewById(radio_button_1);
                    String radio_button_1_answer = String.valueOf(RB_radio_button_1.getText());

                    // put answer to var
                    answers.put("V1", radio_button_1_answer);

                    if (radio_button_1_answer == getApplicationContext().getString(R.string.v1_5)) {
                        prepare_v2();
                    } else {
                        prepare_v3();
                    }
                } else {
//                    Toast toast = Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT);
//                    toast.show();
                }
            }
        });
    }

    private void prepare_v2() {
        Log.d("Niels", "preparing v2");
        setContentView(R.layout.v_2);
        insert_db();

        final RadioGroup v2_radio_group = (RadioGroup) findViewById(R.id.v2_radio_group);

        final Button v2_next = (Button) findViewById(R.id.v2_next);
        v2_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int radio_button_2 = v2_radio_group.getCheckedRadioButtonId(); //if -1, no radio button was checked


                if (radio_button_2 != -1) {
                    RadioButton RB_radio_button_2 = (RadioButton) findViewById(radio_button_2);
                    String radio_button_2_answer = String.valueOf(RB_radio_button_2.getText());

                    // put answer to var
                    answers.put("V2", String.valueOf(RB_radio_button_2.getText()));

                    if (radio_button_2_answer == getApplicationContext().getString(R.string.v2_4)) {
                        prepare_v6();
                    } else {
                        prepare_v3();
                    }
                } else {
//                    Toast toast = Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT);
//                    toast.show();
                }
            }
        });
    }

    private void prepare_v3() {
        setContentView(R.layout.v_3);
        insert_db();
        final CheckBox v3_1 = (CheckBox) findViewById(R.id.v3_1);
        final CheckBox v3_2 = (CheckBox) findViewById(R.id.v3_2);
        final CheckBox v3_3 = (CheckBox) findViewById(R.id.v3_3);
        final CheckBox v3_4 = (CheckBox) findViewById(R.id.v3_4);
        final CheckBox v3_5 = (CheckBox) findViewById(R.id.v3_5);
        final CheckBox v3_6 = (CheckBox) findViewById(R.id.v3_6);
        final CheckBox v3_7 = (CheckBox) findViewById(R.id.v3_7);
        final CheckBox v3_8 = (CheckBox) findViewById(R.id.v3_8);
        final CheckBox v3_9 = (CheckBox) findViewById(R.id.v3_9);
        final CheckBox v3_10 = (CheckBox) findViewById(R.id.v3_10);

        final Button v3_next = (Button) findViewById(R.id.v3_next);
        v3_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean something_checked = false;

                ArrayList<String> v3_answer = new ArrayList<>();
                v3_answer.clear();

                if(v3_1.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_1.getText().toString() + ";");
                } if (v3_2.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_2.getText().toString() + ";");
                } if (v3_3.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_3.getText().toString() + ";");
                } if (v3_4.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_4.getText().toString() + ";");
                } if (v3_5.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_5.getText().toString() + ";");
                } if (v3_6.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_6.getText().toString() + ";");
                } if (v3_7.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_7.getText().toString() + ";");
                } if (v3_8.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_8.getText().toString() + ";");
                } if (v3_9.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_9.getText().toString() + ";");
                } if (v3_10.isChecked()) {
                    something_checked = true;
                    v3_answer.add(v3_10.getText().toString() + ";");
                }

                if (something_checked == true) {
                    // put answer to var
                    answers.put("V3", String.valueOf(v3_answer));

                    prepare_v4();
                }  else {
//                    Toast toast = Toast.makeText(getApplicationContext(), warning, Toast.LENGTH_SHORT);
//                    toast.show();
                }
            }
        });
    }

    private void prepare_v4() {
        setContentView(R.layout.v_4);
        insert_db();

        final CheckBox v4_1 = (CheckBox) findViewById(R.id.v4_1);
        final CheckBox v4_2 = (CheckBox) findViewById(R.id.v4_2);
        final CheckBox v4_3 = (CheckBox) findViewById(R.id.v4_3);
        final CheckBox v4_4 = (CheckBox) findViewById(R.id.v4_4);
        final CheckBox v4_5 = (CheckBox) findViewById(R.id.v4_5);
        final CheckBox v4_6 = (CheckBox) findViewById(R.id.v4_6);
        final CheckBox v4_7 = (CheckBox) findViewById(R.id.v4_7);
        final CheckBox v4_8 = (CheckBox) findViewById(R.id.v4_8);
        final CheckBox v4_9 = (CheckBox) findViewById(R.id.v4_9);
        final CheckBox v4_10 = (CheckBox) findViewById(R.id.v4_10);

        final Button v4_next = (Button) findViewById(R.id.v4_next);
        v4_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                ArrayList<String> v4_answer = new ArrayList<>();
                v4_answer.clear();

                if(v4_1.isChecked()) {
                    v4_answer.add(v4_1.getText().toString() + ";");
                } if (v4_2.isChecked()) {
                    v4_answer.add(v4_2.getText().toString() + ";");
                } if (v4_3.isChecked()) {
                    v4_answer.add(v4_3.getText().toString() + ";");
                } if (v4_4.isChecked()) {
                    v4_answer.add(v4_4.getText().toString() + ";");
                } if (v4_5.isChecked()) {
                    v4_answer.add(v4_5.getText().toString() + ";");
                } if (v4_6.isChecked()) {
                    v4_answer.add(v4_6.getText().toString() + ";");
                } if (v4_7.isChecked()) {
                    v4_answer.add(v4_7.getText().toString() + ";");
                } if (v4_8.isChecked()) {
                    v4_answer.add(v4_8.getText().toString() + ";");
                } if (v4_9.isChecked()) {
                    v4_answer.add(v4_9.getText().toString() + ";");
                } if (v4_10.isChecked()) {
                    v4_answer.add(v4_10.getText().toString() + ";");
                }

                answers.put("V4", String.valueOf(v4_answer));
                prepare_v5();
            }
        });
    }

    private void prepare_v5() {
        setContentView(R.layout.v_5);
        insert_db();
        final RatingBar v5_rb1 = (RatingBar) findViewById(R.id.v5_rb1);
        final ToggleButton v5_tb1 = (ToggleButton) findViewById(R.id.v5_tb1);
        final RatingBar v5_rb2 = (RatingBar) findViewById(R.id.v5_rb2);
        final ToggleButton v5_tb2 = (ToggleButton) findViewById(R.id.v5_tb2);
        final RatingBar v5_rb3 = (RatingBar) findViewById(R.id.v5_rb3);
        final ToggleButton v5_tb3 = (ToggleButton) findViewById(R.id.v5_tb3);
        final RatingBar v5_rb4 = (RatingBar) findViewById(R.id.v5_rb4);
        final ToggleButton v5_tb4 = (ToggleButton) findViewById(R.id.v5_tb4);
        final RatingBar v5_rb5 = (RatingBar) findViewById(R.id.v5_rb5);
        final ToggleButton v5_tb5 = (ToggleButton) findViewById(R.id.v5_tb5);
        final RatingBar v5_rb6 = (RatingBar) findViewById(R.id.v5_rb6);
        final ToggleButton v5_tb6 = (ToggleButton) findViewById(R.id.v5_tb6);
        final RatingBar v5_rb7 = (RatingBar) findViewById(R.id.v5_rb7);
        final ToggleButton v5_tb7 = (ToggleButton) findViewById(R.id.v5_tb7);
        final RatingBar v5_rb8 = (RatingBar) findViewById(R.id.v5_rb8);
        final ToggleButton v5_tb8 = (ToggleButton) findViewById(R.id.v5_tb8);
        final RatingBar v5_rb9 = (RatingBar) findViewById(R.id.v5_rb9);
        final ToggleButton v5_tb9 = (ToggleButton) findViewById(R.id.v5_tb9);



        v5_tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb1.setRating(0);
                    v5_rb1.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb1.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb2.setRating(0);
                    v5_rb2.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb2.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb3.setRating(0);
                    v5_rb3.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb3.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb4.setRating(0);
                    v5_rb4.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb4.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb5.setRating(0);
                    v5_rb5.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb5.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb6.setRating(0);
                    v5_rb6.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb6.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb7.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb7.setRating(0);
                    v5_rb7.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb7.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb8.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb8.setRating(0);
                    v5_rb8.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb8.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        v5_tb9.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    v5_rb9.setRating(0);
                    v5_rb9.setIsIndicator(true);
                    buttonView.setBackgroundColor(Color.DKGRAY);
                } else {
                    v5_rb9.setIsIndicator(false);
                    buttonView.setBackgroundColor(Color.LTGRAY);
                }
            }
        });

        final Button v5_next = (Button) findViewById(R.id.v5_next);
        v5_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ArrayList<String> v5_answer = new ArrayList<>();
                v5_answer.clear();

                float v5_1_answer = v5_rb1.getRating();
                float v5_2_answer = v5_rb2.getRating();
                float v5_3_answer = v5_rb3.getRating();
                float v5_4_answer = v5_rb4.getRating();
                float v5_5_answer = v5_rb5.getRating();
                float v5_6_answer = v5_rb6.getRating();
                float v5_7_answer = v5_rb7.getRating();
                float v5_8_answer = v5_rb8.getRating();
                float v5_9_answer = v5_rb9.getRating();

                v5_answer.add(v5_1_answer + ";" + v5_2_answer + ";" + v5_3_answer + ";" + v5_4_answer + ";" + v5_5_answer + ";" +
                        v5_6_answer + ";" + v5_7_answer + ";" + v5_8_answer + ";" + v5_9_answer);


                answers.put("V5", String.valueOf(v5_answer));

                prepare_v6();
            }
        });
    }

    private void prepare_v6() {
        setContentView(R.layout.v_6);
        insert_db();
        final Button v6_next = (Button) findViewById(R.id.v6_next);

        final TimePicker v6_timepicker = (TimePicker) findViewById(R.id.v6_time);
        v6_timepicker.setIs24HourView(true);
        v6_timepicker.setCurrentHour(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));

        final EditText v6_2 = (EditText) findViewById(R.id.v6_2);

        v6_next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                answers.put("V6", String.valueOf(v6_timepicker.getCurrentHour() + ":" + v6_timepicker.getCurrentMinute()));
                answers.put("V6_2", String.valueOf(v6_2.getText()));

                insert_db();

                // close application
                finish();
            }
        });
    }

    private void prepare_v7() {
        setContentView(R.layout.v_7);
        final Button v7_finish = (Button) findViewById(R.id.v7_finish);
        final EditText free_text = (EditText) findViewById(R.id.v7_freetext);
        v7_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answers.put("v7_freetext", free_text.getText().toString());
                insert_db();

                // close application
                finish();
            }
        });
    }

    private void insert_db() {
        ContentValues context_data;
        ArrayList<String> added_keys = new ArrayList<>();
        // add each answer
        for (String question_key : answers.keySet()) {
            Log.d(TAG, "storing: " + question_key);
            context_data = new ContentValues();
            context_data.put(Provider.InnoStaVa_data.TIMESTAMP, System.currentTimeMillis());
            context_data.put(Provider.InnoStaVa_data.START_TIME, String.valueOf(start_time));
            context_data.put(Provider.InnoStaVa_data.DEVICE_ID, Aware.getSetting(getApplicationContext(), Aware_Preferences.DEVICE_ID));
            context_data.put(Provider.InnoStaVa_data.ANSWER, answers.get(question_key));
            context_data.put(Provider.InnoStaVa_data.QUESTION_ID, question_key);
            getContentResolver().insert(Provider.InnoStaVa_data.CONTENT_URI, context_data);
            added_keys.add(question_key);
        }
        // remove the added ones from answers
        for (String removed_key : added_keys) {
            answers.remove(removed_key);
        }

    }
}
