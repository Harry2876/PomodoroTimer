package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    Spinner spinner;
    Button restartBtn;

    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String currentBackgroundImage = getIntent().getStringExtra("currentBackgroundImage");


        //Setting the spinner activity
        spinner = findViewById(R.id.backgroundspinner);

        //Setting the background options
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.background_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Handling selection of background images and saving it in shared preferences
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBackground = parent.getItemAtPosition(position).toString();

                // Check if the selected option is different from the current background image
                if (!selectedBackground.equals(currentBackgroundImage)) {
                    // Show a Toast message to inform the user
                    String message = "To Make Changes Take Effect Your App Will Restart After Clicking Save Button";
                    Toast.makeText(SettingsActivity.this, message, Toast.LENGTH_LONG).show();
                }

                //Saving the selected background image option in shared prefrences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                SharedPreferences.Editor editor  = sharedPreferences.edit();
                editor.putString("selected_background",selectedBackground);
                editor.apply();

                //Adding restart button
                restartBtn = findViewById(R.id.restartbtn);
                restartBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    restartApp();
                    }
                });

            }

            //Handling the case when nothing is selected
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    //Creating a method to restart the app
    private void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        int pendingIntentId = 123456;
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
        System.exit(0);
    }


    }