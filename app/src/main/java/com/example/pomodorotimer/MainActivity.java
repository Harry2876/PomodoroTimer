package com.example.pomodorotimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    private ToggleButton fullScreenButton;
    Button settingsBtn;
    private ConstraintLayout mainLayout;
    private String currentBackgroundImage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);



        mainLayout = findViewById(R.id.mainlayout);

        //Adding full screen button activity
        fullScreenButton = findViewById(R.id.fullscreenbtn);

        //Adding full screen toggle activity
        fullScreenButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    enableFullScreen();
                } else {
                    disableFullScreen();
                }
            }
        });

        //Adding settings button functionality
        settingsBtn = findViewById(R.id.settingsbtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                intent.putExtra("currentBackgroundImage", currentBackgroundImage);
                startActivity(intent);
            }
        });


        //Retrieving the selected background options from shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String selectedBackground = sharedPreferences.getString("selected_background", "cafe");
        int resourceId = getResources().getIdentifier(selectedBackground, "drawable", getPackageName());
        mainLayout.setBackgroundResource(resourceId);

    }

    //Creating method for enabling full screen
    private void enableFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
    }



    //Creating method for disabling full screen
    private void disableFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }


}