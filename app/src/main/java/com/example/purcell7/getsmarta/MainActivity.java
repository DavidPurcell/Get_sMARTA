package com.example.purcell7.getsmarta;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Globals g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        g = (Globals)getApplication();

        if(g.node == null){
            g.node = new Node(this);
        }

        if(g.deviceIDa == null || g.deviceIDa.equals("")){
            createNewUser(null);
        }

        // reset quest counter
        g.Qcounter=0;

        if (g.corrects.size()==0) {
            g.Qstart=0;
            // g.Qstart=Math.round((int)Math.random()*g.questions.size());
            g.Qnow=g.Qstart;
            g.makeQs();
            g.makea1s();
            g.makea2s();
            g.makea3s();
            g.makeCorrects();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Android M Permission check
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                    }
                });
                builder.show();
            }
        }

        setContentView(R.layout.activity_main);

        //  Play
        final Button buttonA = (Button) findViewById(R.id.button_a);

        buttonA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(MainActivity.this, Play.class);
                //myIntent.putExtra("key", value);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //  Leaderboard
        final Button buttonB = (Button) findViewById(R.id.button_b);
        buttonB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(MainActivity.this, LocalLeaderboard.class);
                //myIntent.putExtra("key", value);
                MainActivity.this.startActivity(myIntent);
            }
        });

        //  My Leaderboard
        final Button buttonC = (Button) findViewById(R.id.button_c);
        buttonC.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent myIntent = new Intent(MainActivity.this, MyLeaderboard.class);
                //myIntent.putExtra("key", value);
                MainActivity.this.startActivity(myIntent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("PERMISSION", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    public void createNewUser(View view){
        g.deviceIDa="Player #" + Math.abs(new Random().nextInt()%1000)+1;
        Toast.makeText(this.getApplicationContext(), g.deviceIDa, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        g.node.start();
    }
}
