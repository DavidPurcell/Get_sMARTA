package com.example.purcell7.getsmarta;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Globals g = (Globals)getApplication();

        // reset quest counter
        g.Qcounter=0;

        g.deviceIDa="Jan";

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


        // remove when done
        if (g.ridescoretable.size()<2) {
            g.makescores();
            g.makeIDs();
            g.maketimes();
            //
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
}
