package com.team9889.internal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

public class AutonomousSelection extends Activity {

    private SharedPreferences globalPrefs;

    private Button redDepot,    redCrater;
    private Button blueDepot,   blueCrater;
    private Button saveAndExitButton;

    private Switch doubleSample;

    private String alliance,    side;

    private String red      = "Red";
    private String blue     = "Blue";
    private String crater   = "Crater";
    private String depot    = "Depot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonomous_selection);

        // Preferences
        globalPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // Buttons
        redDepot            = (Button)findViewById(R.id.redDepot);
        redCrater           = (Button)findViewById(R.id.redCrater);
        blueDepot           = (Button)findViewById(R.id.blueDepot);
        blueCrater          = (Button)findViewById(R.id.blueCrater);
        saveAndExitButton   = (Button)findViewById(R.id.saveAndExitButton);

        // Switches
        doubleSample        = (Switch)findViewById(R.id.doubleSample);

        // Update the bottons with the current layout
        updateOverlay(globalPrefs.getString("AllianceColor", ""),
                globalPrefs.getString("Side", "")  );

        // OnClickListeners

        redDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOverlay(red, depot);
                save();
            }
        });

        redCrater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOverlay(red, crater);
                save();
            }
        });

        blueDepot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOverlay(blue, depot);
                save();
            }
        });

        blueCrater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateOverlay(blue, crater);
                save();
            }
        });

        saveAndExitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
                exit();
            }
        });
    }

    /**
     * Update the buttons
     * @param alliance Color
     * @param side Crater or Depot side
     */
    private void updateOverlay(String alliance, String side){
        if (alliance.equals(red) && side.equals(crater)){
            redCrater.setText("0");
            redDepot.setText("");
            blueCrater.setText("");
            blueDepot.setText("");
        } else if(alliance.equals(red) && side.equals(depot)){
            redDepot.setText("0");
            redCrater.setText("");
            blueDepot.setText("");
            blueCrater.setText("");
        } else if(alliance.equals(blue) && side.equals(crater)){
            blueCrater.setText("0");
            blueDepot.setText("");
            redCrater.setText("");
            redDepot.setText("");
        } else if(alliance.equals(blue) && side.equals(depot)){
            blueDepot.setText("0");
            blueCrater.setText("");
            redDepot.setText("");
            redCrater.setText("");
        }

        this.alliance = alliance;
        this.side = side;
    }

    /**
     * Saves the Prefs to globalPrefs
     */
    private void save(){
        SharedPreferences.Editor editor = globalPrefs.edit();

        editor.putString("AllianceColor", alliance);
        editor.putString("Side", side);
        editor.putBoolean("DoubleSample", doubleSample.isChecked());
        editor.apply();

        // Test if the app saves strings after fresh app install.
        SharedPreferences globalPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        RobotLog.a("Alliance: " +
                String.valueOf(globalPrefs.getString("AllianceColor", "Nope")));

        RobotLog.a("CraterOrDepot: " +
                String.valueOf(globalPrefs.getString("Side", "NOT Today")));

        RobotLog.a("Go for double sample?: " +
                String.valueOf(globalPrefs.getBoolean("DoubleSample", false)));
    }

    /**
     * Exit Autonomous Settings
     */
    public void exit(){
        Intent launchNewIntent = new Intent(this, FtcRobotControllerActivity.class);
        startActivityForResult(launchNewIntent, 0);
    }
}
