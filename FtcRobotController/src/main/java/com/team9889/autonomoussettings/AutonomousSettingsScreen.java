package com.team9889.autonomoussettings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.qualcomm.ftcrobotcontroller.R;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcontroller.internal.FtcRobotControllerActivity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by joshua9889 on 4/3/2019.
 */
public class AutonomousSettingsScreen extends Activity {
    public AutonomousSettingsScreen() {}

    private Button depot, crater;

    private ToggleButton doubleSample, scoreCollectedSample;

    private String depotString = "Depot";
    private String craterString = "Crater";
    private String current;

    private String filename = "autonomousSettings.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autonomous_setting);

        depot = (Button) findViewById(R.id.depot_side_button);
        crater = (Button) findViewById(R.id.crater_side_button);
        Button exit = (Button) findViewById(R.id.save_and_exit_button);

        doubleSample = (ToggleButton)findViewById(R.id.double_sample_switch);
        scoreCollectedSample = (ToggleButton)findViewById(R.id.score_collected_mineral_switch);

        depot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = depotString;
                update();
            }
        });

        crater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current = craterString;
                update();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exit();
            }
        });

        doubleSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                update();
            }
        });

        scoreCollectedSample.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                update();
            }
        });

        read();
    }

    private void setSide(String string) {
        if(string.equals(depotString)) {
            depot.setText("0");
            crater.setText("");
        } else if(string.equals(craterString)) {
            depot.setText("");
            crater.setText("0");
        }
    }

    private void update() {
        // Save data to a file
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_data");
        myDir.mkdirs();
        File file = new File(myDir, filename);
        if (file.exists())
            file.delete();

        String output = current + "," + String.valueOf(doubleSample.isChecked()) + "," +
                String.valueOf(scoreCollectedSample.isChecked());

        setSide(current);

        Charset charset = Charset.forName("US-ASCII");
        RobotLog.a("Writing " + output + " to " + myDir.toString());

        try {
            FileOutputStream writer = new FileOutputStream(file);
            writer.write(output.getBytes(charset));
            writer.close();

            RobotLog.a("Wrote " + output + " to " + myDir.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void read() {
        String root = Environment.getExternalStorageDirectory().toString();
        File file = new File(root + "/saved_data/" + filename);

        String text = "";
        int content;

        try {
            FileInputStream reader = new FileInputStream(file);

            while ((content = reader.read()) != -1) {
                // convert to char and display it
                text += (char)content;
            }

            String[] lines = text.split(",");

            for (int i = 0; i < lines.length; i++) {
                lines[i] = lines[i].replace(",", "");
            }

            current = String.valueOf(lines[0]);
            setSide(current);

            doubleSample.setChecked(String.valueOf(lines[1]).equals("true"));
            scoreCollectedSample.setChecked(String.valueOf(lines[2]).equals("true"));

            RobotLog.e(Arrays.toString(lines));
        } catch (Exception e) {
            e.printStackTrace();
            setSide(depotString);
        }
    }

    private void exit() {
        update();

        Intent launchNewIntent = new Intent(this, FtcRobotControllerActivity.class);
        startActivityForResult(launchNewIntent, 0);
    }
}
